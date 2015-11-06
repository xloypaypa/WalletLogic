package control;

import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.db.DBTable;
import model.session.SessionManager;

import java.nio.channels.SocketChannel;

/**
 * Created by xlo on 2015/11/6.
 * it's the edge logic
 */
public class EdgeLogic extends LogicManager {
    public EdgeLogic(SocketChannel socketChannel) {
        super(socketChannel);
    }

    public void addEdge(String fromType, String toType, String script) {
        SendEvent event = new SendEvent(socketChannel) {
            @Override
            public boolean run() throws Exception {
                String username = SessionManager.getSessionManager().getUsername(socketChannel.socket());
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
                if (budgetEdgeCollection.getEdgeData(from.object.get("_id").toString(), to.object.get("_id").toString()) != null) {
                    return false;
                }
                budgetEdgeCollection.addEege(from.object.get("_id").toString(), to.object.get("_id").toString(), script);
                return true;
            }
        };
        this.setDefaultMessage(event, "/addEdge");
        event.submit();
    }

    public void removeEdge(String fromType, String toType, String script) {
        SendEvent event = new SendEvent(socketChannel) {
            @Override
            public boolean run() throws Exception {
                String username = SessionManager.getSessionManager().getUsername(socketChannel.socket());
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
                if (budgetEdgeCollection.getEdgeData(from.object.get("_id").toString(), to.object.get("_id").toString()) == null) {
                    return false;
                }
                budgetEdgeCollection.remove(from.object.get("_id").toString(), to.object.get("_id").toString());
                return true;
            }
        };
        this.setDefaultMessage(event, "/removeEdge");
        event.submit();
    }

    public void updateEdge(String fromType, String toType, String script) {
        SendEvent event = new SendEvent(socketChannel) {
            @Override
            public boolean run() throws Exception {
                String username = SessionManager.getSessionManager().getUsername(socketChannel.socket());
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
                DBTable.DBData edge = budgetEdgeCollection.getEdge(from.object.get("_id").toString(), to.object.get("_id").toString());
                if (edge == null) {
                    return false;
                }
                edge.object.put("script", script);
                return true;
            }
        };
        this.setDefaultMessage(event, "/updateEdge");
        event.submit();
    }
}
