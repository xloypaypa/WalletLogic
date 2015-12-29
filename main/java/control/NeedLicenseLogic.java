package control;

import control.logic.event.NeedLoginEvent;
import control.logic.event.SendEvent;
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
    }

    protected SendEvent buildSendEvent(Socket socket) {
        return new NeedLoginEvent(socket, this.getClass());
    }
}
