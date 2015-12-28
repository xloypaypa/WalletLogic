package model.config;

import model.db.NeedClearDBTesting;
import model.db.UserLicenseCollection;
import org.junit.After;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by xlo on 2015/12/28.
 * it's the user access config test
 */
public class UserAccessConfigTest implements NeedClearDBTesting {

    @After
    public void tearDown() throws Exception {
        clearDB();
    }

    @Test
    public void should_only_have_root_need_for_money_logic() {
        Set<String> set = UserAccessConfig.getConfig().getNeedAccess("control.MoneyLogic");
        assertEquals(1, set.size());
        assertTrue(set.contains("root"));
    }

    @Test
    public void should_not_have_license() {
        assertFalse(UserAccessConfig.getConfig().haveLicense("username", "control.MoneyLogic"));
    }

    @Test
    public void should_have_License_when_give_root_license() {
        UserLicenseCollection userLicenseCollection = new UserLicenseCollection();
        userLicenseCollection.giveLicense("username", "root");
        userLicenseCollection.submit();
        assertTrue(UserAccessConfig.getConfig().haveLicense("username", "control.MoneyLogic"));
    }

}