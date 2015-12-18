package control.logic.manager;

import control.logic.userDataBuilder.UserRollBackBuilder;
import model.db.DBTable;
import model.db.DetailCollection;
import model.db.MoneyCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

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

        UserRollBackBuilder userRollBackBuilder = new UserRollBackBuilder();
        userRollBackBuilder.createMoney(typename);
        document.append("moneyType", typename)
                .append("value", value)
                .append("roll back call", userRollBackBuilder.getRollBackArray());

        return true;
    }

    public boolean renameMoney(String typename, String newName) {
        MoneyCollection moneyCollection = new MoneyCollection();
        DBTable.DBData money = moneyCollection.getMoney(username, typename);
        if (money == null) {
            return false;
        }
        money.object.put("typename", newName);

        UserRollBackBuilder userRollBackBuilder = new UserRollBackBuilder();
        userRollBackBuilder.renameMoney(typename, newName);
        document.append("renameMoney", typename)
                .append("value", money.object.get("value").toString())
                .append("roll back call", userRollBackBuilder.getRollBackArray());
        return true;
    }

    public boolean removeMoney(String typename) {
        MoneyCollection moneyCollection = new MoneyCollection();
        DBTable.DBData moneyData = moneyCollection.getMoneyData(username, typename);
        if (moneyData == null) {
            return false;
        }
        moneyCollection.removeMoney(username, typename);

        UserRollBackBuilder userRollBackBuilder = new UserRollBackBuilder();
        userRollBackBuilder.removeMoney(typename, moneyData.object.get("value").toString());
        document.append("moneyType", typename)
                .append("value", 0)
                .append("roll back call", userRollBackBuilder.getRollBackArray());
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

        UserRollBackBuilder userRollBackBuilder = new UserRollBackBuilder();
        userRollBackBuilder.transferMoney(from, to, value);
        document.append("fromMoneyType", from)
                .append("toMoneyType", to)
                .append("fromMoneyValue", fromType.object.get("value").toString())
                .append("toMoneyValue", aimType.object.get("value").toString())
                .append("roll back call", userRollBackBuilder.getRollBackArray());
        return true;
    }

}
