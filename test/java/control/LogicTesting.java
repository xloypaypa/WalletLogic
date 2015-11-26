package control;

import control.tool.MoneyLogicNoSend;
import control.tool.UserLogicNoSend;
import model.db.NeedClearDBTesting;
import model.session.SessionManager;
import model.tool.PasswordEncoder;
import org.junit.After;
import org.junit.Before;

import java.net.Socket;

/**
 * Created by xlo on 2015/11/25.
 * it's the class for init testing
 */
public abstract class LogicTesting implements NeedClearDBTesting {

    protected Socket socket;

    @Before
    public void setUp() throws Exception {
        this.socket = new Socket();
        SessionManager.getSessionManager().registerSession(this.socket);
    }

    @After
    public void tearDown() throws Exception {
        clearDB();
    }

    public static UserLogicNoSend registerUser(Socket socket, String username, String password) throws Exception {
        UserLogicNoSend userLogic = new UserLogicNoSend(socket);
        userLogic.register(username, password);
        userLogic.waitEventEnd();
        return userLogic;
    }

    public static UserLogicNoSend loginUser(Socket socket, String username, String password) throws Exception {
        UserLogicNoSend userLogic = new UserLogicNoSend(socket);
        password = password + SessionManager.getSessionManager().getSessionMessage(socket).getSessionID();
        userLogic.login(username, PasswordEncoder.encode(password));
        userLogic.waitEventEnd();
        return userLogic;
    }

    public static MoneyLogicNoSend getMoney(Socket socket) {
        MoneyLogicNoSend moneyLogic = new MoneyLogicNoSend(socket);
        moneyLogic.getMoney();
        moneyLogic.waitEventEnd();
        return moneyLogic;
    }

    public static MoneyLogicNoSend createNewMoney(Socket socket, String type, double value) {
        MoneyLogicNoSend moneyLogic = new MoneyLogicNoSend(socket);
        moneyLogic.createMoney(type, value + "");
        moneyLogic.waitEventEnd();
        return moneyLogic;
    }

    public static MoneyLogicNoSend removeMoney(Socket socket, String type) {
        MoneyLogicNoSend moneyLogic = new MoneyLogicNoSend(socket);
        moneyLogic.removeMoney(type);
        moneyLogic.waitEventEnd();
        return moneyLogic;
    }

    public static MoneyLogicNoSend renameMoney(Socket socket, String type, String name) {
        MoneyLogicNoSend moneyLogic = new MoneyLogicNoSend(socket);
        moneyLogic.renameMoney(type, name);
        moneyLogic.waitEventEnd();
        return moneyLogic;
    }

    public static MoneyLogicNoSend transferMoney(Socket socket, String from, String to, double value) {
        MoneyLogicNoSend moneyLogic = new MoneyLogicNoSend(socket);
        moneyLogic.transferMoney(from, to, value + "");
        moneyLogic.waitEventEnd();
        return moneyLogic;
    }

}
