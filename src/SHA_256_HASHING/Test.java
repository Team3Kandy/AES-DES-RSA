/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SHA_256_HASHING;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Rahmican
 */
public class Test {
    public static void main( String[] args ) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance( "SHA-256" );
    String text = "Text to hash, cryptographically.";

    md.update( text.getBytes( StandardCharsets.UTF_8 ) );
    byte[] digest = md.digest();
    
    String hex = String.format( "%064x", new BigInteger( 1, digest ) );
    System.out.println( hex );
  }
}
