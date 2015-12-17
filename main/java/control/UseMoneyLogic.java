package control;

import control.logic.BudgetNode;
import control.logic.UserIO;
import model.config.WalletDBConfig;
import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.db.DBTable;
import model.db.MoneyCollection;
import model.session.SessionManager;

import java.net.Socket;
import java.util.Collection;

/**
 * Created by xlo on 15/12/10.
 * it's the use money logic
 */
public class UseMoneyLogic extends LogicManager {
    public UseMoneyLogic(Socket socket) {
        super(socket);
    }

    public void income(String moneyName, String budgetName, String value) {
        event.setEventAction(() -> {
            event.lock(WalletDBConfig.getConfig().getDBLockName(BudgetCollection.class),
                    WalletDBConfig.getConfig().getDBLockName(MoneyCollection.class),
                    WalletDBConfig.getConfig().getDBLockName(BudgetEdgeCollection.class));
            String username = SessionManager.getSessionManager().getUsername(socket);
            if (username == null) {
                return false;
            }
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
        });
        this.setDefaultMessage(event, "/income");
        event.submit();
    }

    public void expenditure(String moneyName, String budgetName, String value) {
        event.setEventAction(() -> {
            event.lock(WalletDBConfig.getConfig().getDBLockName(BudgetCollection.class),
                    WalletDBConfig.getConfig().getDBLockName(MoneyCollection.class),
                    WalletDBConfig.getConfig().getDBLockName(BudgetEdgeCollection.class));
            String username = SessionManager.getSessionManager().getUsername(socket);
            if (username == null) {
                return false;
            }
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
        });
        this.setDefaultMessage(event, "/expenditure");
        event.submit();
    }
}
