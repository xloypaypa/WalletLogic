package control.edge;

import control.LogicTesting;
import control.tool.EdgeLogicNoSend;
import model.db.BudgetEdgeCollection;
import net.sf.json.JSONObject;
import org.bson.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/9.
 * it's the testing code for edge logic create
 */
public class EdgeLogicAddTest extends LogicTesting {

    @Test
    public void checkUrl() throws Exception {
        EdgeLogicNoSend edgeLogic = addEdge(this.socket, "a", "b", "c");

        assertEquals("/addEdge", edgeLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldFailWhenAddNewEdgeWithoutLogin() throws Exception {
        EdgeLogicNoSend edgeLogic = addEdge(this.socket, "a", "b", "c");

        JSONObject jsonObject = JSONObject.fromObject(new String(edgeLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldFailWhenDoNotHaveBudgetType() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        EdgeLogicNoSend edgeLogic = addEdge(this.socket, "a", "b", "c");

        JSONObject jsonObject = JSONObject.fromObject(new String(edgeLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldFailWhenAddSameEdge() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        createNewBudget(this.socket, "a", "1", "2");
        createNewBudget(this.socket, "b", "2", "3");

        addEdge(this.socket, "a", "b", "c");
        EdgeLogicNoSend edgeLogic = addEdge(this.socket, "a", "b", "c");

        JSONObject jsonObject = JSONObject.fromObject(new String(edgeLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldOkWhenCreateNewEdge() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        createNewBudget(this.socket, "a", "1", "2");
        createNewBudget(this.socket, "b", "2", "3");

        EdgeLogicNoSend edgeLogic = addEdge(this.socket, "a", "b", "c");

        JSONObject jsonObject = JSONObject.fromObject(new String(edgeLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("ok", jsonObject.getString("result"));
    }

    @Test
    public void DBShouldHaveDataAfterAddMoney() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        createNewBudget(this.socket, "a", "1", "2");
        createNewBudget(this.socket, "b", "2", "3");

        addEdge(this.socket, "a", "b", "c");

        BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
        assertEquals(1, budgetEdgeCollection.getAllDataListData(new Document()).size());
    }

    @Test
    public void shouldFailWhenThereAreCircle() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        createNewBudget(this.socket, "a", "1", "2");
        createNewBudget(this.socket, "b", "2", "3");

        addEdge(this.socket, "b", "a", "c");
        EdgeLogicNoSend edgeLogic = addEdge(this.socket, "a", "b", "c");

        JSONObject jsonObject = JSONObject.fromObject(new String(edgeLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

}
