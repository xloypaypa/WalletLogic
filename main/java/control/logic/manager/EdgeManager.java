package control.logic.manager;

import control.logic.userDataFormat.UserEdge;
import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.db.DBTable;

/**
 * Created by xlo on 2015/12/18.
 * it's the edge manager
 */
public class EdgeManager extends Manager {

    public EdgeManager(String username) {
        super(username);
    }

    public boolean addEdge(String fromType, String toType, String script) {
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
    }

    public boolean removeEdge(String fromType, String toType) {
        BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
        if (budgetEdgeCollection.getEdgeData(username, fromType, toType) == null) {
            return false;
        }
        budgetEdgeCollection.remove(username, fromType, toType);
        return true;
    }

    public boolean updateEdge(String fromType, String toType, String script) {
        BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
        DBTable.DBData edge = budgetEdgeCollection.getEdge(username, fromType, toType);
        if (edge == null) {
            return false;
        }
        edge.object.put("script", script);
        return true;
    }
}
