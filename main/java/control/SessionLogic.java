package control;

import model.session.SessionManager;

import java.net.Socket;

/**
 * Created by xlo on 2015/11/4.
 * it's the session logic
 */
public class SessionLogic extends LogicManager {

    public SessionLogic(Socket socket) {
        super(socket);
    }

    public void getSessionID() {
        event.setEventAction(() -> true);
        String message = SessionManager.getSessionManager().getSessionMessage(socket).getSessionID() + "";
        SessionLogic.this.setSuccessMessage(event, "/session", message);
        this.setFailedMessage(event, "/session");
        event.submit();
    }
}
