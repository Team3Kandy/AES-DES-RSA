/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD5;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Rahmican
 */
public class Encryption {
    public static void main( String[] args ) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance( "MD5" );
    String text = "Netas Aeropark";

    md.update( text.getBytes( StandardCharsets.UTF_8 ) );
    byte[] digest = md.digest();

    StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}

		System.out.println("original:" + text);
		System.out.println("digested(hex):" + sb.toString());
  }
}
