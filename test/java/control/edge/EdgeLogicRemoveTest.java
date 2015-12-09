package control.edge;

import control.LogicTesting;
import control.tool.EdgeLogicNoSend;
import model.db.BudgetEdgeCollection;
import model.session.SessionManager;
import net.sf.json.JSONObject;
import org.bson.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/9.
 * it's the testing code for edge logic remove
 */
public class EdgeLogicRemoveTest extends LogicTesting {

    @Test
    public void checkUrl() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        EdgeLogicNoSend edgeLogic = removeEdge(this.socket, "a", "b");

        assertEquals("/removeEdge", edgeLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldFailWhenRemoveInvalidEdge() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        EdgeLogicNoSend edgeLogic = removeEdge(this.socket, "a", "b");

        JSONObject jsonObject = JSONObject.fromObject(new String(edgeLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldFailWhenNotLogin() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        createNewBudget(this.socket, "a", "1", "2");
        createNewBudget(this.socket, "b", "2", "3");
        addEdge(this.socket, "a", "b", "c");
        SessionManager.getSessionManager().getSessionMessage(this.socket).setUsername(null);
        EdgeLogicNoSend edgeLogic = removeEdge(this.socket, "a", "b");

        JSONObject jsonObject = JSONObject.fromObject(new String(edgeLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldOkWhenRemoveSomeEdge() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        createNewBudget(this.socket, "a", "1", "2");
        createNewBudget(this.socket, "b", "2", "3");
        addEdge(this.socket, "a", "b", "c");
        EdgeLogicNoSend edgeLogic = removeEdge(this.socket, "a", "b");

        JSONObject jsonObject = JSONObject.fromObject(new String(edgeLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("ok", jsonObject.getString("result"));
    }

    @Test
    public void shouldNoDataWhenRemove() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        createNewBudget(this.socket, "a", "1", "2");
        createNewBudget(this.socket, "b", "2", "3");
        addEdge(this.socket, "a", "b", "c");
        removeEdge(this.socket, "a", "b");

        BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
        assertEquals(0, budgetEdgeCollection.findEdgeList(new Document()).size());
    }

}
