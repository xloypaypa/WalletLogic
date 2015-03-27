package database;

import java.util.Vector;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HHDTest {
	@Before
	public void createFile(){
		HHD.writeFile("aim.txt", "this is a test file.");
	}
	
	@After
	public void deleteFile(){
		HHD.deleteFile("aim.txt");
	}
	
	@Test
	public void test1(){
		HHD.copyFile("aim.txt", "ans.txt");
		Vector <String> file=HHD.readFile("ans.txt");
		Assert.assertEquals(1, file.size());
		Assert.assertEquals("this is a test file.", file.get(0));
		HHD.deleteFile("ans.txt");
	}
}
