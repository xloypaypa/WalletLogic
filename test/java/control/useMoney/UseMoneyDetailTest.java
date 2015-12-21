package control.useMoney;

import control.LogicTesting;
import model.db.DetailCollection;
import model.entity.DetailEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/21.
 * it's the use money detail test
 */
public class UseMoneyDetailTest extends LogicTesting {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        createNewMoney(this.socket, "a", 100);

        createNewBudget(this.socket, "a", "1", "2");
        createNewBudget(this.socket, "b", "1", "2");
        createNewBudget(this.socket, "c", "1", "2");
        createNewBudget(this.socket, "d", "1", "2");
        createNewBudget(this.socket, "e", "1", "2");
        createNewBudget(this.socket, "f", "1", "2");

        addEdge(this.socket, "a", "b", "(defn fff[x] x) fff");
        addEdge(this.socket, "b", "c", "(defn fff[x] x) fff");
        addEdge(this.socket, "b", "d", "(defn fff[x] x) fff");
        addEdge(this.socket, "c", "e", "(defn fff[x] x) fff");
        addEdge(this.socket, "d", "e", "(defn fff[x] x) fff");
        addEdge(this.socket, "d", "f", "(defn fff[x] (* x 0.8)) fff");
    }

    @Test
    public void money_detail_test() {
        expenditure(this.socket, "a", "b", "10");

        DetailEntity detailEntity = new DetailCollection().findLastDetail("username");
        assertEquals("expenditure", detailEntity.getEvent());
        assertEquals("a", detailEntity.getItem("moneyType"));
        assertEquals(90, Double.parseDouble(detailEntity.getItem("value")), 1e-3);
    }

    @Test
    public void roll_back_budget_test() {
        income(this.socket, "a", "b", "10");

        DetailEntity detailEntity = new DetailCollection().findLastDetail("username");
        assertEquals("income", detailEntity.getEvent());
        assertEquals("a", detailEntity.getItem("moneyType"));
        assertEquals(110, Double.parseDouble(detailEntity.getItem("value")), 1e-3);
    }
}
