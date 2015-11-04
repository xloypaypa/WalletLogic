package control;

import model.config.UserAccessConfig;
import model.db.MoneyCollection;

import java.nio.channels.SocketChannel;

/**
 * Created by xlo on 2015/11/4.
 * it's the logic of money
 */
public class MoneyLogic extends LogicManager {
    public MoneyLogic(SocketChannel socketChannel) {
        super(socketChannel);
    }

    public void createMoney(String username, String password, String typename, String value) {
        SendEvent event = new SendEvent(socketChannel) {
            @Override
            public boolean run() throws Exception {
                if (!UserAccessConfig.getConfig().checkUser(username, password, socketChannel)) {
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

    public void removeMoney(String username, String password, String typename) {
        SendEvent event = new SendEvent(socketChannel) {
            @Override
            public boolean run() throws Exception {
                if (!UserAccessConfig.getConfig().checkUser(username, password, socketChannel)) {
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
        this.setDefaultMessage(event, "/createMoney");
        event.submit();
    }
}
