package control.useMoney;

import control.LogicTesting;
import control.tool.UseMoneyLogicNoSend;
import model.db.BudgetCollection;
import model.db.MoneyCollection;
import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/11.
 * it's the testing code for use money logic income
 */
public class UseMoneyLogicIncome extends LogicTesting {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        createNewMoney(this.socket, "a", 0);

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
    public void checkUrl() throws Exception {
        UseMoneyLogicNoSend useMoneyLogic = income(this.socket, "a", "b", "10");

        assertEquals("/income", useMoneyLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void should_budget_value_equal_10_when_income_in_b_10_except_f_and_e() {
        income(this.socket, "a", "b", "10");
        BudgetCollection budgetCollection = new BudgetCollection();
        assertEquals(10, budgetCollection.getBudgetData("username", "d").getNowIncome(), 1e-3);
    }

    @Test
    public void should_money_value_equal_10_when_income_in_b_10() {
        income(this.socket, "a", "b", "10");
        MoneyCollection moneyCollection = new MoneyCollection();
        assertEquals(10, moneyCollection.getMoneyData("username", "a").getValue(), 1e-3);
    }

    @Test
    public void should_fail_when_income_error_budget() throws Exception {
        UseMoneyLogicNoSend useMoneyLogic = income(this.socket, "a", "x", "10");

        JSONObject jsonObject = JSONObject.fromObject(new String(useMoneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void should_fail_when_income_error_money() throws Exception {
        UseMoneyLogicNoSend useMoneyLogic = income(this.socket, "x", "b", "10");

        JSONObject jsonObject = JSONObject.fromObject(new String(useMoneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void should_data_not_change_when_income_fail() throws Exception {
        income(this.socket, "x", "b", "10");
        BudgetCollection budgetCollection = new BudgetCollection();
        assertEquals(0, budgetCollection.getBudgetData("username", "d").getNowIncome(), 1e-3);
    }

}
