package encryptionAlgorithm;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

public class AESTest {

	@Test
	public void test1() {
		assertEquals("abc", AES.decode(AES.encode("abc", "abcd"), "abcd"));
	}
	
	@Test
	public void test2() {
		assertEquals("abc", AES.decode(AES.encode("abc", "abcdabcdabcdabcdabcdabcd"), "abcdabcdabcdabcdabcdabcd"));
	}
	
	@Test
	public void test3() {
		Vector <String> expected,ans;
		expected=new Vector<>();
		ans=new Vector<>();
		expected.addElement("a");
		expected.addElement("b");
		String s=new String();
		for (int i=0;i<expected.size();i++) s+=expected.get(i)+"\r\n";
		ans=AES.decodeAsVector(AES.encode(s, "abcdabcdabcdabcdabcdabcd"), "abcdabcdabcdabcdabcdabcd");
		assertEquals(expected.size(), ans.size());
		for (int i=0;i<expected.size();i++){
			assertEquals(expected.get(i), ans.get(i));
		}
	}

}
