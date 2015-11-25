package control;

import model.config.EncryptionConfig;
import model.session.SessionManager;
import model.tool.RSA;

import java.net.Socket;

/**
 * Created by xlo on 2015/11/4.
 * it's the session logic
 */
public class SessionLogic extends LogicManager {

    public SessionLogic(Socket socket) {
        super(socket);
    }

    public void getSessionID() {
        event.setEventAction(() -> true);
        String message = SessionManager.getSessionManager().getSessionMessage(socket).getSessionID() + "";
        SessionLogic.this.setSuccessMessage(event, "/session", message);
        this.setFailedMessage(event, "/session");
        event.submit();
    }

    public void key(byte[] data) {
        event.setEventAction(() -> {
            SessionManager.getSessionManager().getSessionMessage(socket).setPublicKey(RSA.bytes2PublicKey(data));
            SessionManager.getSessionManager().getSessionMessage(socket).setEncryption(true);
            return true;
        });
        event.sendWhileSuccess("/key", RSA.publicKey2Bytes(EncryptionConfig.getConfig().getKeyPair().getPublic()));
        this.setFailedMessage(event, "/key");
        event.submit();
    }
}
