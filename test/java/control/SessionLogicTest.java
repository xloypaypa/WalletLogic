package control;

import control.tool.SessionLogicNoSend;
import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by xlo on 2015/11/25.
 * it's the testing for session logic
 */
public class SessionLogicTest extends LogicTesting {

    protected SessionLogicNoSend sessionLogic;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.sessionLogic = new SessionLogicNoSend(this.socket);
    }

    @Test
    public void sendSessionIDShouldSendUrlToSession() throws Exception {
        this.sessionLogic.getSessionID();
        this.sessionLogic.waitEventEnd();

        assertEquals("/session", this.sessionLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void testSendSessionID() throws Exception {
        this.sessionLogic.getSessionID();
        this.sessionLogic.waitEventEnd();

        JSONObject jsonObject = JSONObject.fromObject(new String(this.sessionLogic.getEvent().getMessage().get(0).getValue()));

        assertNotNull(jsonObject.getLong("result"));
    }

}