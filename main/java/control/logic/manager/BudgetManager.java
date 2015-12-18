package control.logic.manager;

import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.db.DBTable;
import org.bson.Document;

import java.util.List;

/**
 * Created by xlo on 2015/12/18.
 * it's the budget manager
 */
public class BudgetManager extends Manager {

    public BudgetManager(String username) {
        super(username);
    }

    public boolean createBudget(String typename, String income, String expenditure) {
        BudgetCollection budgetCollection = new BudgetCollection();
        if (budgetCollection.getBudgetData(username, typename) != null) {
            return false;
        }
        budgetCollection.addBudgetType(username, typename, Double.valueOf(income), Double.valueOf(expenditure));
        return true;
    }

    public boolean removeBudget(String typename) {
        BudgetCollection budgetCollection = new BudgetCollection();
        if (budgetCollection.getBudgetData(username, typename) == null) {
            return false;
        }
        budgetCollection.removeBudget(username, typename);

        BudgetEdgeCollection edgeCollection = new BudgetEdgeCollection();
        List<DBTable.DBData> relativeEdge;
        relativeEdge = edgeCollection.getAllDataListData(new Document("username", username).append("from", typename));
        for (DBTable.DBData now : relativeEdge) {
            new EdgeManager(username).removeEdge((String) now.object.get("from"), (String) now.object.get("to"));
        }
        relativeEdge = edgeCollection.getAllDataListData(new Document("username", username).append("to", typename));
        for (DBTable.DBData now : relativeEdge) {
            new EdgeManager(username).removeEdge((String) now.object.get("from"), (String) now.object.get("to"));
        }

        return true;
    }

    public boolean changeBudget(String typename, String newName, String income, String expenditure) {
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
    }

}
