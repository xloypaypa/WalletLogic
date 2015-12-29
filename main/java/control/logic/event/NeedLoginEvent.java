package control.logic.event;

import control.NeedLicenseLogic;
import model.config.UserAccessConfig;
import model.session.SessionManager;

import java.net.Socket;

/**
 * Created by xlo on 2015/12/29.
 * it's the event need login
 */
public class NeedLoginEvent extends SendEvent {

    protected Class<? extends NeedLicenseLogic> aClass;

    public NeedLoginEvent(Socket socket, Class<? extends NeedLicenseLogic> aClass) {
        super(socket);
        this.aClass = aClass;
    }

    @Override
    public boolean run() throws Exception {
        String username = SessionManager.getSessionManager().getUsername(socket);
        return !(username == null || !UserAccessConfig.getConfig().haveLicense(username, this.aClass.getName())) && super.run();
    }
}
