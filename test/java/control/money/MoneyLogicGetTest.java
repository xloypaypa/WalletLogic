package control.money;

import control.LogicTesting;
import control.user.UserLogicLoginTest;
import control.tool.MoneyLogicNoSend;
import model.session.SessionManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xlo on 2015/11/26.
 * it's the testing code for get money
 */
public class MoneyLogicGetTest extends LogicTesting {

    @Test
    public void checkUrl() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        MoneyLogicNoSend moneyLogic = getMoney(this.socket);

        assertEquals("/getMoney", moneyLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldFailWhenNotLogin() throws Exception {
        MoneyLogicNoSend moneyLogic = getMoney(this.socket);

        JSONObject jsonObject = JSONObject.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldSendDataWhenGetData() throws Exception {
        UserLogicLoginTest.registerUser(this.socket, "username", "password");
        UserLogicLoginTest.loginUser(this.socket, "username", "password");

        MoneyLogicNoSend moneyLogic = new MoneyLogicNoSend(this.socket);
        moneyLogic.createMoney("type", "100");
        moneyLogic.waitEventEnd();

        moneyLogic = new MoneyLogicNoSend(this.socket);
        moneyLogic.getMoney();
        moneyLogic.waitEventEnd();

        JSONArray jsonArray = JSONArray.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals(1, jsonArray.size());
    }

}