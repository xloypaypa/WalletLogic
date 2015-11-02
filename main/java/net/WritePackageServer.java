package net;

import net.server.AbstractServer;
import net.tool.connectionSolver.ConnectionMessage;
import net.tool.connectionSolver.ConnectionMessageImpl;
import net.tool.connectionSolver.ConnectionStatus;
import net.tool.packageSolver.packageWriter.HttpPackageWriter;
import net.tool.packageSolver.packageWriter.PackageWriter;

import java.nio.channels.SocketChannel;

/**
 * Created by xlo on 15-11-2.
 * it's the server write package
 */
public class WritePackageServer extends AbstractServer {
    protected PackageWriter packageWriter;

    public WritePackageServer(SocketChannel socketChannel) {
        super(new ConnectionMessageImpl());
        this.connectionMessage.setSocket(socketChannel);
    }

    @Override
    public ConnectionStatus whenInit() {
        return null;
    }

    @Override
    public ConnectionStatus whenConnecting() {
        return null;
    }

    @Override
    public ConnectionStatus whenReading() {
        return null;
    }

    @Override
    public ConnectionStatus whenWriting() {
        return null;
    }

    @Override
    public ConnectionStatus whenClosing() {
        return null;
    }

    @Override
    public ConnectionStatus whenError() {
        return null;
    }

    @Override
    public ConnectionStatus whenWaiting() {
        return null;
    }
}
