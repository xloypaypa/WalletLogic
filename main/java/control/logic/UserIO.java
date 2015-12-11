package control.logic;

import javafx.util.Pair;
import model.config.script.ForceCacheScriptManager;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by xlo on 15/12/10.
 * it's the use io
 */
public class UserIO {

    private UserEdge userEdge;
    private UserBudget userBudget;

    public UserIO(String username) {
        userBudget = new UserBudget(username);
        userEdge = new UserEdge(username);
    }

    public Collection<BudgetNode> ifIncome(String typename, double value) throws ReflectiveOperationException {
        Map<String, BudgetNode> map = new HashMap<>();
        calculate(typename, value, map, true);
        return map.values();
    }

    public Collection<BudgetNode> ifExpenditure(String typename, double value) throws ReflectiveOperationException {
        Map<String, BudgetNode> map = new HashMap<>();
        calculate(typename, value, map, false);
        return map.values();
    }

    public UserBudget getUserBudget() {
        return userBudget;
    }

    public UserEdge getUserEdge() {
        return userEdge;
    }

    /**
     * calculate io
     *
     * @param typename start type name
     * @param value start value
     * @param result result map
     * @param flag {@code true} if income, {@code false} if expenditure
     * @throws ReflectiveOperationException
     */
    private void calculate(String typename, double value, Map<String, BudgetNode> result, boolean flag) throws ReflectiveOperationException {
        incomeCalculateNowNode(typename, value, result, flag);
        incomeCalculateNextNode(typename, value, result, flag);
    }

    private void incomeCalculateNextNode(String typename, double value, Map<String, BudgetNode> result, boolean flag) throws ReflectiveOperationException {
        List<Pair<String, String>> next = userEdge.getFather(typename);
        for (Pair<String, String> now : next) {
            Object object = ForceCacheScriptManager.getForceCacheScriptManager().runCommand(now.getValue());
            Method method = object.getClass().getMethod("invoke", Object.class);
            double nextValue = (double) method.invoke(object, value);
            calculate(now.getKey(), nextValue, result, flag);
        }
    }

    private void incomeCalculateNowNode(String typename, double value, Map<String, BudgetNode> result, boolean flag) {
        BudgetNode nowNode = result.get(typename);
        if (nowNode == null) {
            nowNode = userBudget.getNode(typename);
            result.put(nowNode.getName(), nowNode);
        }
        if (flag) {
            nowNode.setNowIncome(nowNode.getNowIncome() + value);
        } else {
            nowNode.setNowExpenditure(nowNode.getNowExpenditure() + value);
        }
    }

}
