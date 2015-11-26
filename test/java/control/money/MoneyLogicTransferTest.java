package control.money;

import control.LogicTesting;
import control.tool.MoneyLogicNoSend;
import control.user.UserLogicLoginTest;
import model.db.DBTable;
import model.db.MoneyCollection;
import model.session.SessionManager;
import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by xlo on 2015/11/26.
 * it's the testing code for transfer money
 */
public class MoneyLogicTransferTest extends LogicTesting {

    @Test
    public void checkUrl() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type1", 100);
        createNewMoney(this.socket, "type2", 100);
        MoneyLogicNoSend moneyLogic = transferMoney(this.socket, "type1", "type2", 30);

        assertEquals("/transferMoney", moneyLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldOkWhenTransferMoney() throws Exception {
        UserLogicLoginTest.registerUser(this.socket, "username", "password");
        UserLogicLoginTest.loginUser(this.socket, "username", "password");

        createNewMoney(this.socket, "type1", 100);
        createNewMoney(this.socket, "type2", 100);
        MoneyLogicNoSend moneyLogic = transferMoney(this.socket, "type1", "type2", 30);

        JSONObject jsonObject = JSONObject.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("ok", jsonObject.getString("result"));
    }

    @Test
    public void shouldFailWhenRemoveInvalidMoney() throws Exception {
        UserLogicLoginTest.registerUser(this.socket, "username", "password");
        UserLogicLoginTest.loginUser(this.socket, "username", "password");

        createNewMoney(this.socket, "type1", 100);
        MoneyLogicNoSend moneyLogic = transferMoney(this.socket, "type1", "type2", 30);

        JSONObject jsonObject = JSONObject.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldFailWhenNotLogin() throws Exception {
        UserLogicLoginTest.registerUser(this.socket, "username", "password");
        UserLogicLoginTest.loginUser(this.socket, "username", "password");

        createNewMoney(this.socket, "type1", 100);
        createNewMoney(this.socket, "type2", 100);
        SessionManager.getSessionManager().getSessionMessage(this.socket).setUsername(null);
        MoneyLogicNoSend moneyLogic = transferMoney(this.socket, "type1", "type2", 30);

        JSONObject jsonObject = JSONObject.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldFailWhenTransferTooManyMoney() throws Exception {
        UserLogicLoginTest.registerUser(this.socket, "username", "password");
        UserLogicLoginTest.loginUser(this.socket, "username", "password");

        createNewMoney(this.socket, "type1", 100);
        createNewMoney(this.socket, "type2", 100);
        MoneyLogicNoSend moneyLogic = transferMoney(this.socket, "type1", "type2", 130);

        JSONObject jsonObject = JSONObject.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void dataShouldBeChangedAfterTransfer() throws Exception {
        UserLogicLoginTest.registerUser(this.socket, "username", "password");
        UserLogicLoginTest.loginUser(this.socket, "username", "password");

        createNewMoney(this.socket, "type1", 100);
        createNewMoney(this.socket, "type2", 100);
        transferMoney(this.socket, "type1", "type2", 30);

        MoneyCollection moneyCollection = new MoneyCollection();
        for (DBTable.DBData now : moneyCollection.getMoneyListData("username")) {
            if (now.object.get("typename").equals("type1")) {
                assertEquals(70.0, (double) now.object.get("value"), 1e-3);
            } else if (now.object.get("typename").equals("type2")) {
                assertEquals(130.0, (double) now.object.get("value"), 1e-3);
            } else {
                fail();
            }
        }
    }

}