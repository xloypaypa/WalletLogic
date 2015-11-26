package control.user;

import control.LogicTesting;
import control.tool.UserLogicNoSend;
import model.session.SessionManager;
import model.tool.PasswordEncoder;
import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/11/25.
 * it's the testing code for user logic
 */
public class UserLogicLoginTest extends LogicTesting {

    @Test
    public void loginShouldSendUrlToLogin() throws Exception {
        UserLogicNoSend userLogic = new UserLogicNoSend(this.socket);
        userLogic.login("username", "password");
        userLogic.waitEventEnd();

        assertEquals("/login", userLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldOkWhenLoginWithRightMessage() throws Exception {
        registerUser(this.socket, "username", "password");

        UserLogicNoSend userLogic = new UserLogicNoSend(this.socket);
        String password = "password" + SessionManager.getSessionManager().getSessionMessage(this.socket).getSessionID();
        userLogic.login("username", PasswordEncoder.encode(password));
        userLogic.waitEventEnd();

        JSONObject jsonObject = JSONObject.fromObject(new String(userLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("ok", jsonObject.getString("result"));
    }

    @Test
    public void shouldFailedWhenLoginWithWrongMessage() throws Exception {
        registerUser(this.socket, "username", "password");

        UserLogicNoSend userLogic = new UserLogicNoSend(this.socket);
        String password = "abc" + SessionManager.getSessionManager().getSessionMessage(this.socket).getSessionID();
        userLogic.login("username", PasswordEncoder.encode(password));
        userLogic.waitEventEnd();

        JSONObject jsonObject = JSONObject.fromObject(new String(userLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void SessionManagerShouldHaveDataAfterLogin() throws  Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        assertEquals("username", SessionManager.getSessionManager().getUsername(this.socket));
    }

}