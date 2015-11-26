package model.db;

import org.bson.Document;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xlo on 2015/11/26.
 * it's the testing code for budget edge
 */
public class BudgetEdgeCollectionTest extends DBTesting {

    @Test
    public void shouldHaveEdgeAfterAddEdge() throws Exception {
        BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
        budgetEdgeCollection.addEdge("username", "from", "to", "");
        budgetEdgeCollection.submit();

        assertNotNull(budgetEdgeCollection.getEdgeData("username", "from", "to"));
    }

    @Test
    public void shouldGetNullWhenNotHaveData() throws Exception {
        BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
        budgetEdgeCollection.addEdge("username", "from", "to", "");
        budgetEdgeCollection.submit();

        assertNull(budgetEdgeCollection.getEdgeData("username", "from", "to2"));
    }

    @Test
    public void shouldChangeAfterChangeData() throws Exception {
        BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
        budgetEdgeCollection.addEdge("username", "from", "to", "");
        budgetEdgeCollection.submit();

        budgetEdgeCollection.getEdge("username", "from", "to").object.put("script", "1");
        budgetEdgeCollection.submit();

        assertEquals("1", budgetEdgeCollection.getEdgeData("username", "from", "to").object.get("script"));
    }

    @Test
    public void shouldHaveDifferentDataWithDifferentUser() throws  Exception {
        BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
        budgetEdgeCollection.addEdge("user1", "from", "to", "1");
        budgetEdgeCollection.addEdge("user2", "from", "to", "2");
        budgetEdgeCollection.submit();

        assertEquals("2", budgetEdgeCollection.getEdgeData("user2", "from", "to").object.get("script"));
    }

    @Test
    public void shouldNotHaveDataAfterRemove() throws Exception {
        BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
        budgetEdgeCollection.addEdge("username", "from", "to", "");
        budgetEdgeCollection.submit();

        budgetEdgeCollection.remove("username", "from", "to");
        budgetEdgeCollection.submit();

        assertNull(budgetEdgeCollection.getEdgeData("username", "from", "to"));
    }

    @Test
    public void shouldGetAllUserDataWhenQueryEdge() throws Exception {
        BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
        budgetEdgeCollection.addEdge("user1", "from", "to", "1");
        budgetEdgeCollection.addEdge("user2", "from", "to", "2");
        budgetEdgeCollection.submit();

        assertEquals(2, budgetEdgeCollection.findEdgeList(new Document("from", "from")).size());
    }

}