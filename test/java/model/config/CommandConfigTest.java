package model.config;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by xlo on 15-11-3.
 * it's the testing code for command config
 */
public class CommandConfigTest {

    @Test
    public void testInit() {
        CommandConfig commandConfig = CommandConfig.getConfig();
        CommandConfig.PostInfo postInfo = commandConfig.findPostInfo("/register");
        assertNotNull(postInfo);
        Class[] param = new Class[2];
        param[0]=param[1]=String.class;
        try {
            assertNotNull(commandConfig.getMethod(postInfo.getManager(),
                    postInfo.getMethod(), param));
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            fail();
        }
    }
}