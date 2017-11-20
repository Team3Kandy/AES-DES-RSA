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
import java.util.Arrays;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
 
public class AES {
 
    private static SecretKeySpec secretKey;
    private static byte[] key;
    public  static SecretKey generatedKey;
    
     public  static boolean generateKey() {
        try {
            generatedKey = KeyGenerator.getInstance("AES").generateKey();
           
        } catch (NoSuchAlgorithmException e) {
            System.err.println("NoSuchAlgorithmException: " + e.getMessage());
            return false;
        } 
        return true;
    }
    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        
        try {
            
           
            
            System.out.println("Düz metin hali :  = "+myKey);
            key = myKey.getBytes("UTF-8");
            System.out.println("UTF8 = "+key);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            System.out.println("Digest = "+key);
            System.out.println("Kopyalamadan önceki dizi uzuluğu = "+ key.length);
            
            key = Arrays.copyOf(key, 16); 
            
            System.out.println("Kopyalama işleminden sonraki dizi uzunluğu  = "+key.length);
            
            System.out.println("16 byte'lık Hali:  = "+key);
            secretKey = new SecretKeySpec(key, "AES");
            System.out.println("SecretKey son Hali: "+ secretKey);
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
             if(generateKey())
            {
                secretKey = (SecretKeySpec) generatedKey;
                System.out.println("SecretKey yeni oluşturulan Key ile değişti.");
            }
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            System.out.println("Şifrelenecek textin bytları : "+ strToEncrypt.getBytes("UTF-8"));
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
    final String secretKey = "Bu bir şifreleme anahtarıdır. Bu bir şifreleme anahtarıdır. Bu bir şifreleme anahtarıdır. Bu bir şifreleme anahtarıdır.Bu bir şifreleme anahtarıdır.Bu bir şifreleme anahtarıdır.Bu bir şifreleme anahtarıdır. ";
    
    String originalString = "Netaş Aeropark";
    String encryptedString = encrypt(originalString, secretKey) ;
    String decryptedString = decrypt(encryptedString, secretKey) ;
     
    System.out.println("Orjinal Metin: " + originalString);
    System.out.println("Şifreli Metin "+encryptedString);
    System.out.println("Çözülmüş metin: "+ decryptedString);
    
   System.out.println("Maximum Key Uzunluğu: "+Cipher.getMaxAllowedKeyLength("AES"));
    
   
}
}