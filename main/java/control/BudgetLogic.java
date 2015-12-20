package control;

import control.logic.manager.BudgetManager;
import model.config.WalletDBConfig;
import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.db.DetailCollection;
import model.session.SessionManager;

import java.net.Socket;
import java.util.Date;

/**
 * Created by xlo on 2015/11/5.
 * it's the budget logic
 */
public class BudgetLogic extends LogicManager {

    public BudgetLogic(Socket socket) {
        super(socket);
    }

    public void getBudget() {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            if (username == null) {
                return false;
            }
            BudgetCollection budgetCollection = new BudgetCollection();
            BudgetLogic.this.setSuccessMessage(event, "/getBudget", budgetCollection.getBudgetListData(username));
            return true;
        });
        this.setFailedMessage(event, "/getBudget");
        event.submit();
    }

    public void createBudget(String typename, String income, String expenditure) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            BudgetManager budgetManager = new BudgetManager(username);
            if (username != null && budgetManager.createBudget(typename, income, expenditure, "0", "0")) {
                new DetailCollection().addDetail(username, new Date(), "createBudget",
                        budgetManager.getUserRollBackMessage(), budgetManager.getDetailMessage());
                return true;
            } else {
                return false;
            }
        });
        this.setDefaultMessage(event, "/createBudget");
        event.submit();
    }

    public void removeBudget(String typename) {
        event.setEventAction(() -> {
            event.lock(WalletDBConfig.getConfig().getDBLockName(BudgetCollection.class),
                    WalletDBConfig.getConfig().getDBLockName(BudgetEdgeCollection.class));
            String username = SessionManager.getSessionManager().getUsername(socket);
            BudgetManager budgetManager = new BudgetManager(username);
            if (username != null && budgetManager.removeBudget(typename)) {
                new DetailCollection().addDetail(username, new Date(), "removeBudget",
                        budgetManager.getUserRollBackMessage(), budgetManager.getDetailMessage());
                return true;
            } else {
                return false;
            }
        });
        this.setDefaultMessage(event, "/removeBudget");
        event.submit();
    }

    public void changeBudget(String typename, String newName, String income, String expenditure) {
        event.setEventAction(() -> {
            event.lock(WalletDBConfig.getConfig().getDBLockName(BudgetCollection.class),
                    WalletDBConfig.getConfig().getDBLockName(BudgetEdgeCollection.class));
            String username = SessionManager.getSessionManager().getUsername(socket);
            BudgetManager budgetManager = new BudgetManager(username);
            if (username != null && budgetManager.changeBudget(typename, newName, income, expenditure)) {
                new DetailCollection().addDetail(username, new Date(), "changeBudget",
                        budgetManager.getUserRollBackMessage(), budgetManager.getDetailMessage());
                return true;
            } else {
                return false;
            }
        });
        this.setDefaultMessage(event, "/changeBudget");
        event.submit();
    }

}
