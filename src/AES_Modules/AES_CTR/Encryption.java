/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AES_Modules.AES_CTR;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import static org.apache.commons.codec.binary.Base64.encodeBase64;

/**
 *
 * @author Rahmican
 */
 
public class Encryption {
     public byte[] deriveKey(String p, byte[] s, int i, int l) throws Exception {
  PBEKeySpec ks = new PBEKeySpec(p.toCharArray(), s, i, l);
  SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
  return skf.generateSecret(ks).getEncoded();
}
    public String encrypt(String s, String p) throws Exception {
  SecureRandom r = SecureRandom.getInstance("SHA1PRNG");
 
  // Generate 160 bit Salt for Encryption Key
  byte[] esalt = new byte[20]; r.nextBytes(esalt);
  // Generate 128 bit Encryption Key
  byte[] dek = deriveKey(p, esalt, 100000, 128);
 
  // Perform Encryption
  SecretKeySpec eks = new SecretKeySpec(dek, "AES");
  Cipher c = Cipher.getInstance("AES/CTR/NoPadding");
  c.init(Cipher.ENCRYPT_MODE, eks, new IvParameterSpec(new byte[16]));
  byte[] es = c.doFinal(s.getBytes(StandardCharsets.UTF_8));
 
  // Generate 160 bit Salt for HMAC Key
  byte[] hsalt = new byte[20]; r.nextBytes(hsalt);
  // Generate 160 bit HMAC Key
  byte[] dhk = deriveKey(p, hsalt, 100000, 160);
 
  // Perform HMAC using SHA-256
  SecretKeySpec hks = new SecretKeySpec(dhk, "HmacSHA256");
  Mac m = Mac.getInstance("HmacSHA256");
  m.init(hks);
  byte[] hmac = m.doFinal(es);
 
  // Construct Output as "ESALT + HSALT + CIPHERTEXT + HMAC"
  byte[] os = new byte[40 + es.length + 32];
  System.arraycopy(esalt, 0, os, 0, 20);
  System.arraycopy(hsalt, 0, os, 20, 20);
  System.arraycopy(es, 0, os, 40, es.length);
  System.arraycopy(hmac, 0, os, 40 + es.length, 32);
 
  // Return a Base64 Encoded String
  return new String(encodeBase64(os));
}
    
}
