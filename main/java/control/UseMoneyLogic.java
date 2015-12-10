package control;

import java.net.Socket;

/**
 * Created by xlo on 15/12/10.
 * it's the use money logic
 */
public class UseMoneyLogic extends LogicManager {
    public UseMoneyLogic(Socket socket) {
        super(socket);
    }

    public void income(String typename, String value) {
        event.setEventAction(() -> true);
        event.submit();
    }
}
