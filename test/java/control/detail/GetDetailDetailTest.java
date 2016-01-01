package control.detail;

import control.LogicTesting;
import control.tool.DetailLogicNoSend;
import model.db.DetailCollection;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/30.
 * it's the get all detail test
 */
public class GetDetailDetailTest extends LogicTesting {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

        createNewMoney(this.socket, "a", 100);
        createNewMoney(this.socket, "b", 100);

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

        transferMoney(this.socket, "a", "b", 30);
        income(this.socket, "a", "b", "10");
        expenditure(this.socket, "b", "c", "20");
    }

    @Test
    public void checkUrl() {
        String id = new DetailCollection().findDetails("username", new Date(0), new Date()).get(3).getId();

        DetailLogicNoSend detailLogic = getDetailDetail(this.socket, id);
        assertEquals("/getDetailDetail", detailLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void checkData() {
        String id = new DetailCollection().findDetails("username", new Date(0), new Date()).get(9).getId();

        DetailLogicNoSend detailLogic = getDetailDetail(this.socket, id);
        JSONObject jsonObject = JSONObject.fromObject(new String(detailLogic.getEvent().getMessage().get(0).getValue()));
        assertEquals("createBudget", ((JSONObject) ((JSONObject) jsonObject.get("result")).get("message")).getString("event"));
    }

}
