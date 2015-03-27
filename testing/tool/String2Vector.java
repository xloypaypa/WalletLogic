package tool;

import java.util.Vector;

import encryptionAlgorithm.AES;

public class String2Vector {
	public static Vector <String> toVector(String s){
		return AES.decodeAsVector(AES.encode(s, ""), "");
	}
	
	public static String toString(Vector <String> a){
		String ans=new String();
		for (int i=0;i<a.size();i++) ans+=a.get(i)+"\r\n";
		return ans;
	}
}
