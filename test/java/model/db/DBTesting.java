package model.db;

import org.junit.After;

/**
 * Created by xlo on 2015/11/26.
 * it's the abstract db testing
 */
public abstract class DBTesting implements NeedClearDBTesting {

    @After
    public void tearDown() throws Exception {
        clearDB();
    }
}
