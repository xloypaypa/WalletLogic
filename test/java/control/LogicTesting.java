package control;

import control.tool.BudgetLogicNoSend;
import control.tool.EdgeLogicNoSend;
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

    public static BudgetLogicNoSend createNewBudget(Socket socket, String type, String income, String expenditure) {
        BudgetLogicNoSend budgetLogic = new BudgetLogicNoSend(socket);
        budgetLogic.createBudget(type, income, expenditure);
        budgetLogic.waitEventEnd();
        return budgetLogic;
    }

    public static BudgetLogicNoSend getBudget(Socket socket) {
        BudgetLogicNoSend budgetLogic = new BudgetLogicNoSend(socket);
        budgetLogic.getBudget();
        budgetLogic.waitEventEnd();
        return budgetLogic;
    }

    public static EdgeLogicNoSend addEdge(Socket socket, String from, String to, String script) {
        EdgeLogicNoSend edgeLogic = new EdgeLogicNoSend(socket);
        edgeLogic.addEdge(from, to, script);
        edgeLogic.waitEventEnd();
        return edgeLogic;
    }

    public static EdgeLogicNoSend getEdge(Socket socket, String from, String to) {
        EdgeLogicNoSend edgeLogic = new EdgeLogicNoSend(socket);
        edgeLogic.getEdge(from, to);
        edgeLogic.waitEventEnd();
        return edgeLogic;
    }

    public static EdgeLogicNoSend removeEdge(Socket socket, String from, String to) {
        EdgeLogicNoSend edgeLogic = new EdgeLogicNoSend(socket);
        edgeLogic.removeEdge(from, to);
        edgeLogic.waitEventEnd();
        return edgeLogic;
    }

    public static EdgeLogicNoSend updateEdge(Socket socket, String from, String to, String script) {
        EdgeLogicNoSend edgeLogic = new EdgeLogicNoSend(socket);
        edgeLogic.updateEdge(from, to, script);
        edgeLogic.waitEventEnd();
        return edgeLogic;
    }

    public static EdgeLogicNoSend getEdgeSuper(Socket socket, String type) {
        EdgeLogicNoSend edgeLogic = new EdgeLogicNoSend(socket);
        edgeLogic.getSuperEdgeList(type);
        edgeLogic.waitEventEnd();
        return edgeLogic;
    }

    public static EdgeLogicNoSend getEdgeExtend(Socket socket, String type) {
        EdgeLogicNoSend edgeLogic = new EdgeLogicNoSend(socket);
        edgeLogic.getExtendEdgeList(type);
        edgeLogic.waitEventEnd();
        return edgeLogic;
    }

}
