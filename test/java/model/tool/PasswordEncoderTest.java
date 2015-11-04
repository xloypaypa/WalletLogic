package model.tool;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xlo on 2015/11/4.
 * it's the testing code for encode
 */
public class PasswordEncoderTest {

    @Test
    public void testEncode() throws Exception {
        String s = "abc";
        assertEquals("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", PasswordEncoder.encode(s));
    }
}