package control;

import control.logic.UserEdge;
import model.config.WalletDBConfig;
import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.db.DBTable;
import model.session.SessionManager;
import org.bson.Document;

import java.net.Socket;

/**
 * Created by xlo on 2015/11/6.
 * it's the edge logic
 */
public class EdgeLogic extends LogicManager {

    public EdgeLogic(Socket socket) {
        super(socket);
    }

    public void getEdgeList() {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            if (username == null) {
                return false;
            }

            EdgeLogic.this.setSuccessMessage(event, "/getEdgeList", new BudgetEdgeCollection()
                    .findEdgeList(new Document("username", username)));
            return true;
        });
        this.setFailedMessage(event, "/getEdgeList");
        event.submit();
    }

    public void addEdge(String fromType, String toType, String script) {
        event.setEventAction(() -> {
            event.lock(WalletDBConfig.getConfig().getDBLockName(BudgetCollection.class),
                    WalletDBConfig.getConfig().getDBLockName(BudgetEdgeCollection.class));
            String username = SessionManager.getSessionManager().getUsername(socket);
            if (username == null) {
                return false;
            }

            BudgetCollection budgetCollection = new BudgetCollection();
            DBTable.DBData from = budgetCollection.getBudgetData(username, fromType);
            DBTable.DBData to = budgetCollection.getBudgetData(username, toType);
            if (from == null || to == null) {
                return false;
            }

            BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
            if (budgetEdgeCollection.getEdgeData(username, fromType, toType) != null) {
                return false;
            }
            UserEdge userEdge = new UserEdge(username);
            if (!userEdge.checkCouldAddEdge(fromType, toType)) {
                return false;
            }
            budgetEdgeCollection.addEdge(username, fromType, toType, script);
            return true;
        });
        this.setDefaultMessage(event, "/addEdge");
        event.submit();
    }

    public void removeEdge(String fromType, String toType) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            if (username == null) {
                return false;
            }

            BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
            if (budgetEdgeCollection.getEdgeData(username, fromType, toType) == null) {
                return false;
            }
            budgetEdgeCollection.remove(username, fromType, toType);
            return true;
        });
        this.setDefaultMessage(event, "/removeEdge");
        event.submit();
    }

    public void updateEdge(String fromType, String toType, String script) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            if (username == null) {
                return false;
            }

            BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
            DBTable.DBData edge = budgetEdgeCollection.getEdge(username, fromType, toType);
            if (edge == null) {
                return false;
            }
            edge.object.put("script", script);
            return true;
        });
        this.setDefaultMessage(event, "/updateEdge");
        event.submit();
    }

}
