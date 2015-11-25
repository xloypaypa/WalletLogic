package control;

import control.tool.UserLogicNoSend;
import model.session.SessionManager;
import model.tool.PasswordEncoder;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

/**
 * Created by xlo on 2015/11/25.
 * it's the testing code for user logic
 */
public class UserLogicTest extends LogicTesting {

    public static void registerUser(Socket socket, String username, String password) throws Exception {
        UserLogicNoSend userLogic = new UserLogicNoSend(socket);
        userLogic.register(username, password);
        userLogic.waitEventEnd();
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

}