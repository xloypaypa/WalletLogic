package control;

import model.db.BudgetCollection;
import model.db.DBTable;
import model.session.SessionManager;

import java.nio.channels.SocketChannel;

/**
 * Created by xlo on 2015/11/5.
 * it's the budget logic
 */
public class BudgetLogic extends LogicManager {

    public BudgetLogic(SocketChannel socketChannel) {
        super(socketChannel);
    }

    public void getBudget() {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socketChannel.socket());
            if (username == null) {
                return false;
            }
            BudgetCollection budgetCollection = new BudgetCollection();
            BudgetLogic.this.setSuccessMessage(event, "/getBudget", budgetCollection.getBudgetListData(username));
            return true;
        });
        this.setFailedMessage(event, "/getBudget");
        event.submit();
    }

    public void createBudget(String typename, String income, String expenditure) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socketChannel.socket());
            if (username == null) {
                return false;
            }
            BudgetCollection budgetCollection = new BudgetCollection();
            if (budgetCollection.getBudgetData(username, typename) != null) {
                return false;
            }
            budgetCollection.addBudgetType(username, typename, Double.valueOf(income), Double.valueOf(expenditure));
            return true;
        });
        this.setDefaultMessage(event, "/createBudget");
        event.submit();
    }

    public void removeBudget(String typename) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socketChannel.socket());
            if (username == null) {
                return false;
            }
            BudgetCollection budgetCollection = new BudgetCollection();
            if (budgetCollection.getBudgetData(username, typename) == null) {
                return false;
            }
            budgetCollection.removeBudget(username, typename);
            return true;
        });
        this.setDefaultMessage(event, "/removeBudget");
        event.submit();
    }

    public void changeBudget(String typename, String newName, String income, String expenditure) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socketChannel.socket());
            if (username == null) {
                return false;
            }
            BudgetCollection budgetCollection = new BudgetCollection();
            DBTable.DBData budget = budgetCollection.getBudget(username, typename);
            if (budget == null) {
                return false;
            }
            budget.object.put("typename", newName);
            budget.object.put("income", Double.valueOf(income));
            budget.object.put("expenditure", Double.valueOf(expenditure));
            return true;
        });
        this.setDefaultMessage(event, "/changeBudget");
        event.submit();
    }
}
