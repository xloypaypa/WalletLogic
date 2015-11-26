package control;

import model.db.NeedClearDBTesting;
import model.session.SessionManager;
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

}
