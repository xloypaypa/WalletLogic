package control;

import net.PackageServer;
import net.tool.connectionManager.ConnectionManager;

import java.nio.channels.SocketChannel;

/**
 * Created by xlo on 2015/11/3.
 * it's the logic manager
 */
public abstract class LogicManager {
    protected SocketChannel socketChannel;
    protected PackageServer packageServer;

    public LogicManager(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        this.packageServer = (PackageServer) ConnectionManager.getSolverManager()
                .getSolver(socketChannel.socket());
    }

    public void setDefaultMessage(SendEvent event, String url) {
        setSuccessMessage(event, url);
        setFailedMessage(event, url);
    }

    public void setFailedMessage(SendEvent event, String url) {
        event.sendWhileFailed(url, "fail".getBytes());
    }

    public void setSuccessMessage(SendEvent event, String url) {
        event.sendWhileSuccess(url, "ok".getBytes());
    }

    public void setSuccessMessage(SendEvent event, String url, String message) {
        event.sendWhileSuccess(url, message.getBytes());
    }

    public void setFailedMessage(SendEvent event, String url, String message) {
        event.sendWhileFailed(url, message.getBytes());
    }
}
