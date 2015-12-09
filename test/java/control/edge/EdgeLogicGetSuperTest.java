package control.edge;

import control.LogicTesting;
import control.tool.EdgeLogicNoSend;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/9.
 * it's the testing code for edge logic get super
 */
public class EdgeLogicGetSuperTest extends LogicTesting {

    @Test
    public void checkUrl() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "2");
        createNewBudget(this.socket, "b", "1", "2");
        addEdge(this.socket, "a", "b", "c");
        EdgeLogicNoSend edgeLogic = getEdgeSuper(this.socket, "a");

        assertEquals("/getSuperEdgeList", edgeLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldFailWhenNotLogin() throws Exception {
        EdgeLogicNoSend edgeLogic = getEdgeSuper(this.socket, "a");

        JSONObject jsonObject = JSONObject.fromObject(new String(edgeLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

    @Test
    public void shouldSendDataWhenGetData() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "2");
        createNewBudget(this.socket, "b", "1", "2");
        addEdge(this.socket, "a", "b", "c");
        EdgeLogicNoSend edgeLogic = getEdgeSuper(this.socket, "a");

        JSONArray jsonArray = JSONArray.fromObject(new String(edgeLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals(1, jsonArray.size());
    }

}
