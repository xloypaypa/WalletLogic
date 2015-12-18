package control.budget;

import control.LogicTesting;
import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.db.DBTable;
import org.bson.Document;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/18.
 * it's the budget roll back test
 */
public class BudgetRollBackTest extends LogicTesting {

    @Test
    public void create_budget_roll_back_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "1");
        rollBack(this.socket);

        assertEquals(0, new BudgetCollection().getAllDataListData(new Document()).size());
    }

    @Test
    public void remove_budget_roll_back_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "1");
        removeBudget(this.socket, "a");
        rollBack(this.socket);

        assertEquals(1, new BudgetCollection().getAllDataListData(new Document()).size());
    }

    @Test
    public void update_budget_roll_back_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "1");
        changeBudget(this.socket, "a", "b", "2", "2");
        rollBack(this.socket);

        assertEquals("a", new BudgetCollection().getAllDataListData(new Document()).get(0).object.get("typename"));
    }

    @Test
    public void remove_budget_with_edge_roll_back_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "1");
        createNewBudget(this.socket, "b", "1", "1");
        addEdge(this.socket, "a", "b", "c");
        removeBudget(this.socket, "a");
        rollBack(this.socket);

        assertEquals(1, new BudgetEdgeCollection().getAllDataListData(new Document()).size());
    }

    @Test
    public void update_budget_with_edge_roll_back_test() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewBudget(this.socket, "a", "1", "1");
        createNewBudget(this.socket, "b", "1", "1");
        addEdge(this.socket, "a", "b", "c");
        changeBudget(this.socket, "a", "d", "1", "1");
        rollBack(this.socket);

        assertEquals(1, new BudgetEdgeCollection().getAllDataListData(new Document("from", "a")).size());
    }

}
