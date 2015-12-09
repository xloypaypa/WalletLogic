package control.money;

import control.LogicTesting;
import control.tool.MoneyLogicNoSend;
import model.db.MoneyCollection;
import model.session.SessionManager;
import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/11/26.
 * it's the testing code for rename money
 */
public class MoneyLogicRenameTest extends LogicTesting {

    @Test
    public void checkUrl() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        MoneyLogicNoSend moneyLogic = renameMoney(this.socket, "type", "name");

        assertEquals("/renameMoney", moneyLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldFailWhenRenameInvalidMoney() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        MoneyLogicNoSend moneyLogic = renameMoney(this.socket, "type2", "name");

        JSONObject jsonObject = JSONObject.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldFailWhenNotLogin() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        SessionManager.getSessionManager().getSessionMessage(this.socket).setUsername(null);
        MoneyLogicNoSend moneyLogic = renameMoney(this.socket, "type", "name");

        JSONObject jsonObject = JSONObject.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldOkWhenRenameSomeType() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        MoneyLogicNoSend moneyLogic = renameMoney(this.socket, "type", "name");

        JSONObject jsonObject = JSONObject.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("ok", jsonObject.getString("result"));
    }

    @Test
    public void dataShouldRenameAfterRename() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        renameMoney(this.socket, "type", "name");

        MoneyCollection moneyCollection = new MoneyCollection();
        assertEquals("name", moneyCollection.getMoneyListData("username").get(0).object.get("typename"));
    }

}