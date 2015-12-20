package control.logic.manager;

import control.logic.userDataBuilder.UserRollBackBuilder;
import model.db.MoneyCollection;
import model.entity.MoneyEntity;

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
        MoneyEntity money = moneyCollection.getMoney(username, typename);
        if (money == null) {
            return false;
        }
        money.setName(newName);

        UserRollBackBuilder userRollBackBuilder = new UserRollBackBuilder();
        userRollBackBuilder.renameMoney(typename, newName);
        document.append("moneyType", typename)
                .append("value", money.getValue() + "")
                .append("roll back call", userRollBackBuilder.getRollBackArray());
        return true;
    }

    public boolean removeMoney(String typename) {
        MoneyCollection moneyCollection = new MoneyCollection();
        MoneyEntity moneyData = moneyCollection.getMoneyData(username, typename);
        if (moneyData == null) {
            return false;
        }
        moneyCollection.removeMoney(username, typename);

        UserRollBackBuilder userRollBackBuilder = new UserRollBackBuilder();
        userRollBackBuilder.removeMoney(typename, moneyData.getValue() + "");
        document.append("moneyType", typename)
                .append("value", 0)
                .append("roll back call", userRollBackBuilder.getRollBackArray());
        return true;
    }

    public boolean transferMoney(String from, String to, String value) {
        MoneyCollection moneyCollection = new MoneyCollection();
        MoneyEntity fromType = moneyCollection.getMoney(username, from);
        MoneyEntity aimType = moneyCollection.getMoney(username, to);
        if (fromType == null || aimType == null) {
            return false;
        }
        double fromValue = fromType.getValue();
        double aimValue = aimType.getValue();
        Double need = Double.valueOf(value);
        if (fromValue < need) {
            return false;
        }
        fromType.setValue(fromValue - need);
        aimType.setValue(aimValue + need);

        UserRollBackBuilder userRollBackBuilder = new UserRollBackBuilder();
        userRollBackBuilder.transferMoney(from, to, value);
        document.append("fromMoneyType", from)
                .append("toMoneyType", to)
                .append("fromMoneyValue", fromType.getValue() + "")
                .append("toMoneyValue", aimType.getValue() + "")
                .append("roll back call", userRollBackBuilder.getRollBackArray());
        return true;
    }

}
