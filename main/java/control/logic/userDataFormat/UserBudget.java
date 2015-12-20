package control.logic.userDataFormat;

import model.db.BudgetCollection;
import model.entity.BudgetEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 15/12/10.
 * it's the user budget
 */
public class UserBudget {

    private Map<String, BudgetEntity> nodes;

    public UserBudget(String username) {
        this.nodes = new HashMap<>();
        List<BudgetEntity> userBudget = new BudgetCollection().getBudgetListData(username);
        for (BudgetEntity now : userBudget) {
            nodes.put(now.getName(), now);
        }
    }

    public BudgetEntity getNode(String typename) {
        return nodes.get(typename);
    }

    public boolean nodeExist(String typename) {
        return this.nodes.containsKey(typename);
    }

}
