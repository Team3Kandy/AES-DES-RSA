/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gensig;

/**
 *
 * @author Rahmican
 */
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
 
public class AES {
 
    
    
    private static SecretKeySpec secretKey;
    private static byte[] key;
   
    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        
        try {
            
           
            
            key = myKey.getBytes("UTF-8");
            
            sha = MessageDigest.getInstance("SHA-1");
            
            key = sha.digest(key);
            
            key = Arrays.copyOf(key, 16); 
            
            
            secretKey = new SecretKeySpec(key, "AES");
            
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
 
    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES");
           
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Şifreleme hatası : " + e.toString());
        }
        return null;
    }
 
    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES");
            
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Şifre Çözme Hatası : " + e.toString());
        }
        return null;
        
    }
    public static void main(String[] args) throws NoSuchAlgorithmException
{
    Security.addProvider(new BouncyCastleProvider());
    
    final String secretKey = "a";
    
    String originalString = "Netaş Aeropark";
    String encryptedString = encrypt(originalString, secretKey) ;
    String decryptedString = decrypt(encryptedString, secretKey) ;
     
    System.out.println("Orjinal Metin: " + originalString);
    System.out.println("Şifreli Metin "+encryptedString);
    //System.out.println("Generated Key "+generatedKey);   
    System.out.println("Çözülmüş metin: "+ decryptedString);
    
   //System.out.println("Maximum Key Uzunluğu: "+Cipher.getMaxAllowedKeyLength("AES"));
    
   
}
}
