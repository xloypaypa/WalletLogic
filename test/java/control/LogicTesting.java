package control;

import com.mongodb.DBCollection;
import model.config.WalletDBConfig;
import model.db.UserCollection;
import model.db.WalletCollection;
import model.session.SessionManager;
import org.junit.After;
import org.junit.Before;

import java.net.Socket;
import java.util.Map;

/**
 * Created by xlo on 2015/11/25.
 * it's the class for init testing
 */
public class LogicTesting {

    protected Socket socket;

    @Before
    public void setUp() throws Exception {
        this.socket = new Socket();
        SessionManager.getSessionManager().registerSession(this.socket);
    }

    @After
    public void tearDown() throws Exception {
        for (Map.Entry<String, String> name : WalletDBConfig.getConfig().getTables().entrySet()) {
            WalletCollection walletCollection = (WalletCollection) Class.forName(name.getKey()).newInstance();
            walletCollection.clearDB();
        }
    }

}
