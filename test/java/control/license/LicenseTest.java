package control.license;

import control.LogicTesting;
import control.tool.MoneyLogicNoSendNeedLicense;
import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/28.
 * it's the license test
 */
public class LicenseTest extends LogicTesting {

    @Test
    public void checkUrl() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        MoneyLogicNoSendNeedLicense moneyLogic = new MoneyLogicNoSendNeedLicense(this.socket);
        moneyLogic.createMoney("type", "100");
        moneyLogic.waitEventEnd();

        JSONObject jsonObject = JSONObject.fromObject(new String(moneyLogic.getEvent().getMessage().get(0).getValue()));

        assertEquals("fail", jsonObject.getString("result"));
    }

}
