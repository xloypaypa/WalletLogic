package control.money;

import control.LogicTesting;
import control.tool.MoneyLogicNoSend;
import control.user.UserLogicLoginTest;
import model.db.MoneyCollection;
import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/11/25.
 * it's the money logic's test
 */
public class MoneyLogicCreateTest extends LogicTesting {

    @Test
    public void checkUrl() throws Exception {
        MoneyLogicNoSend moneyLogic = new MoneyLogicNoSend(this.socket);
        moneyLogic.createMoney("type", "100");
        moneyLogic.waitEventEnd();

        assertEquals("/createMoney", moneyLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldFailWhenCreateNewMoneyWithoutLogin() throws Exception {
        MoneyLogicNoSend moneyLogic = new MoneyLogicNoSend(this.socket);
        moneyLogic.createMoney("type", "100");
        moneyLogic.waitEventEnd();

        JSONObject jsonObject = JSONObject.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldFailWhenCreateSameMoney() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        MoneyLogicNoSend moneyLogic = new MoneyLogicNoSend(this.socket);
        moneyLogic.createMoney("type", "100");
        moneyLogic.waitEventEnd();

        moneyLogic = new MoneyLogicNoSend(this.socket);
        moneyLogic.createMoney("type", "100");
        moneyLogic.waitEventEnd();

        JSONObject jsonObject = JSONObject.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldOkWhenCreateNewMoney() throws Exception {
        UserLogicLoginTest.registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        MoneyLogicNoSend moneyLogic = new MoneyLogicNoSend(this.socket);
        moneyLogic.createMoney("type", "100");
        moneyLogic.waitEventEnd();

        JSONObject jsonObject = JSONObject.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("ok", jsonObject.getString("result"));
    }

    @Test
    public void DBShouldHaveDataAfterAddMoney() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);

        MoneyCollection moneyCollection = new MoneyCollection();
        assertEquals(1, moneyCollection.getMoneyListData("username").size());
    }

}