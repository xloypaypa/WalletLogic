package control.logic.manager;

import control.logic.userDataBuilder.UserRollBackBuilder;
import control.logic.userDataFormat.BudgetNode;
import control.logic.userDataFormat.UserIO;
import model.db.BudgetCollection;
import model.db.MoneyCollection;
import model.entity.BudgetEntity;
import model.entity.MoneyEntity;

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
                BudgetEntity data = new BudgetCollection().getBudgetData(username, now.getName());
                userRollBackBuilder.putBudgetValue(now.getName(), data.getNowIncome() + "", data.getNowExpenditure() + "");
                putBudgetValue(now.getName(), now.getNowIncome() + "", now.getNowExpenditure() + "");
            }
            MoneyEntity data = moneyCollection.getMoneyData(username, moneyName);
            userRollBackBuilder.putMoneyValue(moneyName, data.getValue() + "");
            putMoneyValue(moneyName, (data.getValue() + Double.valueOf(value)) + "");

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
                BudgetEntity data = new BudgetCollection().getBudgetData(username, now.getName());
                userRollBackBuilder.putBudgetValue(now.getName(), data.getNowIncome() + "", data.getNowExpenditure() + "");
                putBudgetValue(now.getName(), now.getNowIncome() + "", now.getNowExpenditure() + "");
            }
            MoneyEntity data = moneyCollection.getMoneyData(username, moneyName);
            userRollBackBuilder.putMoneyValue(moneyName, data.getValue() + "");
            putMoneyValue(moneyName, (data.getValue() - Double.valueOf(value)) + "");
            document.append("moneyName", moneyName)
                    .append("budgetName", budgetName)
                    .append("value", value)
                    .append("roll back call", userRollBackBuilder.getRollBackArray());
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    public void putMoneyValue(String moneyName, String value) {
        UserRollBackBuilder userRollBackBuilder = new UserRollBackBuilder();
        MoneyEntity money = new MoneyCollection().getMoney(username, moneyName);
        userRollBackBuilder.putMoneyValue(moneyName, money.getValue() + "");
        money.setValue(Double.valueOf(value));
    }

    public void putBudgetValue(String budgetName, String nowIncome, String nowExpenditure) {
        UserRollBackBuilder userRollBackBuilder = new UserRollBackBuilder();
        BudgetEntity data = new BudgetCollection().getBudget(username, budgetName);
        userRollBackBuilder.putBudgetValue(budgetName, data.getNowIncome() + "", data.getNowExpenditure() + "");
        data.setNowIncome(Double.valueOf(nowIncome));
        data.setNowExpenditure(Double.valueOf(nowExpenditure));
    }

}
