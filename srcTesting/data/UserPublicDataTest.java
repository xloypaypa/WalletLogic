package data;

import static org.junit.Assert.*;

import org.junit.Test;

import encryptionAlgorithm.MD5;
import type.UserMessage;

public class UserPublicDataTest extends DataTest {

	@Test
	public void testBaseFeature() {
		UserPublicData.addUser(new UserMessage("name", MD5.encode("pass"), "normal"));
		assertTrue(UserPublicData.userExist("name"));
		assertTrue(UserPublicData.checkUser("name", "pass"));
		UserPublicData.loadUser();
		assertTrue(UserPublicData.userExist("name"));
		assertTrue(UserPublicData.checkUser("name", "pass"));
		
		UserPublicData.login("name", "pass");
		assertEquals("name", UserPublicData.getNowUser());
		assertEquals("normal", UserPublicData.getUserType());
	}

}
