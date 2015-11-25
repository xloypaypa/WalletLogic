package control;

import control.tool.SessionLogicNoSend;
import model.config.EncryptionConfig;
import model.session.SessionManager;
import model.tool.RSA;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.security.PublicKey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void sendServerKey() throws Exception {
        this.sessionLogic.key(RSA.publicKey2Bytes(RSA.buildKeyPair().getPublic()));
        this.sessionLogic.waitEventEnd();

        assertEquals("/key", this.sessionLogic.getEvent().getMessage().get(0).getKey());
    }

    @Test
    public void shouldOpenServerEncryption() throws Exception {
        this.sessionLogic.key(RSA.publicKey2Bytes(RSA.buildKeyPair().getPublic()));
        this.sessionLogic.waitEventEnd();
        assertTrue(SessionManager.getSessionManager().getSessionMessage(this.socket).isEncryption());
    }

    @Test
    public void shouldSendServerKey() throws Exception {
        this.sessionLogic.key(RSA.publicKey2Bytes(RSA.buildKeyPair().getPublic()));
        this.sessionLogic.waitEventEnd();

        PublicKey publicKey = RSA.bytes2PublicKey(this.sessionLogic.getEvent().getMessage().get(0).getValue());
        assertEquals(EncryptionConfig.getConfig().getKeyPair().getPublic(), publicKey);
    }

}