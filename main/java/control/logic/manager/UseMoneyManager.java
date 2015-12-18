package control.logic.manager;

import control.logic.userDataBuilder.UserRollBackBuilder;
import control.logic.userDataFormat.BudgetNode;
import control.logic.userDataFormat.UserIO;
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
        MoneyCollection moneyCollection = new MoneyCollection();
        UserRollBackBuilder userRollBackBuilder = new UserRollBackBuilder();
        try {
            Collection<BudgetNode> budgetNodes = userIO.ifIncome(budgetName, Double.valueOf(value));
            for (BudgetNode now : budgetNodes) {
                putBudgetValue(now.getName(), now.getNowIncome()+"", now.getNowExpenditure()+"", userRollBackBuilder);
            }
            DBTable.DBData data = moneyCollection.getMoneyData(username, moneyName);
            putMoneyValue(moneyName, ((double) data.object.get("value") + Double.valueOf(value)) + "", userRollBackBuilder);

            document.append("moneyName", moneyName)
                    .append("budgetName", budgetName)
                    .append("value", value)
                    .append("roll back call", userRollBackBuilder.getRollBackArray());
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
        MoneyCollection moneyCollection = new MoneyCollection();
        UserRollBackBuilder userRollBackBuilder = new UserRollBackBuilder();
        try {
            Collection<BudgetNode> budgetNodes = userIO.ifExpenditure(budgetName, Double.valueOf(value));
            for (BudgetNode now : budgetNodes) {
                putBudgetValue(now.getName(), now.getNowIncome()+"", now.getNowExpenditure()+"", userRollBackBuilder);
            }
            DBTable.DBData data = moneyCollection.getMoneyData(username, moneyName);
            putMoneyValue(moneyName, ((double) data.object.get("value") - Double.valueOf(value)) + "", userRollBackBuilder);
            document.append("moneyName", moneyName)
                    .append("budgetName", budgetName)
                    .append("value", value)
                    .append("roll back call", userRollBackBuilder.getRollBackArray());
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    public void putMoneyValue(String moneyName, String value, UserRollBackBuilder userRollBackBuilder) {
        DBTable.DBData data = new MoneyCollection().getMoney(username, moneyName);
        userRollBackBuilder.putMoneyValue(moneyName, data.object.get("value").toString());
        data.object.put("value", Double.valueOf(value));
    }

    public void putBudgetValue(String budgetName, String nowIncome, String nowExpenditure, UserRollBackBuilder userRollBackBuilder) {
        DBTable.DBData data = new BudgetCollection().getBudget(username, budgetName);
        userRollBackBuilder.putBudgetValue(budgetName, data.object.get("now income").toString(), data.object.get("now expenditure").toString());
        data.object.put("now income", Double.valueOf(nowIncome));
        data.object.put("now expenditure", Double.valueOf(nowExpenditure));
    }

}
