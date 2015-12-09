package control.budget;

import control.LogicTesting;
import control.tool.BudgetLogicNoSend;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/9.
 * it's the testing code for budget logic get method
 */
public class BudgetLogicGetTest extends LogicTesting {

    @Test
    public void checkUrl() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "type", "1", "2");
        BudgetLogicNoSend budgetLogic = getBudget(this.socket);

        assertEquals("/getBudget", budgetLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldFailWhenNotLogin() throws Exception {
        BudgetLogicNoSend budgetLogic = getBudget(this.socket);

        JSONObject jsonObject = JSONObject.fromObject(new String(budgetLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldSendDataWhenGetData() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        createNewBudget(this.socket, "a", "1", "2");

        BudgetLogicNoSend budgetLogic = getBudget(this.socket);

        JSONArray jsonArray = JSONArray.fromObject(new String(budgetLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals(1, jsonArray.size());
    }

}
