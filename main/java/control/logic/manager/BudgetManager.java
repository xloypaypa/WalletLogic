package control.logic.manager;

import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.db.DBTable;
import model.entity.BudgetEntity;
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

    public boolean createBudget(String typename, String income, String expenditure, String nowIncome, String nowExpenditure) {
        BudgetCollection budgetCollection = new BudgetCollection();
        if (budgetCollection.getBudgetData(username, typename) != null) {
            return false;
        }
        budgetCollection.addBudgetType(username, typename, Double.valueOf(income), Double.valueOf(expenditure), Double.valueOf(nowIncome), Double.valueOf(nowExpenditure));

        userRollBackBuilder.createBudget(typename);
        document.append("BudgetType", typename)
                .append("income", income)
                .append("expenditure", expenditure);
        return true;
    }

    public boolean removeBudget(String typename) {
        BudgetCollection budgetCollection = new BudgetCollection();
        BudgetEntity budgetData = budgetCollection.getBudgetData(username, typename);
        if (budgetData == null) {
            return false;
        }
        budgetCollection.removeBudget(username, typename);
        userRollBackBuilder.removeBudget(typename, budgetData.getIncome() + "", budgetData.getExpenditure() + "",
                budgetData.getNowIncome() + "", budgetData.getNowExpenditure() + "");

        BudgetEdgeCollection edgeCollection = new BudgetEdgeCollection();
        List<DBTable.DBData> relativeEdge;
        relativeEdge = edgeCollection.getAllDataListData(new Document("username", username).append("from", typename));
        removeRelativeEdge(relativeEdge);
        relativeEdge = edgeCollection.getAllDataListData(new Document("username", username).append("to", typename));
        removeRelativeEdge(relativeEdge);

        document.append("BudgetType", typename);
        return true;
    }

    private void removeRelativeEdge(List<DBTable.DBData> relativeEdge) {
        for (DBTable.DBData now : relativeEdge) {
            new EdgeManager(username).removeEdge((String) now.object.get("from"), (String) now.object.get("to"));
            userRollBackBuilder.removeEdge((String) now.object.get("from"), (String) now.object.get("to"), now.object.get("script").toString());
        }
    }

    public boolean changeBudget(String typename, String newName, String income, String expenditure) {
        BudgetCollection budgetCollection = new BudgetCollection();
        BudgetEntity budget = budgetCollection.getBudget(username, typename);
        if (budget == null) {
            return false;
        }
        userRollBackBuilder.changeBudget(typename, budget.getIncome() + "", budget.getExpenditure() + "", newName);
        budget.setName(newName);
        budget.setIncome(Double.valueOf(income));
        budget.setExpenditure(Double.valueOf(expenditure));

        BudgetEdgeCollection edgeCollection = new BudgetEdgeCollection();
        List<DBTable.DBData> relativeEdge;
        relativeEdge = edgeCollection.getAllDataList(new Document("username", username).append("from", typename));
        for (DBTable.DBData now : relativeEdge) {
            userRollBackBuilder.updateEdge(now.object.get("from").toString(), now.object.get("to").toString(),
                    newName, now.object.get("to").toString(), now.object.get("script").toString());
            now.object.put("from", newName);
        }

        relativeEdge = edgeCollection.getAllDataList(new Document("username", username).append("to", typename));
        for (DBTable.DBData now : relativeEdge) {
            userRollBackBuilder.updateEdge(now.object.get("from").toString(), now.object.get("to").toString(),
                    now.object.get("from").toString(), newName, now.object.get("script").toString());
            now.object.put("to", newName);
        }

        document.append("BudgetType", typename)
                .append("newName", newName)
                .append("income", income)
                .append("expenditure", expenditure);
        return true;
    }

}
