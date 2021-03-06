package control;

import control.logic.manager.MoneyManager;
import model.db.DetailCollection;
import model.db.MoneyCollection;
import model.entity.MoneyEntity;
import model.session.SessionManager;

import java.net.Socket;
import java.util.*;

/**
 * Created by xlo on 2015/11/4.
 * it's the logic of money
 */
public class MoneyLogic extends NeedLicenseLogic {

    public MoneyLogic(Socket socket) {
        super(socket);
    }

    public void getMoney() {
        event.setEventAction(() -> {
            MoneyCollection moneyCollection = new MoneyCollection();
            List<MoneyEntity> listData = moneyCollection.getMoneyListData(username);
            List<Map<String, String>> mapList = new LinkedList<>();
            for (MoneyEntity now : listData) {
                HashMap<String, String> map = new HashMap<>();
                map.put("typename", now.getName());
                map.put("value", now.getValue() + "");
                mapList.add(map);
            }
            MoneyLogic.this.setSuccessMessage(event, "/getMoney", mapList);
            return true;
        });
        this.setFailedMessage(event, "/getMoney");
        event.submit();
    }

    public void createMoney(String typename, String value) {
        event.setEventAction(() -> {
            MoneyManager moneyManager = new MoneyManager(username);
            if (moneyManager.createMoney(typename, value)) {
                new DetailCollection().addDetail(username, new Date(), "createMoney",
                        moneyManager.getUserRollBackMessage(), moneyManager.getDetailMessage());
                return true;
            } else {
                return false;
            }
        });
        this.setDefaultMessage(event, "/createMoney");
        event.submit();
    }

    public void removeMoney(String typename) {
        event.setEventAction(() -> {
            MoneyManager moneyManager = new MoneyManager(username);
            if (username != null && moneyManager.removeMoney(typename)) {
                new DetailCollection().addDetail(username, new Date(), "removeMoney",
                        moneyManager.getUserRollBackMessage(), moneyManager.getDetailMessage());
                return true;
            } else {
                return false;
            }
        });
        this.setDefaultMessage(event, "/removeMoney");
        event.submit();
    }

    public void transferMoney(String from, String to, String value) {
        event.setEventAction(() -> {
            MoneyManager moneyManager = new MoneyManager(username);
            if (moneyManager.transferMoney(from, to, value)) {
                new DetailCollection().addDetail(username, new Date(), "transferMoney",
                        moneyManager.getUserRollBackMessage(), moneyManager.getDetailMessage());
                return true;
            } else {
                return false;
            }
        });
        this.setDefaultMessage(event, "/transferMoney");
        event.submit();
    }

    public void renameMoney(String typename, String newName) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            MoneyManager moneyManager = new MoneyManager(username);
            if (username != null && moneyManager.renameMoney(typename, newName)) {
                new DetailCollection().addDetail(username, new Date(), "renameMoney",
                        moneyManager.getUserRollBackMessage(), moneyManager.getDetailMessage());
                return true;
            } else {
                return false;
            }
        });
        this.setDefaultMessage(event, "/renameMoney");
        event.submit();
    }
}
