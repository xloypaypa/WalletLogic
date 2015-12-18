package control.money;

import control.LogicTesting;
import model.db.DBTable;
import model.db.MoneyCollection;
import org.bson.Document;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/18.
 * it's the roll back test
 */
public class MoneyRollBackTest extends LogicTesting {

    @Test
    public void create_money_roll_back_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        rollBack(this.socket);

        assertEquals(0, new MoneyCollection().getAllDataListData(new Document()).size());
    }

    @Test
    public void remove_money_roll_back_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        removeMoney(this.socket, "type");
        rollBack(this.socket);

        assertEquals(1, new MoneyCollection().getAllDataListData(new Document()).size());
    }

    @Test
    public void rename_money_roll_back_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        renameMoney(this.socket, "type", "a");
        rollBack(this.socket);

        assertEquals("type", new MoneyCollection().getAllDataListData(new Document()).get(0).object.get("typename"));
    }

    @Test
    public void transfer_money_roll_back_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "a", 100);
        createNewMoney(this.socket, "b", 100);
        transferMoney(this.socket, "a", "b", 30);
        rollBack(this.socket);

        DBTable.DBData data = new MoneyCollection().getMoneyData("username", "a");
        assertEquals(100, (Double) data.object.get("value"), 1e-3);
    }

}
