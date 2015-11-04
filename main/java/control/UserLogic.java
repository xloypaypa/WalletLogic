package control;

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
                if (username == null || password == null) {
                    return false;
                }
                UserCollection userCollection = new UserCollection();
                DBTable.DBData user = userCollection.getUserData(username);
                if (user == null) {
                    return false;
                }
                String ans = PasswordEncoder.encode(user.object.get("password").toString() + SessionManager.getSessionManager().getSessionID(socketChannel.socket()));
                return password.equals(ans);
            }
        };
        event.submit();
    }

    public void register(String username, String password) {
        SendEvent event = new SendEvent(socketChannel) {
            @Override
            public boolean run() throws Exception {
                if (username == null || password == null) {
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
