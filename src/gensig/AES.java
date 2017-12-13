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
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES {
 
    
    
    private static SecretKeySpec secretKey;
    private static byte[] key;
   
    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        
        try {
            
           
            
            key = myKey.getBytes("UTF-8");
            
            sha = MessageDigest.getInstance("SHA-1","BCFIPS");
            
            key = sha.digest(key);
            
            key = Arrays.copyOf(key, 16);
            
            
            secretKey = new SecretKeySpec(key, "AES");
            
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }
    
 
    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES","BCFIPS");

            cipher.init(Cipher.ENCRYPT_MODE, secretKey,(AlgorithmParameterSpec) null);
            
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Şifreleme hatası : " );
        }
        return null;
    }
 
    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES","BCFIPS");

            cipher.init(Cipher.DECRYPT_MODE, secretKey,(AlgorithmParameterSpec) null);
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

    final String secretKey = "1";


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
