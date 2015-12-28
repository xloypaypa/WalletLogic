package control;

import model.config.UserAccessConfig;
import model.session.SessionManager;

import java.net.Socket;

/**
 * Created by xlo on 2015/12/28.
 * it's the logic need license
 */
public abstract class NeedLicenseLogic extends LogicManager {

    protected String username;

    protected NeedLicenseLogic(Socket socket) {
        super(socket);
        this.username = SessionManager.getSessionManager().getUsername(socket);
        if (!UserAccessConfig.getConfig().haveLicense(this.username, this.getClass().getName())) {
            throw new NoLicenseException();
        }
    }

    protected SendEvent buildSendEvent(Socket socket) {
        return new SendEvent(socket);
    }
}
