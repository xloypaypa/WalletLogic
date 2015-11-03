package control;

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
    protected Set<byte[]> successMessage, failedMessage, commitMessage;

    public SendEvent(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        this.successMessage = new HashSet<>();
        this.failedMessage = new HashSet<>();
        this.commitMessage = new HashSet<>();
        this.packageServer = (PackageServer) ConnectionManager.getSolverManager().getSolver(this.socketChannel.socket());
    }

    public void sendWhileCommit(byte[] message) {
        this.commitMessage.add(message);
    }

    public void sendWhileSuccess(byte[] message) {
        this.successMessage.add(message);
    }

    public void sendWhileFailed(byte[] message) {
        this.failedMessage.add(message);
    }

    @Override
    protected void whenCommit() {
        super.whenCommit();
        for (byte[] now : this.commitMessage) {
            this.packageServer.addMessage(now);
        }
    }

    @Override
    protected void whenFailed() {
        super.whenFailed();
        for (byte[] now : this.failedMessage) {
            this.packageServer.addMessage(now);
        }
    }

    @Override
    protected void whenSucceed() {
        super.whenSucceed();
        for (byte[] now : this.successMessage) {
            this.packageServer.addMessage(now);
        }
    }
}
