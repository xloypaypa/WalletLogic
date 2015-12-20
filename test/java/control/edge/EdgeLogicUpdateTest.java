package control.edge;

import control.LogicTesting;
import control.tool.EdgeLogicNoSend;
import model.db.BudgetEdgeCollection;
import model.session.SessionManager;
import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/9.
 * it's the testing code for edge logic update
 */
public class EdgeLogicUpdateTest extends LogicTesting {

    @Test
    public void checkUrl() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        EdgeLogicNoSend edgeLogic = updateEdge(this.socket, "a", "b", "c");

        assertEquals("/updateEdge", edgeLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldFailWhenRenameInvalidEdge() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        EdgeLogicNoSend edgeLogic = updateEdge(this.socket, "a", "b", "c");

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
        EdgeLogicNoSend edgeLogic = updateEdge(this.socket, "a", "b", "c");

        JSONObject jsonObject = JSONObject.fromObject(new String(edgeLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldOkWhenRenameSomeType() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        createNewBudget(this.socket, "a", "1", "2");
        createNewBudget(this.socket, "b", "2", "3");

        addEdge(this.socket, "a", "b", "c");
        EdgeLogicNoSend edgeLogic = updateEdge(this.socket, "a", "b", "c");

        JSONObject jsonObject = JSONObject.fromObject(new String(edgeLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("ok", jsonObject.getString("result"));
    }

    @Test
    public void dataShouldRenameAfterRename() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        createNewBudget(this.socket, "a", "1", "2");
        createNewBudget(this.socket, "b", "2", "3");

        addEdge(this.socket, "a", "b", "c");
        updateEdge(this.socket, "a", "b", "d");

        BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
        assertEquals("d", budgetEdgeCollection.getEdgeData("username", "a", "b").getScript());
    }

}
