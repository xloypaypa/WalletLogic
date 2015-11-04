package model.config;

import model.db.DBTable;
import model.db.UserCollection;
import model.session.SessionManager;
import model.tool.PasswordEncoder;

import java.nio.channels.SocketChannel;

/**
 * Created by xlo on 2015/11/4.
 * it's the user access config
 */
public class UserAccessConfig implements ConfigInterface {

    public static UserAccessConfig getConfig() {
        return (UserAccessConfig) ConfigManager.getConfigManager().getConfig(UserAccessConfig.class);
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void reload() throws Exception {

    }

    public boolean checkUser(String username, String password, SocketChannel socketChannel) {
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
}
