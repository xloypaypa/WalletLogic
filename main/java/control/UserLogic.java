package control;

import model.db.UserCollection;

import java.nio.channels.SocketChannel;

/**
 * Created by xlo on 2015/11/3.
 * it's the user logic
 */
public class UserLogic extends LogicManager {
    public UserLogic(SocketChannel socketChannel) {
        super(socketChannel);
    }

    public void register(String username, String password) {
        SendEvent event = new SendEvent(socketChannel) {
            @Override
            public boolean run() throws Exception {
                UserCollection userCollection = new UserCollection();
                if (userCollection.getUserData(username)==null) {
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
