package control;

import control.logic.manager.MoneyManager;
import model.db.MoneyCollection;
import model.session.SessionManager;

import java.net.Socket;

/**
 * Created by xlo on 2015/11/4.
 * it's the logic of money
 */
public class MoneyLogic extends LogicManager {

    public MoneyLogic(Socket socket) {
        super(socket);
    }

    public void getMoney() {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            if (username == null) {
                return false;
            }
            MoneyCollection moneyCollection = new MoneyCollection();
            MoneyLogic.this.setSuccessMessage(event, "/getMoney", moneyCollection.getMoneyListData(username));
            return true;
        });
        this.setFailedMessage(event, "/getMoney");
        event.submit();
    }

    public void createMoney(String typename, String value) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            return username != null && new MoneyManager(username).createMoney(typename, value);
        });
        this.setDefaultMessage(event, "/createMoney");
        event.submit();
    }

    public void removeMoney(String typename) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            return username != null && new MoneyManager(username).removeMoney(typename);
        });
        this.setDefaultMessage(event, "/removeMoney");
        event.submit();
    }

    public void transferMoney(String from, String to, String value) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            return username != null && new MoneyManager(username).transferMoney(from, to, value);
        });
        this.setDefaultMessage(event, "/transferMoney");
        event.submit();
    }

    public void renameMoney(String typename, String newName) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            return username != null && new MoneyManager(username).renameMoney(typename, newName);
        });
        this.setDefaultMessage(event, "/renameMoney");
        event.submit();
    }
}
