package control.budget;

import control.LogicTesting;
import control.tool.BudgetLogicNoSend;
import control.user.UserLogicLoginTest;
import model.db.BudgetCollection;
import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/9.
 * it's the testing code for budget create
 */
public class BudgetLogicCreateTest extends LogicTesting {

    @Test
    public void checkUrl() throws Exception {
        BudgetLogicNoSend budgetLogic = new BudgetLogicNoSend(this.socket);
        budgetLogic.createBudget("ab", "1", "2");
        budgetLogic.waitEventEnd();

        assertEquals("/createBudget", budgetLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldFailWhenCreateNewMoneyWithoutLogin() throws Exception {
        BudgetLogicNoSend budgetLogic = new BudgetLogicNoSend(this.socket);
        budgetLogic.createBudget("ab", "1", "2");
        budgetLogic.waitEventEnd();

        JSONObject jsonObject = JSONObject.fromObject(new String(budgetLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldFailWhenCreateSameBudget() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        createNewBudget(this.socket, "a", "1", "2");

        BudgetLogicNoSend budgetLogic = createNewBudget(this.socket, "a", "1", "2");

        JSONObject jsonObject = JSONObject.fromObject(new String(budgetLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldOkWhenCreateNewBudget() throws Exception {
        UserLogicLoginTest.registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        BudgetLogicNoSend budgetLogic = createNewBudget(this.socket, "a", "1", "2");

        JSONObject jsonObject = JSONObject.fromObject(new String(budgetLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("ok", jsonObject.getString("result"));
    }

    @Test
    public void DBShouldHaveDataAfterAddMoney() throws Exception {
        UserLogicLoginTest.registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "2");

        BudgetCollection budgetCollection = new BudgetCollection();
        assertEquals(1, budgetCollection.getBudgetListData("username").size());
    }
}
