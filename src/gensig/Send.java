/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gensig;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

/**
 *
 * @author Rahmican
 */
public class Send {
    public static void main(String args[]) {
		try {
			FileOutputStream fos = new FileOutputStream("KeyPair/text.txt");
			MessageDigest md = MessageDigest.getInstance("SHA");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			String data = "This have I thought good to deliver thee, "+
				"that thou mightst not lose the dues of rejoicing " +
				"by being ignorant of what greatness is promised thee.";
			byte buf[] = data.getBytes();
			md.update(buf);
			oos.writeObject("DATA : "+data);
			oos.writeObject("DIGEST : "+md.digest());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
