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

    public void clientKey(byte[] data) {
        event.setEventAction(() -> {
            SessionManager.getSessionManager().getSessionMessage(socket).setPublicKey(RSA.bytes2PublicKey(data));
            SessionManager.getSessionManager().getSessionMessage(socket).setClientEncryption(true);
            return true;
        });
        this.setDefaultMessage(event, "/clientKey");
        event.submit();
    }

    public void serverKey() {
        event.setEventAction(() -> {
            SessionManager.getSessionManager().getSessionMessage(socket).setServerEncryption(true);
            return true;
        });
        event.sendWhileSuccess("/serverKey", RSA.publicKey2Bytes(EncryptionConfig.getConfig().getKeyPair().getPublic()));
        this.setFailedMessage(event, "/serverKey");
        event.submit();
    }
}
