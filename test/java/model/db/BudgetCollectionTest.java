package model.db;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xlo on 2015/11/26.
 * it's the testing code for budget collection
 */
public class BudgetCollectionTest extends DBTesting {

    @Test
    public void shouldHaveBudgetAfterAddBudget() throws Exception {
        BudgetCollection budgetCollection = new BudgetCollection();
        budgetCollection.addBudgetType("username", "type", 100, 100, (double) 0, (double) 0);
        budgetCollection.submit();

        assertNotNull(budgetCollection.getBudgetData("username", "type"));
    }

    @Test
    public void shouldGetNullWhenNotHaveData() throws Exception {
        BudgetCollection budgetCollection = new BudgetCollection();
        budgetCollection.addBudgetType("username", "type", 100, 100, (double) 0, (double) 0);
        budgetCollection.submit();

        assertNull(budgetCollection.getBudgetData("username", "type1"));
    }

    @Test
    public void shouldChangeAfterChangeData() throws Exception {
        BudgetCollection budgetCollection = new BudgetCollection();
        budgetCollection.addBudgetType("username", "type", 100, 100, (double) 0, (double) 0);
        budgetCollection.submit();

        budgetCollection.getBudget("username", "type").setIncome(1);
        budgetCollection.submit();

        assertEquals(1.0, budgetCollection.getBudgetData("username", "type").getIncome(), 1e-3);
    }

    @Test
    public void shouldHaveDifferentDataWithDifferentUser() throws  Exception {
        BudgetCollection budgetCollection = new BudgetCollection();
        budgetCollection.addBudgetType("user1", "type", 100, 100, (double) 0, (double) 0);
        budgetCollection.addBudgetType("user2", "type", 101, 101, (double) 0, (double) 0);
        budgetCollection.submit();

        assertEquals(101.0, budgetCollection.getBudgetData("user2", "type").getIncome(), 1e-3);
    }

    @Test
    public void shouldNotHaveDataAfterRemove() throws Exception {
        BudgetCollection budgetCollection = new BudgetCollection();
        budgetCollection.addBudgetType("username", "type", 100, 100, (double) 0, (double) 0);
        budgetCollection.submit();

        budgetCollection.removeBudget("username", "type");
        budgetCollection.submit();

        assertNull(budgetCollection.getBudgetData("username", "type"));
    }

    @Test
    public void shouldGetAllUserDataWhenQueryUserBudget() throws Exception {
        BudgetCollection budgetCollection = new BudgetCollection();
        budgetCollection.addBudgetType("username", "type1", 100, 100, (double) 0, (double) 0);
        budgetCollection.submit();
        budgetCollection.addBudgetType("username", "type2", 100, 100, (double) 0, (double) 0);
        budgetCollection.submit();

        assertEquals(2, budgetCollection.getBudgetListData("username").size());
    }

}