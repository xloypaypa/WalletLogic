package control;

import model.config.UserAccessConfig;
import model.db.UserCollection;
import model.session.SessionManager;

import java.net.Socket;

/**
 * Created by xlo on 2015/11/3.
 * it's the user logic
 */
public class UserLogic extends LogicManager {

    public UserLogic(Socket socket) {
        super(socket);
    }

    public void login(String username, String password) {
        event.setEventAction(() -> {
            long sessionID = SessionManager.getSessionManager().getSessionMessage(socket).getSessionID();
            boolean result = UserAccessConfig.getConfig().checkUser(username, password, sessionID);
            if (result) {
                SessionManager.getSessionManager().loginSession(socket, username);
            }
            return result;
        });
        this.setDefaultMessage(event, "/login");
        event.submit();
    }

    public void register(String username, String password) {
        event.setEventAction(() -> {
            if (username == null || username.length() == 0 || password == null) {
                return false;
            }
            UserCollection userCollection = new UserCollection();
            if (userCollection.getUserData(username) == null) {
                userCollection.addUser(username, password);
                return true;
            } else {
                return false;
            }
        });
        this.setDefaultMessage(event, "/register");
        event.submit();
    }
}
