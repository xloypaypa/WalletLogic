package control;

import control.logic.manager.UseMoneyManager;
import model.config.WalletDBConfig;
import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.db.MoneyCollection;
import model.session.SessionManager;

import java.net.Socket;

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
            return username != null && new UseMoneyManager(username).income(moneyName, budgetName, value);
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
            return username != null && new UseMoneyManager(username).expenditure(moneyName, budgetName, value);
        });
        this.setDefaultMessage(event, "/expenditure");
        event.submit();
    }
}
