package control;

import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.db.DBTable;
import model.session.SessionManager;
import org.bson.Document;

import java.net.Socket;
import java.util.Map;

/**
 * Created by xlo on 2015/11/6.
 * it's the edge logic
 */
public class EdgeLogic extends LogicManager {

    public EdgeLogic(Socket socket) {
        super(socket);
    }

    public void addEdge(String fromType, String toType, String script) {
        event.setEventAction(() -> {
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
            budgetEdgeCollection.addEdge(username, fromType, toType, script);
            return true;
        });
        this.setDefaultMessage(event, "/addEdge");
        event.submit();
    }

    public void getEdge(String fromType, String toType) {
        event.setEventAction(() -> {
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
            if (budgetEdgeCollection.getEdgeData(username, fromType, toType) == null) {
                return false;
            }
            Map<String, Object> result = new BudgetEdgeCollection()
                    .findEdgeList(new Document("username", username).append("from", fromType).append("to", toType)).get(0).object;
            result.remove("_id");
            EdgeLogic.this.setSuccessMessage(event, "/getEdge", result);
            return true;
        });
        this.setFailedMessage(event, "/getEdge");
        event.submit();
    }

    public void removeEdge(String fromType, String toType, String script) {
        event.setEventAction(() -> {
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

            BudgetCollection budgetCollection = new BudgetCollection();
            DBTable.DBData from = budgetCollection.getBudgetData(username, fromType);
            DBTable.DBData to = budgetCollection.getBudgetData(username, toType);
            if (from == null || to == null) {
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

    public void getSuperEdgeList(String typename) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            if (username == null) {
                return false;
            }

            EdgeLogic.this.setSuccessMessage(event, "/getSuperEdgeList", new BudgetEdgeCollection()
                    .findEdgeList(new Document("username", username).append("from", typename)));
            return true;
        });
        this.setFailedMessage(event, "/getSuperEdgeList");
        event.submit();
    }

    public void getExtendEdgeList(String typename) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            if (username == null) {
                return false;
            }

            EdgeLogic.this.setSuccessMessage(event, "/getExtendEdgeList", new BudgetEdgeCollection()
                    .findEdgeList(new Document("username", username).append("to", typename)));
            return true;
        });
        this.setFailedMessage(event, "/getExtendEdgeList");
        event.submit();
    }
}
