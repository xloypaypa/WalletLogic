package control.license;

import control.LogicTesting;
import control.NoLicenseException;
import control.tool.MoneyLogicNoSendNeedLicense;
import org.junit.Test;

/**
 * Created by xlo on 2015/12/28.
 * it's the license test
 */
public class LicenseTest extends LogicTesting {

    @Test (expected = NoLicenseException.class)
    public void checkUrl() throws Exception {
        registerUser(this.socket, "username", "password");
        loginUser(this.socket, "username", "password");
        MoneyLogicNoSendNeedLicense moneyLogic = new MoneyLogicNoSendNeedLicense(this.socket);
        moneyLogic.createMoney("type", "100");
        moneyLogic.waitEventEnd();
    }

}
