package control;

import model.config.UserAccessConfig;
import model.db.DBTable;
import model.db.UserCollection;
import model.session.SessionManager;
import model.tool.PasswordEncoder;

import java.nio.channels.SocketChannel;

/**
 * Created by xlo on 2015/11/3.
 * it's the user logic
 */
public class UserLogic extends LogicManager {
    public UserLogic(SocketChannel socketChannel) {
        super(socketChannel);
    }

    public void login(String username, String password) {
        SendEvent event = new SendEvent(socketChannel) {
            @Override
            public boolean run() throws Exception {
                boolean result = UserAccessConfig.getConfig().checkUser(username, password, socketChannel);
                if (result) {
                    SessionManager.getSessionManager().loginSession(socketChannel.socket(), username);
                }
                return result;
            }
        };
        this.setDefaultMessage(event, "/login");
        event.submit();
    }

    public void register(String username, String password) {
        SendEvent event = new SendEvent(socketChannel) {
            @Override
            public boolean run() throws Exception {
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
            }
        };
        this.setDefaultMessage(event, "/register");
        event.submit();
    }
}
