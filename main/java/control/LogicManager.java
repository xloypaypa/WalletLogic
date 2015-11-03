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
        event.sendWhileSuccess(url, "ok".getBytes());
        event.sendWhileFailed(url, "fail".getBytes());
    }
}
