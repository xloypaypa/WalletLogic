package control.logic.manager;

import control.logic.userDataBuilder.UserRollBackBuilder;
import control.logic.userDataFormat.UserEdge;
import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.entity.BudgetEntity;
import model.entity.EdgeEntity;

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
        BudgetEntity from = budgetCollection.getBudgetData(username, fromType);
        BudgetEntity to = budgetCollection.getBudgetData(username, toType);
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

        UserRollBackBuilder userRollBackBuilder = new UserRollBackBuilder();
        userRollBackBuilder.addEdge(fromType, toType);
        document.append("fromBudget", fromType)
                .append("toBudget", toType)
                .append("script", script)
                .append("roll back call", userRollBackBuilder.getRollBackArray());
        return true;
    }

    public boolean removeEdge(String fromType, String toType) {
        BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
        EdgeEntity data = budgetEdgeCollection.getEdgeData(username, fromType, toType);
        if (data == null) {
            return false;
        }
        budgetEdgeCollection.remove(username, fromType, toType);

        UserRollBackBuilder userRollBackBuilder = new UserRollBackBuilder();
        userRollBackBuilder.removeEdge(fromType, toType, data.getScript());
        document.append("fromBudget", fromType)
                .append("toBudget", toType)
                .append("roll back call", userRollBackBuilder.getRollBackArray());
        return true;
    }

    public boolean updateEdge(String fromType, String toType, String newFrom, String newTo, String script) {
        BudgetEdgeCollection budgetEdgeCollection = new BudgetEdgeCollection();
        EdgeEntity edge = budgetEdgeCollection.getEdge(username, fromType, toType);
        if (edge == null) {
            return false;
        }
        UserRollBackBuilder userRollBackBuilder = new UserRollBackBuilder();
        userRollBackBuilder.updateEdge(edge.getFrom(), edge.getTo(), newFrom, newTo, edge.getScript());
        edge.setFrom(newFrom);
        edge.setTo(newTo);
        edge.setScript(script);

        document.append("fromBudget", fromType)
                .append("toBudget", toType)
                .append("script", script)
                .append("roll back call", userRollBackBuilder.getRollBackArray());
        return true;
    }
}
