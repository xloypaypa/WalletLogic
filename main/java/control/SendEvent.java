package control;

import javafx.util.Pair;
import model.db.event.Event;
import model.session.SessionManager;
import net.PackageServer;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xlo on 15-11-2.
 * it's the event
 */
public final class SendEvent extends Event {
    protected SocketChannel socketChannel;
    protected List<Pair<String, byte[]>> successMessage, failedMessage, commitMessage;
    protected EventAction eventAction;

    public SendEvent(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        this.successMessage = new ArrayList<>();
        this.failedMessage = new ArrayList<>();
        this.commitMessage = new ArrayList<>();
    }

    public void setEventAction(EventAction eventAction) {
        this.eventAction = eventAction;
    }

    public void sendWhileCommit(String url, byte[] message) {
        this.commitMessage.add(new Pair<>(url, message));
    }

    public void sendWhileSuccess(String url, byte[] message) {
        this.successMessage.add(new Pair<>(url, message));
    }

    public void sendWhileFailed(String url, byte[] message) {
        this.failedMessage.add(new Pair<>(url, message));
    }

    @Override
    protected void whenCommit() {
        super.whenCommit();
        PackageServer packageServer = SessionManager.getSessionManager().getPackageServer(socketChannel.socket());
        for (Pair<String, byte[]> now : this.commitMessage) {
            packageServer.addMessage(now.getKey(), now.getValue());
        }
    }

    @Override
    protected void whenFailed() {
        super.whenFailed();
        PackageServer packageServer = SessionManager.getSessionManager().getPackageServer(socketChannel.socket());
        for (Pair<String, byte[]> now : this.failedMessage) {
            packageServer.addMessage(now.getKey(), now.getValue());
        }
    }

    @Override
    protected void whenSucceed() {
        super.whenSucceed();
        PackageServer packageServer = SessionManager.getSessionManager().getPackageServer(socketChannel.socket());
        for (Pair<String, byte[]> now : this.successMessage) {
            packageServer.addMessage(now.getKey(), now.getValue());
        }
    }

    @Override
    public boolean run() throws Exception {
        return eventAction.action();
    }

    @FunctionalInterface
    public interface EventAction {
        boolean action();
    }
}
