package control.logic.manager;

import model.db.DBTable;
import model.db.MoneyCollection;

/**
 * Created by xlo on 2015/12/18.
 * it's the money manager
 */
public class MoneyManager extends Manager {

    public MoneyManager(String username) {
        super(username);
    }

    public boolean createMoney(String typename, String value) {
        MoneyCollection moneyCollection = new MoneyCollection();
        if (moneyCollection.getMoneyData(username, typename) != null) {
            return false;
        }
        moneyCollection.createMoney(username, typename, Double.valueOf(value));
        return true;
    }

    public boolean renameMoney(String typename, String newName) {
        MoneyCollection moneyCollection = new MoneyCollection();
        DBTable.DBData money = moneyCollection.getMoney(username, typename);
        if (money == null) {
            return false;
        }
        money.object.put("typename", newName);
        return true;
    }

    public boolean removeMoney(String typename) {
        MoneyCollection moneyCollection = new MoneyCollection();
        if (moneyCollection.getMoneyData(username, typename) == null) {
            return false;
        }
        moneyCollection.removeMoney(username, typename);
        return true;
    }

    public boolean transferMoney(String from, String to, String value) {
        MoneyCollection moneyCollection = new MoneyCollection();
        DBTable.DBData fromType = moneyCollection.getMoney(username, from);
        DBTable.DBData aimType = moneyCollection.getMoney(username, to);
        if (fromType == null || aimType == null) {
            return false;
        }
        double fromValue = (double) fromType.object.get("value");
        double aimValue = (double) aimType.object.get("value");
        Double need = Double.valueOf(value);
        if (fromValue < need) {
            return false;
        }
        fromType.object.put("value", fromValue - need);
        aimType.object.put("value", aimValue + need);
        return true;
    }

}
