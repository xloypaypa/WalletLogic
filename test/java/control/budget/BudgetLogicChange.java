package control.budget;

import control.LogicTesting;
import control.tool.BudgetLogicNoSend;
import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.entity.BudgetEntity;
import model.entity.EdgeEntity;
import model.session.SessionManager;
import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by xlo on 2015/12/9.
 * it's the testing code for budget change
 */
public class BudgetLogicChange extends LogicTesting {

    @Test
    public void checkUrl() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        BudgetLogicNoSend budgetLogic = changeBudget(this.socket, "a", "c", "2", "3");

        assertEquals("/changeBudget", budgetLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldFailWhenRenameInvalidBudget() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        BudgetLogicNoSend budgetLogic = changeBudget(this.socket, "a", "c", "2", "3");

        JSONObject jsonObject = JSONObject.fromObject(new String(budgetLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldFailWhenNotLogin() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "2");
        SessionManager.getSessionManager().getSessionMessage(this.socket).setUsername(null);
        BudgetLogicNoSend budgetLogic = changeBudget(this.socket, "a", "c", "2", "3");

        JSONObject jsonObject = JSONObject.fromObject(new String(budgetLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldOkWhenRenameSomeType() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "2");
        BudgetLogicNoSend budgetLogic = changeBudget(this.socket, "a", "c", "2", "3");

        JSONObject jsonObject = JSONObject.fromObject(new String(budgetLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("ok", jsonObject.getString("result"));
    }

    @Test
    public void dataShouldRenameAfterRename() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "2");
        changeBudget(this.socket, "a", "c", "2", "3");

        BudgetCollection budgetCollection = new BudgetCollection();
        BudgetEntity dbData = budgetCollection.getBudgetData("username", "c");
        assertNotNull(dbData);
        assertEquals("c", dbData.getName());
        assertEquals(2.0, dbData.getIncome(), 1e-3);
        assertEquals(3.0, dbData.getExpenditure(), 1e-3);
    }

    @Test
    public void shouldUpdateRelativeEdgeWhenChangeBudget() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "2");
        createNewBudget(this.socket, "b", "1", "2");
        addEdge(this.socket, "a", "b", "c");
        changeBudget(this.socket, "a", "c", "2", "3");

        BudgetEdgeCollection edgeCollection = new BudgetEdgeCollection();
        EdgeEntity dbData = edgeCollection.getEdgeData("username", "c", "b");
        assertNotNull(dbData);
    }

}
