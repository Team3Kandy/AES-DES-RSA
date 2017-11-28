/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DES;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author Rahmican
 */
public class Test {
  public static void main(String[] argv) throws Exception {
    SecretKey key = KeyGenerator.getInstance("DES").generateKey();
    Encryption_Decryption encrypter = new Encryption_Decryption(key);
    String encrypted = encrypter.encrypt("Don't tell anybody!");
    String decrypted = encrypter.decrypt(encrypted);
    
    
    System.out.println("encrypted: "+encrypted);
    System.out.println("decrypted: "+decrypted);
  }
}
