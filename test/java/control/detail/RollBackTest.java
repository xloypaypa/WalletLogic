package control.detail;

import control.LogicTesting;
import model.db.DetailCollection;
import model.db.lock.NameLockManager;
import model.entity.DetailEntity;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertNull;

/**
 * Created by xlo on 2015/12/29.
 * it's the roll back test
 */
public class RollBackTest extends LogicTesting {

    @Test
    public void checkUrl() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        createNewMoney(this.socket, "type", 100);

        rollBack(this.socket);

        DetailCollection detailCollection = new DetailCollection();
        DetailEntity detailEntity = detailCollection.findLastDetail("username");
        assertNull(detailEntity);
    }

}
