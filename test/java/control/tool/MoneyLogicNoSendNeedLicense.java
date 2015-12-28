package control.tool;

import java.net.Socket;

/**
 * Created by xlo on 2015/12/28.
 * it's the money logic need license
 */
public class MoneyLogicNoSendNeedLicense extends MoneyLogicNoSend {
    public MoneyLogicNoSendNeedLicense(Socket socket) {
        super(socket);
    }
}
