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
    protected Map<Socket, SessionMessage> sessionMessage;

    private static SessionManager sessionManager;

    protected SessionManager() {
        this.sessionMessage = new HashMap<>();
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
        this.sessionMessage.put(socket, new SessionMessage());
        this.sessionMessage.get(socket).setSessionID(Math.abs(new Random().nextLong()));
    }

    public SessionMessage getSessionMessage(Socket socket) {
        return this.sessionMessage.get(socket);
    }

    public void removeSession(Socket socket) {
        this.sessionMessage.remove(socket);
    }

    public void loginSession(Socket socket, String username) {
        this.sessionMessage.get(socket).setUsername(username);
    }

    public boolean isLogin(Socket socket) {
        return this.sessionMessage.get(socket).getUsername() != null;
    }

    public String getUsername(Socket socket) {
        return this.sessionMessage.get(socket).getUsername();
    }

    public class SessionMessage {
        protected long sessionID;
        protected String username;

        public void setSessionID(long sessionID) {
            this.sessionID = sessionID;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public long getSessionID() {
            return sessionID;
        }

        public String getUsername() {
            return username;
        }
    }
}
