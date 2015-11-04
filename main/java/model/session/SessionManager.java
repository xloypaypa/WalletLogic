package model.session;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by xlo on 2015/11/4.
 * it's the session manager
 */
public class SessionManager {
    protected Map<Socket, Long> sessionID;

    private static SessionManager sessionManager;

    protected SessionManager() {
        this.sessionID = new HashMap<>();
    }

    public static SessionManager getSessionManager() {
        if (sessionManager == null) {
            synchronized (SessionManager.class) {
                if (sessionManager == null) {
                    sessionManager = new SessionManager();
                }
            }
        }
        return sessionManager;
    }

    public void registerSession(Socket socket) {
        this.sessionID.put(socket, new Random().nextLong());
    }

    public long getSessionID(Socket socket) {
        return this.sessionID.get(socket);
    }

    public void removeSession(Socket socket) {
        this.sessionID.remove(socket);
    }
}
