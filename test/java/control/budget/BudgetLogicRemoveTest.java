package control.budget;

import control.LogicTesting;
import control.tool.BudgetLogicNoSend;
import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.session.SessionManager;
import net.sf.json.JSONObject;
import org.bson.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/9.
 * it's the testing code for budget remove
 */
public class BudgetLogicRemoveTest extends LogicTesting {

    @Test
    public void checkUrl() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        BudgetLogicNoSend budgetLogic = removeBudget(this.socket, "type");

        assertEquals("/removeBudget", budgetLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldFailWhenRemoveInvalidBudget() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        BudgetLogicNoSend budgetLogic = removeBudget(this.socket, "type");

        JSONObject jsonObject = JSONObject.fromObject(new String(budgetLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldFailWhenNotLogin() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "type", "1", "2");
        SessionManager.getSessionManager().getSessionMessage(this.socket).setUsername(null);
        BudgetLogicNoSend budgetLogic = removeBudget(this.socket, "type");

        JSONObject jsonObject = JSONObject.fromObject(new String(budgetLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldOkWhenRemoveSomeMoney() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "type", "1", "2");
        BudgetLogicNoSend budgetLogic = removeBudget(this.socket, "type");

        JSONObject jsonObject = JSONObject.fromObject(new String(budgetLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("ok", jsonObject.getString("result"));
    }

    @Test
    public void shouldNoDataWhenRemove() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "type", "1", "2");
        removeBudget(this.socket, "type");

        BudgetCollection budgetCollection = new BudgetCollection();
        assertEquals(0, budgetCollection.getBudgetListData("username").size());
    }

    @Test
    public void shouldRemoveRelativeEdgeWhenRemoveBudget() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "2");
        createNewBudget(this.socket, "b", "1", "2");
        addEdge(this.socket, "a", "b", "c");
        removeBudget(this.socket, "a");

        BudgetEdgeCollection edgeCollection = new BudgetEdgeCollection();
        assertEquals(0, edgeCollection.getAllDataListData(new Document()).size());
    }

}
