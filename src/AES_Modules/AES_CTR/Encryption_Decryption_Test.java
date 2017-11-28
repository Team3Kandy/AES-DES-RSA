/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AES_Modules.AES_CTR;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;


/**
 *
 * @author Rahmican
 */
public class Encryption_Decryption_Test {
    

    public static void main(String[] args) throws Exception{
    
    Encryption en=new Encryption();
    String encryptedWord=en.encrypt("Test", "Anahtar");
           
    System.out.println("Encrypted word is : " + encryptedWord);
    Decryption de =new Decryption();
    System.out.println("Decrypted word is : " +    de.decrypt(encryptedWord, "Anahtar"));
  }


}
