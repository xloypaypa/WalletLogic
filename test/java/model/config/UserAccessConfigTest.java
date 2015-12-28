package model.config;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by xlo on 2015/12/28.
 * it's the user access config test
 */
public class UserAccessConfigTest {

    @Test
    public void should_only_have_root_need_for_money_logic() {
        Set<String> set = UserAccessConfig.getConfig().getNeedAccess("control.MoneyLogic");
        assertEquals(1, set.size());
        assertTrue(set.contains("root"));
    }

}