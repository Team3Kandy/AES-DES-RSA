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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;

public class GenerateDigitalSignature {
    public static void main(String[] args) {
        try {
            
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(1024, random);

        
        KeyPair keyPair = keyGen.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        
         System.out.println("public key = " + publicKey);
        System.out.println("private key = " + privateKey);
        
        
        
        Signature signature = Signature.getInstance("SHA1withDSA", "SUN");
        signature.initSign(privateKey);

        //
        // Supply the data to be signed to the Signature object
        // using the update() method and generate the digital
        // signature.
        //
        byte[] bytes = Files.readAllBytes(Paths.get("KeyPair/README"));
        
            System.out.println("bytes: " + bytes);
        signature.update(bytes);
        byte[] digitalSignature = signature.sign();

        
        Files.write(Paths.get("signature"), digitalSignature);
        Files.write(Paths.get("publickey"), keyPair.getPublic().getEncoded());
        
        signature.initVerify(publicKey);
        
        if(signature.verify(digitalSignature))
        {
            System.out.println("İMZA GEÇERLİ");
            System.out.println(digitalSignature);
            
        }
        
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    } catch (NoSuchProviderException e) {
        e.printStackTrace();
    } catch (InvalidKeyException e) {
        e.printStackTrace();
    } catch (SignatureException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    }