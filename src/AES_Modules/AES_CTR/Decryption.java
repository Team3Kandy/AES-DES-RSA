/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AES_Modules.AES_CTR;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.Mac;
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
         public byte[] deriveKey(String p, byte[] s, int i, int l) throws Exception {
  PBEKeySpec ks = new PBEKeySpec(p.toCharArray(), s, i, l);
  SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
  return skf.generateSecret(ks).getEncoded();
}
         
    public  String decrypt(String eos, String p) throws Exception {
  // Recover our Byte Array by Base64 Decoding
  // byte[] os = decodeBase64(eos.toCharArray());
  byte[] os = Base64.decodeBase64(eos);
 
  // Check Minimum Length (ESALT (20) + HSALT (20) + HMAC (32))
  if (os.length > 72) {
    // Recover Elements from String
    byte[] esalt = Arrays.copyOfRange(os, 0, 20);
    byte[] hsalt = Arrays.copyOfRange(os, 20, 40);
    byte[] es = Arrays.copyOfRange(os, 40, os.length - 32);
    byte[] hmac = Arrays.copyOfRange(os, os.length - 32, os.length);
 
    // Regenerate HMAC key using Recovered Salt (hsalt)
    byte[] dhk = deriveKey(p, hsalt, 100000, 160);
 
    // Perform HMAC using SHA-256
    SecretKeySpec hks = new SecretKeySpec(dhk, "HmacSHA256");
    Mac m = Mac.getInstance("HmacSHA256");
    m.init(hks);
    byte[] chmac = m.doFinal(es);
 
    // Compare Computed HMAC vs Recovered HMAC
    if (MessageDigest.isEqual(hmac, chmac)) {
      // HMAC Verification Passed
      // Regenerate Encryption Key using Recovered Salt (esalt)
      byte[] dek = deriveKey(p, esalt, 100000, 128);
 
      // Perform Decryption
      SecretKeySpec eks = new SecretKeySpec(dek, "AES");
      Cipher c = Cipher.getInstance("AES/CTR/NoPadding");
      c.init(Cipher.DECRYPT_MODE, eks, new IvParameterSpec(new byte[16]));
      byte[] s = c.doFinal(es);
 
      // Return our Decrypted String
      return new String(s, StandardCharsets.UTF_8);
    }
  }
  throw new Exception();
}
    
}
