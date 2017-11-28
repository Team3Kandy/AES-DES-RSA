/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gensig;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author Rahmican
 */
public class SimpleMAC {

	public static void main(String[] args) throws Exception{
		String simple = "Netas Aeropark'dan selamlar.";
		
		Mac mac = Mac.getInstance("HMACSHA256");
		
                
                
		mac.init(new SecretKeySpec("Password ".getBytes("UTF-8"), ""));
		
               
                
		byte[] ret = mac.doFinal(simple.getBytes("UTF-8"));
		
		System.out.println("Length of MAC: " + ret.length);
		for(byte b: ret){
			System.out.print(b + ", ");
		}
		
		String tobeverified = Hex.encodeHexString(ret);
		System.out.println();
		System.out.println(tobeverified);

	}

}
