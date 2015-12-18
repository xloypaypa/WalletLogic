package control.logic.manager;

import control.logic.BudgetNode;
import control.logic.UserIO;
import model.db.BudgetCollection;
import model.db.DBTable;
import model.db.MoneyCollection;

import java.util.Collection;

/**
 * Created by xlo on 2015/12/18.
 * it's the use money manager
 */
public class UseMoneyManager extends Manager {

    public UseMoneyManager(String username) {
        super(username);
    }

    public boolean income(String moneyName, String budgetName, String value) {
        UserIO userIO = new UserIO(username);
        if (!userIO.getUserBudget().nodeExist(budgetName)) {
            return false;
        }
        BudgetCollection budgetCollection = new BudgetCollection();
        MoneyCollection moneyCollection = new MoneyCollection();
        try {
            Collection<BudgetNode> budgetNodes = userIO.ifIncome(budgetName, Double.valueOf(value));
            for (BudgetNode now : budgetNodes) {
                DBTable.DBData data = budgetCollection.getBudget(username, now.getName());
                data.object.put("now income", now.getNowIncome());
            }
            DBTable.DBData data = moneyCollection.getMoney(username, moneyName);
            data.object.put("value", (double) data.object.get("value") + Double.valueOf(value));
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    public boolean expenditure(String moneyName, String budgetName, String value) {
        UserIO userIO = new UserIO(username);
        if (!userIO.getUserBudget().nodeExist(budgetName)) {
            return false;
        }
        BudgetCollection budgetCollection = new BudgetCollection();
        MoneyCollection moneyCollection = new MoneyCollection();
        try {
            Collection<BudgetNode> budgetNodes = userIO.ifExpenditure(budgetName, Double.valueOf(value));
            for (BudgetNode now : budgetNodes) {
                DBTable.DBData data = budgetCollection.getBudget(username, now.getName());
                data.object.put("now expenditure", now.getNowExpenditure());
            }
            DBTable.DBData data = moneyCollection.getMoney(username, moneyName);
            data.object.put("value", (double) data.object.get("value") - Double.valueOf(value));
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

}
