package control;

import model.session.SessionManager;

import java.nio.channels.SocketChannel;

/**
 * Created by xlo on 2015/11/4.
 * it's the session logic
 */
public class SessionLogic extends LogicManager {
    public SessionLogic(SocketChannel socketChannel) {
        super(socketChannel);
    }

    public void getSessionID() {
        SendEvent event = new SendEvent(socketChannel) {
            @Override
            public boolean run() throws Exception {
                return true;
            }
        };
        this.setSuccessMessage(event, "/session", SessionManager.getSessionManager().getSessionID(socketChannel.socket()) + "");
        this.setFailedMessage(event, "/session");
        event.submit();
    }
}
