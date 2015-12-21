package control.logic.manager;

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
        if (!checkBudgetExist(budgetName, userIO)) return false;
        MoneyCollection moneyCollection = new MoneyCollection();
        try {
            Collection<BudgetEntity> budgetNodes = userIO.ifIncome(budgetName, Double.valueOf(value));
            updateBudget(budgetNodes);
            updateMoney(moneyName, budgetName, moneyCollection, Double.valueOf(value));
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    public boolean expenditure(String moneyName, String budgetName, String value) {
        UserIO userIO = new UserIO(username);
        if (!checkBudgetExist(budgetName, userIO)) return false;
        MoneyCollection moneyCollection = new MoneyCollection();
        try {
            Collection<BudgetEntity> budgetNodes = userIO.ifExpenditure(budgetName, Double.valueOf(value));
            updateBudget(budgetNodes);
            updateMoney(moneyName, budgetName, moneyCollection, -Double.valueOf(value));
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    private void updateMoney(String moneyName, String budgetName, MoneyCollection moneyCollection, double aDouble) {
        MoneyEntity data = moneyCollection.getMoneyData(username, moneyName);
        userRollBackBuilder.putMoneyValue(moneyName, data.getValue() + "");
        putMoneyValue(moneyName, (data.getValue() + aDouble) + "");
        document.append("moneyType", moneyName)
                .append("budgetType", budgetName)
                .append("value", (data.getValue() + aDouble) + "");
    }

    public void putMoneyValue(String moneyName, String value) {
        MoneyEntity money = new MoneyCollection().getMoney(username, moneyName);
        userRollBackBuilder.putMoneyValue(moneyName, money.getValue() + "");
        money.setValue(Double.valueOf(value));
    }

    public void putBudgetValue(String budgetName, String nowIncome, String nowExpenditure) {
        BudgetEntity data = new BudgetCollection().getBudget(username, budgetName);
        userRollBackBuilder.putBudgetValue(budgetName, data.getNowIncome() + "", data.getNowExpenditure() + "");
        data.setNowIncome(Double.valueOf(nowIncome));
        data.setNowExpenditure(Double.valueOf(nowExpenditure));
    }

    private boolean checkBudgetExist(String budgetName, UserIO userIO) {
        return userIO.getUserBudget().nodeExist(budgetName);
    }

    private void updateBudget(Collection<BudgetEntity> budgetNodes) {
        for (BudgetEntity now : budgetNodes) {
            BudgetEntity data = new BudgetCollection().getBudgetData(username, now.getName());
            userRollBackBuilder.putBudgetValue(now.getName(), data.getNowIncome() + "", data.getNowExpenditure() + "");
            putBudgetValue(now.getName(), now.getNowIncome() + "", now.getNowExpenditure() + "");
        }
    }

}
