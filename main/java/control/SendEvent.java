package control;

import javafx.util.Pair;
import model.db.event.Event;
import net.PackageServer;
import net.tool.connectionManager.ConnectionManager;

import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xlo on 15-11-2.
 * it's the event
 */
public abstract class SendEvent extends Event {
    protected SocketChannel socketChannel;
    protected PackageServer packageServer;
    protected Set<Pair<String, byte[]>> successMessage, failedMessage, commitMessage;

    public SendEvent(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        this.successMessage = new HashSet<>();
        this.failedMessage = new HashSet<>();
        this.commitMessage = new HashSet<>();
        this.packageServer = (PackageServer) ConnectionManager.getSolverManager().getSolver(this.socketChannel.socket());
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
        for (Pair<String, byte[]> now : this.commitMessage) {
            this.packageServer.addMessage(now.getKey(), now.getValue());
        }
    }

    @Override
    protected void whenFailed() {
        super.whenFailed();
        for (Pair<String, byte[]> now : this.failedMessage) {
            this.packageServer.addMessage(now.getKey(), now.getValue());
        }
    }

    @Override
    protected void whenSucceed() {
        super.whenSucceed();
        for (Pair<String, byte[]> now : this.successMessage) {
            this.packageServer.addMessage(now.getKey(), now.getValue());
        }
    }
}
