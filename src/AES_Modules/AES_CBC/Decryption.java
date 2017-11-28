/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AES_Modules.AES_CBC;

import java.nio.ByteBuffer;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Rahmican
 */
public class Decryption {
  @SuppressWarnings("static-access")
  public String decrypt(String encryptedText) throws Exception {
    String password="Hello";
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    
    // Salt ve Vi 'yi ayır
    ByteBuffer buffer = ByteBuffer.wrap(new Base64().decode(encryptedText));
    byte[] saltBytes = new byte[20];
    buffer.get(saltBytes, 0, saltBytes.length);
    byte[] ivBytes1 = new byte[cipher.getBlockSize()];
    buffer.get(ivBytes1, 0, ivBytes1.length);
    byte[] encryptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes1.length];
  
    buffer.get(encryptedTextBytes);
    
// Anahtarı türet
    
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 65556, 256);
    SecretKey secretKey = factory.generateSecret(spec);
    SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
    cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes1));
    byte[] decryptedTextBytes = null;
    
    try {
      decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
    } catch (IllegalBlockSizeException e) {
        e.printStackTrace();
    } catch (BadPaddingException e) {
        e.printStackTrace();
    }
   
    return new String(decryptedTextBytes);
  }
}