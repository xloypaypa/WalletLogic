package control.detail;

import control.LogicTesting;
import control.tool.DetailLogicNoSend;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/21.
 * it's the get money detail test
 */
public class GetMoneyDetailTest extends LogicTesting {

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
    public void should_have_5_detail() {
        DetailLogicNoSend detailLogic = getMoneyDetail(this.socket, "0", new Date().getTime() + "");
        JSONArray jsonArray = JSONArray.fromObject(new String(detailLogic.getEvent().getMessage().get(0).getValue()));
        assertEquals(6, jsonArray.size());
    }

    @Test
    public void should_1st_detail_is_a_100() {
        DetailLogicNoSend detailLogic = getMoneyDetail(this.socket, "0", new Date().getTime() + "");
        JSONArray jsonArray = JSONArray.fromObject(new String(detailLogic.getEvent().getMessage().get(0).getValue()));
        JSONObject jsonObject = (JSONObject) jsonArray.get(5);
        assertEquals("a", jsonObject.getString("moneyType"));
        assertEquals(100, jsonObject.getDouble("value"), 1e-3);
    }

    @Test
    public void should_2nd_detail_is_b_100() {
        DetailLogicNoSend detailLogic = getMoneyDetail(this.socket, "0", new Date().getTime() + "");
        JSONArray jsonArray = JSONArray.fromObject(new String(detailLogic.getEvent().getMessage().get(0).getValue()));
        JSONObject jsonObject = (JSONObject) jsonArray.get(4);
        assertEquals("b", jsonObject.getString("moneyType"));
        assertEquals(100, jsonObject.getDouble("value"), 1e-3);
    }

    @Test
    public void should_3rd_detail_is_a_70() {
        DetailLogicNoSend detailLogic = getMoneyDetail(this.socket, "0", new Date().getTime() + "");
        JSONArray jsonArray = JSONArray.fromObject(new String(detailLogic.getEvent().getMessage().get(0).getValue()));
        JSONObject jsonObject = (JSONObject) jsonArray.get(3);
        assertEquals("a", jsonObject.getString("moneyType"));
        assertEquals(70, jsonObject.getDouble("value"), 1e-3);
    }

    @Test
    public void should_4th_detail_is_b_130() {
        DetailLogicNoSend detailLogic = getMoneyDetail(this.socket, "0", new Date().getTime() + "");
        JSONArray jsonArray = JSONArray.fromObject(new String(detailLogic.getEvent().getMessage().get(0).getValue()));
        JSONObject jsonObject = (JSONObject) jsonArray.get(2);
        assertEquals("b", jsonObject.getString("moneyType"));
        assertEquals(130, jsonObject.getDouble("value"), 1e-3);
    }

    @Test
    public void should_5th_detail_is_a_80() {
        DetailLogicNoSend detailLogic = getMoneyDetail(this.socket, "0", new Date().getTime() + "");
        JSONArray jsonArray = JSONArray.fromObject(new String(detailLogic.getEvent().getMessage().get(0).getValue()));
        JSONObject jsonObject = (JSONObject) jsonArray.get(1);
        assertEquals("a", jsonObject.getString("moneyType"));
        assertEquals(80, jsonObject.getDouble("value"), 1e-3);
    }

    @Test
    public void should_6th_detail_is_b_110() {
        DetailLogicNoSend detailLogic = getMoneyDetail(this.socket, "0", new Date().getTime() + "");
        JSONArray jsonArray = JSONArray.fromObject(new String(detailLogic.getEvent().getMessage().get(0).getValue()));
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        assertEquals("b", jsonObject.getString("moneyType"));
        assertEquals(110, jsonObject.getDouble("value"), 1e-3);
    }

}
