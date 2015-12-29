package control.logic.userDataBuilder;

import control.logic.manager.BudgetManager;
import control.logic.manager.EdgeManager;
import control.logic.manager.MoneyManager;
import control.logic.manager.UseMoneyManager;
import org.bson.BsonArray;
import org.bson.Document;

import java.util.ArrayList;

/**
 * Created by xlo on 2015/12/18.
 * it's the user data builder
 */
public class UserRollBackBuilder extends Builder {

    private ArrayList<Document> rollBackArray;

    public UserRollBackBuilder() {
        rollBackArray = new ArrayList<>();
    }

    public ArrayList<Document> getRollBackArray() {
        return rollBackArray;
    }

    public void createMoney(String typename) {
        rollBackArray.add(this.managerCallMessageBuilder(MoneyManager.class, "removeMoney", typename));
    }

    public void renameMoney(String typename, String newName) {
        rollBackArray.add(this.managerCallMessageBuilder(MoneyManager.class, "renameMoney", newName, typename));
    }

    public void removeMoney(String typename, String value) {
        rollBackArray.add(this.managerCallMessageBuilder(MoneyManager.class, "createMoney", typename, value));
    }

    public void transferMoney(String from, String to, String value) {
        rollBackArray.add(this.managerCallMessageBuilder(MoneyManager.class, "transferMoney", to, from, value));
    }

    public void addEdge(String from, String to) {
        rollBackArray.add(this.managerCallMessageBuilder(EdgeManager.class, "removeEdge", from, to));
    }

    public void removeEdge(String from, String to, String script) {
        rollBackArray.add(this.managerCallMessageBuilder(EdgeManager.class, "addEdge", from, to, script));
    }

    public void updateEdge(String from, String to, String newFrom, String newTo, String script) {
        rollBackArray.add(this.managerCallMessageBuilder(EdgeManager.class, "updateEdge", newFrom, newTo, from, to, script));
    }

    public void createBudget(String typename) {
        rollBackArray.add(this.managerCallMessageBuilder(BudgetManager.class, "removeBudget", typename));
    }

    public void removeBudget(String typename, String income, String expenditure, String nowIncome, String nowExpenditure) {
        rollBackArray.add(this.managerCallMessageBuilder(BudgetManager.class, "createBudget", typename, income, expenditure, nowIncome, nowExpenditure));
    }

    public void changeBudget(String typename, String pastIncome, String pastExpenditure, String newName) {
        rollBackArray.add(this.managerCallMessageBuilder(BudgetManager.class, "changeBudget", newName, typename, pastIncome, pastExpenditure));
    }

    public void putMoneyValue(String typename, String past) {
        rollBackArray.add(this.managerCallMessageBuilder(UseMoneyManager.class, "putMoneyValue", typename, past));
    }

    public void putBudgetValue(String typename, String pastIncome, String pastExpenditure) {
        rollBackArray.add(this.managerCallMessageBuilder(UseMoneyManager.class, "putBudgetValue", typename, pastIncome, pastExpenditure));
    }
}
