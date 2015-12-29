package control.tool;

import control.NeedLicenseLogic;
import model.config.UserAccessConfig;
import model.session.SessionManager;

import java.net.Socket;

/**
 * Created by xlo on 2015/12/29.
 * it's the event not send message and need login
 */
public class NoSendNeedLoginEvent extends NoSendEvent {

    protected Class<? extends NeedLicenseLogic> aClass;

    public NoSendNeedLoginEvent(Socket socket, EventCounter eventCounter, Class<? extends NeedLicenseLogic> aClass) {
        super(socket, eventCounter);
        this.aClass = aClass;
    }

    @Override
    public boolean run() throws Exception {
        String username = SessionManager.getSessionManager().getUsername(socket);
        return !(username == null || !UserAccessConfig.getConfig().haveLicense(username, this.aClass.getName())) && super.run();
    }
}
