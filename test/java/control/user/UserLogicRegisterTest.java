package control.user;

import control.LogicTesting;
import control.tool.UserLogicNoSend;
import model.db.UserCollection;
import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xlo on 2015/11/26.
 * it's the testing code for user register
 */
public class UserLogicRegisterTest extends LogicTesting {

    @Test
    public void registerShouldSendUrlToRegister() throws Exception {
        UserLogicNoSend userLogic = new UserLogicNoSend(this.socket);
        userLogic.register("username", "password");
        userLogic.waitEventEnd();

        assertEquals("/register", userLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldOkWhenRegisterNewUser() throws Exception {
        UserLogicNoSend userLogic = new UserLogicNoSend(this.socket);
        userLogic.register("username", "password");
        userLogic.waitEventEnd();

        JSONObject jsonObject = JSONObject.fromObject(new String(userLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("ok", jsonObject.getString("result"));
    }

    @Test
    public void shouldFailWhenRegisterSameUser() throws Exception {
        registerUser(this.socket, "username", "password");

        UserLogicNoSend userLogic = new UserLogicNoSend(this.socket);
        userLogic.register("username", "password");
        userLogic.waitEventEnd();

        JSONObject jsonObject = JSONObject.fromObject(new String(userLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldDBHaveDataAfterRegister() throws Exception {
        registerUser(this.socket, "username", "password");

        UserCollection userCollection = new UserCollection();
        assertNotNull(userCollection.getUserData("username"));
    }

}