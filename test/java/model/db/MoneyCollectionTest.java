package model.db;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xlo on 2015/11/26.
 * it's the testing code for money collection
 */
public class MoneyCollectionTest extends DBTesting {

    @Test
    public void shouldHaveMoneyAfterAddMoney() throws Exception {
        MoneyCollection moneyCollection = new MoneyCollection();
        moneyCollection.createMoney("username", "type", 100);
        moneyCollection.submit();

        assertNotNull(moneyCollection.getMoneyData("username", "type"));
    }

    @Test
    public void shouldGetNullWhenNotHaveData() throws Exception {
        MoneyCollection moneyCollection = new MoneyCollection();
        moneyCollection.createMoney("username", "type", 100);
        moneyCollection.submit();

        assertNull(moneyCollection.getMoneyData("username1", "type"));
    }

    @Test
    public void shouldChangeAfterChangeData() throws Exception {
        MoneyCollection moneyCollection = new MoneyCollection();
        moneyCollection.createMoney("username", "type", 100);
        moneyCollection.submit();

        moneyCollection.getMoney("username", "type").object.put("value", 101.0);
        moneyCollection.submit();

        assertEquals(101.0, (double) moneyCollection.getMoneyData("username", "type").object.get("value"), 1e-3);
    }

    @Test
    public void shouldHaveDifferentDataWithDifferentUser() throws  Exception {
        MoneyCollection moneyCollection = new MoneyCollection();
        moneyCollection.createMoney("user1", "type", 100);
        moneyCollection.createMoney("user2", "type", 101);
        moneyCollection.submit();

        assertEquals(101.0, (double)moneyCollection.getMoneyData("user2", "type").object.get("value"), 1e-3);
    }

    @Test
    public void shouldNotHaveDataAfterRemove() throws Exception {
        MoneyCollection moneyCollection = new MoneyCollection();
        moneyCollection.createMoney("username", "type", 100);
        moneyCollection.submit();

        moneyCollection.removeMoney("username", "type");
        moneyCollection.submit();

        assertNull(moneyCollection.getMoneyData("username", "type"));
    }

    @Test
    public void shouldGetAllUserDataWhenQueryUserMoney() throws Exception {
        MoneyCollection moneyCollection = new MoneyCollection();
        moneyCollection.createMoney("username", "type1", 100);
        moneyCollection.submit();
        moneyCollection.createMoney("username", "type2", 101);
        moneyCollection.submit();

        assertEquals(2, moneyCollection.getMoneyListData("username").size());
    }

}