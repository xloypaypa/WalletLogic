package control;

import model.db.DBTable;
import model.db.MoneyCollection;
import model.session.SessionManager;

import java.nio.channels.SocketChannel;

/**
 * Created by xlo on 2015/11/4.
 * it's the logic of money
 */
public class MoneyLogic extends LogicManager {
    public MoneyLogic(SocketChannel socketChannel) {
        super(socketChannel);
    }

    public void getMoney() {
        SendEvent event = new SendEvent(socketChannel) {
            @Override
            public boolean run() throws Exception {
                String username = SessionManager.getSessionManager().getUsername(socketChannel.socket());
                if (username == null) {
                    return false;
                }
                MoneyCollection moneyCollection = new MoneyCollection();
                MoneyLogic.this.setSuccessMessage(this, "/getMoney", moneyCollection.getMoneyListData(username));
                return true;
            }
        };
        this.setFailedMessage(event, "/getMoney");
        event.submit();
    }

    public void createMoney(String typename, String value) {
        SendEvent event = new SendEvent(socketChannel) {
            @Override
            public boolean run() throws Exception {
                String username = SessionManager.getSessionManager().getUsername(socketChannel.socket());
                if (username == null) {
                    return false;
                }
                MoneyCollection moneyCollection = new MoneyCollection();
                if (moneyCollection.getMoneyData(username, typename) != null) {
                    return false;
                }
                moneyCollection.createMoney(username, typename, Double.valueOf(value));
                return true;
            }
        };
        this.setDefaultMessage(event, "/createMoney");
        event.submit();
    }

    public void removeMoney(String typename) {
        SendEvent event = new SendEvent(socketChannel) {
            @Override
            public boolean run() throws Exception {
                String username = SessionManager.getSessionManager().getUsername(socketChannel.socket());
                if (username == null) {
                    return false;
                }
                MoneyCollection moneyCollection = new MoneyCollection();
                if (moneyCollection.getMoneyData(username, typename) == null) {
                    return false;
                }
                moneyCollection.removeMoney(username, typename);
                return true;
            }
        };
        this.setDefaultMessage(event, "/removeMoney");
        event.submit();
    }

    public void transferMoney(String from, String to, String value) {
        SendEvent event = new SendEvent(socketChannel) {
            @Override
            public boolean run() throws Exception {
                String username = SessionManager.getSessionManager().getUsername(socketChannel.socket());
                if (username == null) {
                    return false;
                }
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
        };
        this.setDefaultMessage(event, "/transferMoney");
        event.submit();
    }
}
