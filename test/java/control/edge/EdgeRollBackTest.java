package control.edge;

import control.LogicTesting;
import model.db.BudgetEdgeCollection;
import org.bson.Document;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/18.
 * it's the edge roll back test
 */
public class EdgeRollBackTest extends LogicTesting {

    @Test
    public void create_edge_roll_back_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "1");
        createNewBudget(this.socket, "b", "1", "1");
        addEdge(this.socket, "a", "b", "c");
        rollBack(this.socket);

        assertEquals(0, new BudgetEdgeCollection().getAllDataListData(new Document()).size());
    }

    @Test
    public void remove_edge_roll_back_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "1");
        createNewBudget(this.socket, "b", "1", "1");
        addEdge(this.socket, "a", "b", "c");
        removeEdge(this.socket, "a", "b");
        rollBack(this.socket);

        assertEquals(1, new BudgetEdgeCollection().getAllDataListData(new Document()).size());
    }

    @Test
    public void update_edge_roll_back_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "1");
        createNewBudget(this.socket, "b", "1", "1");
        addEdge(this.socket, "a", "b", "c");
        updateEdge(this.socket, "a", "b", "d");
        rollBack(this.socket);

        assertEquals("c", new BudgetEdgeCollection().getAllDataListData(new Document()).get(0).object.get("script"));
    }

}
