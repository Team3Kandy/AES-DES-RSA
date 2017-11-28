/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AES_Modules.AES_CBC;

/**
 *
 * @author Rahmican
 */
public class Test{
/**
 * @param args the command line arguments
 */
  public static void main(String[] args) throws Exception{
    Encryption en=new Encryption();
    String encryptedWord=en.encrypt("Test"); 
    System.out.println("Encrypted word is : " + encryptedWord);
    Decryption de =new Decryption();
    System.out.println("Decrypted word is : " +    de.decrypt(encryptedWord));
  }
}
