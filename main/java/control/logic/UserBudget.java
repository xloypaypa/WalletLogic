package control.logic;

import model.db.BudgetCollection;
import model.db.DBTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 15/12/10.
 * it's the user budget
 */
public class UserBudget {

    private Map<String, BudgetNode> nodes;

    public UserBudget(String username) {
        this.nodes = new HashMap<>();
        List<DBTable.DBData> userBudget = new BudgetCollection().getBudgetListData(username);
        for (DBTable.DBData now : userBudget) {
            String name = now.object.get("typename").toString();
            double income = (double) now.object.get("income");
            double expenditure = (double) now.object.get("expenditure");
            double nowIncome = (double) now.object.get("now income");
            double nowExpenditure = (double) now.object.get("now expenditure");
            BudgetNode node = new BudgetNode(name, income, expenditure, nowIncome, nowExpenditure);
            nodes.put(node.getName(), node);
        }
    }

    public BudgetNode getNode(String typename) {
        return new BudgetNode(nodes.get(typename));
    }

    public boolean nodeExist(String typename) {
        return this.nodes.containsKey(typename);
    }

}
