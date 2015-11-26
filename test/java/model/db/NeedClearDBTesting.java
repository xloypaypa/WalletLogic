package model.db;

import model.config.WalletDBConfig;
import org.junit.After;

import java.util.Map;

/**
 * Created by xlo on 2015/11/26.
 * it's the interface clear db
 */
public interface NeedClearDBTesting {

    default void clearDB() throws Exception {
        for (Map.Entry<String, String> name : WalletDBConfig.getConfig().getTables().entrySet()) {
            WalletCollection walletCollection = (WalletCollection) Class.forName(name.getKey()).newInstance();
            walletCollection.clearDB();
        }
    }
}
