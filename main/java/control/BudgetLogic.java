package control;

import model.config.WalletDBConfig;
import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.db.DBTable;
import model.session.SessionManager;
import org.bson.Document;

import java.net.Socket;
import java.util.List;

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
            if (username == null) {
                return false;
            }
            BudgetCollection budgetCollection = new BudgetCollection();
            if (budgetCollection.getBudgetData(username, typename) != null) {
                return false;
            }
            budgetCollection.addBudgetType(username, typename, Double.valueOf(income), Double.valueOf(expenditure));
            return true;
        });
        this.setDefaultMessage(event, "/createBudget");
        event.submit();
    }

    public void removeBudget(String typename) {
        event.setEventAction(() -> {
            event.lock(WalletDBConfig.getConfig().getDBLockName(BudgetCollection.class),
                    WalletDBConfig.getConfig().getDBLockName(BudgetEdgeCollection.class));
            String username = SessionManager.getSessionManager().getUsername(socket);
            if (username == null) {
                return false;
            }
            BudgetCollection budgetCollection = new BudgetCollection();
            if (budgetCollection.getBudgetData(username, typename) == null) {
                return false;
            }
            budgetCollection.removeBudget(username, typename);

            BudgetEdgeCollection edgeCollection = new BudgetEdgeCollection();
            List<DBTable.DBData> relativeEdge;
            relativeEdge = edgeCollection.getAllDataListData(new Document("username", username).append("from", typename));
            for (DBTable.DBData now : relativeEdge) {
                edgeCollection.remove(username, (String) now.object.get("from"), (String) now.object.get("to"));
            }

            relativeEdge = edgeCollection.getAllDataListData(new Document("username", username).append("to", typename));
            for (DBTable.DBData now : relativeEdge) {
                edgeCollection.remove(username, (String) now.object.get("from"), (String) now.object.get("to"));
            }

            return true;
        });
        this.setDefaultMessage(event, "/removeBudget");
        event.submit();
    }

    public void changeBudget(String typename, String newName, String income, String expenditure) {
        event.setEventAction(() -> {
            event.lock(WalletDBConfig.getConfig().getDBLockName(BudgetCollection.class),
                    WalletDBConfig.getConfig().getDBLockName(BudgetEdgeCollection.class));
            String username = SessionManager.getSessionManager().getUsername(socket);
            if (username == null) {
                return false;
            }
            BudgetCollection budgetCollection = new BudgetCollection();
            DBTable.DBData budget = budgetCollection.getBudget(username, typename);
            if (budget == null) {
                return false;
            }
            budget.object.put("typename", newName);
            budget.object.put("income", Double.valueOf(income));
            budget.object.put("expenditure", Double.valueOf(expenditure));

            BudgetEdgeCollection edgeCollection = new BudgetEdgeCollection();
            List<DBTable.DBData> relativeEdge;
            relativeEdge = edgeCollection.getAllDataList(new Document("username", username).append("from", typename));
            for (DBTable.DBData now : relativeEdge) {
                now.object.put("from", newName);
            }

            relativeEdge = edgeCollection.getAllDataList(new Document("username", username).append("to", typename));
            for (DBTable.DBData now : relativeEdge) {
                now.object.put("to", newName);
            }

            return true;
        });
        this.setDefaultMessage(event, "/changeBudget");
        event.submit();
    }
}
