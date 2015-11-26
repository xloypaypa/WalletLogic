package control.money;

import control.LogicTesting;
import control.tool.MoneyLogicNoSend;
import control.user.UserLogicLoginTest;
import model.db.MoneyCollection;
import model.session.SessionManager;
import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/11/26.
 * it's the testing code for remove money
 */
public class MoneyLogicRemoveTest extends LogicTesting {

    @Test
    public void checkUrl() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        MoneyLogicNoSend moneyLogic = removeMoney(this.socket, "type");

        assertEquals("/removeMoney", moneyLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldFailWhenRemoveInvalidMoney() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        MoneyLogicNoSend moneyLogic = removeMoney(this.socket, "type2");

        JSONObject jsonObject = JSONObject.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldFailWhenNotLogin() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        SessionManager.getSessionManager().getSessionMessage(this.socket).setUsername(null);
        MoneyLogicNoSend moneyLogic = removeMoney(this.socket, "type2");

        JSONObject jsonObject = JSONObject.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldOkWhenRemoveSomeMoney() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        MoneyLogicNoSend moneyLogic = removeMoney(this.socket, "type");

        JSONObject jsonObject = JSONObject.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("ok", jsonObject.getString("result"));
    }

    @Test
    public void shouldNoDataWhenRemove() throws Exception {
        UserLogicLoginTest.registerUser(this.socket, "username", "password");
        UserLogicLoginTest.loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        removeMoney(this.socket, "type");

        MoneyCollection moneyCollection = new MoneyCollection();

        assertEquals(0, moneyCollection.getMoneyListData("username").size());
    }

}