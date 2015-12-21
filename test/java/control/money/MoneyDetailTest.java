package control.money;

import control.LogicTesting;
import model.db.DetailCollection;
import model.entity.DetailEntity;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/21.
 * it's the money detail test
 */
public class MoneyDetailTest extends LogicTesting {

    @Test
    public void create_money_detail_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);

        DetailCollection detailCollection = new DetailCollection();
        DetailEntity detailEntity = detailCollection.findLastDetail("username");
        assertEquals("createMoney", detailEntity.getEvent());
        assertEquals("type", detailEntity.getItem("moneyType"));
        assertEquals(100, Double.parseDouble(detailEntity.getItem("value")), 1e-3);
    }

    @Test
    public void remove_money_detail_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        removeMoney(this.socket, "type");

        DetailCollection detailCollection = new DetailCollection();
        DetailEntity detailEntity = detailCollection.findLastDetail("username");
        assertEquals("removeMoney", detailEntity.getEvent());
        assertEquals("type", detailEntity.getItem("moneyType"));
        assertEquals(0, Double.parseDouble(detailEntity.getItem("value")), 1e-3);
    }

    @Test
    public void rename_money_detail_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);
        renameMoney(this.socket, "type", "other");

        DetailCollection detailCollection = new DetailCollection();
        DetailEntity detailEntity = detailCollection.findLastDetail("username");
        assertEquals("renameMoney", detailEntity.getEvent());
        assertEquals("type", detailEntity.getItem("moneyType"));
        assertEquals(100, Double.parseDouble(detailEntity.getItem("value")), 1e-3);
    }

    @Test
    public void transfer_money_detail_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "a", 100);
        createNewMoney(this.socket, "b", 100);
        transferMoney(this.socket, "a", "b", 30);

        DetailCollection detailCollection = new DetailCollection();
        DetailEntity detailEntity = detailCollection.findLastDetail("username");
        assertEquals("transferMoney", detailEntity.getEvent());
        assertEquals("a", detailEntity.getItem("fromMoneyType"));
        assertEquals("b", detailEntity.getItem("toMoneyType"));
        assertEquals(70, Double.parseDouble(detailEntity.getItem("fromMoneyValue")), 1e-3);
        assertEquals(130, Double.parseDouble(detailEntity.getItem("toMoneyValue")), 1e-3);
    }

}
