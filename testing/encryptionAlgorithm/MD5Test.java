package encryptionAlgorithm;

import static org.junit.Assert.*;

import org.junit.Test;

public class MD5Test {

	@Test
	public void test() {
		assertFalse(MD5.encode("abc").equals(MD5.encode("adr")));
		assertTrue(MD5.encode("adr").equals(MD5.encode("adr")));
	}

}
