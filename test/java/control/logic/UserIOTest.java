package control.logic;

import control.LogicTesting;
import control.logic.userDataFormat.BudgetNode;
import control.logic.userDataFormat.UserIO;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by xlo on 15/12/10.
 * it's the testing code for user io
 */
public class UserIOTest extends LogicTesting {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");

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
    }

    @Test
    public void should_get_5_nodes_when_income_in_b() throws ReflectiveOperationException {
        UserIO userIO = new UserIO("username");
        assertEquals(5, userIO.ifIncome("b", 10).size());
    }

    @Test
    public void should_not_have_a_when_income_in_b_10() throws ReflectiveOperationException {
        UserIO userIO = new UserIO("username");
        Collection<BudgetNode> budgetNodes = userIO.ifIncome("b", 10);
        budgetNodes.stream().filter(now -> now.getName().equals("a")).forEach(now -> fail());
    }

    @Test
    public void should_value_equal_10_when_income_in_b_10_except_f_and_e() throws ReflectiveOperationException {
        UserIO userIO = new UserIO("username");
        Collection<BudgetNode> budgetNodes = userIO.ifIncome("b", 10);
        budgetNodes.stream().filter(now -> !now.getName().equals("f") && !now.getName().equals("e"))
                .forEach(now -> assertEquals(10, now.getNowIncome(), 1e-3));
    }

    @Test
    public void should_e_value_equal_20_when_income_in_b_10() throws ReflectiveOperationException {
        UserIO userIO = new UserIO("username");
        Collection<BudgetNode> budgetNodes = userIO.ifIncome("b", 10);
        budgetNodes.stream().filter(now -> now.getName().equals("e"))
                .forEach(now -> assertEquals(20, now.getNowIncome(), 1e-3));
    }

    @Test
    public void should_f_value_equal_8_when_expenditure_in_b_10() throws ReflectiveOperationException {
        UserIO userIO = new UserIO("username");
        Collection<BudgetNode> budgetNodes = userIO.ifExpenditure("b", 10);
        budgetNodes.stream().filter(now -> now.getName().equals("f"))
                .forEach(now -> assertEquals(8, now.getNowExpenditure(), 1e-3));
    }
}