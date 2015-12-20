package model.config;

import model.db.UserCollection;
import model.entity.UserEntity;
import model.tool.PasswordEncoder;

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

    public boolean checkUser(String username, String password, long sessionID) {
        if (username == null || password == null) {
            return false;
        }
        UserCollection userCollection = new UserCollection();
        UserEntity user = userCollection.getUserData(username);
        if (user == null) {
            return false;
        }
        String ans = PasswordEncoder.encode(user.getPassword() + sessionID);
        return password.equals(ans);
    }
}
