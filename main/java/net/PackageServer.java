package net;

import model.session.SessionManager;
import net.server.AbstractServer;
import net.tool.connectionManager.ConnectionManager;
import net.tool.connectionSolver.ConnectionMessageImpl;
import net.tool.connectionSolver.ConnectionStatus;
import net.tool.packageSolver.PackageStatus;
import net.tool.packageSolver.headSolver.HttpRequestHeadSolver;
import net.tool.packageSolver.packageReader.HttpPackageReader;
import net.tool.packageSolver.packageReader.PackageReader;
import net.tool.packageSolver.packageWriter.HttpPackageWriter;
import net.tool.packageSolver.packageWriter.PackageWriter;
import net.tool.packageSolver.packageWriter.packageWriterFactory.HttpRequestPackageWriterFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * Created by xlo on 15-11-2.
 * it's the server read page
 */
public class PackageServer extends AbstractServer {
    protected volatile PackageReader packageReader;
    protected volatile PackageWriter packageWriter;

    public PackageServer() {
        super(new ConnectionMessageImpl());
    }

    @Override
    public ConnectionStatus whenInit() {
        this.packageReader = new HttpPackageReader(this.getConnectionMessage().getSocket());
        this.packageWriter = new HttpPackageWriter(this.getConnectionMessage().getSocket());
        SessionManager.getSessionManager().registerSession(this.getConnectionMessage().getSocket().socket());
        return ConnectionStatus.READING;
    }

    @Override
    public ConnectionStatus whenConnecting() {
        return null;
    }

    @Override
    public ConnectionStatus whenReading() {
        try {
            PackageStatus packageStatus = packageReader.read();
            if (packageStatus.equals(PackageStatus.END)) {
                HttpRequestHeadSolver httpRequestHeadSolver = (HttpRequestHeadSolver) this.packageReader.getHeadPart();
                try {
                    NetMessageSolver.sendEvent(httpRequestHeadSolver.getUrl(), this.packageReader.getBody(), this.getConnectionMessage().getSocket());
                } catch (Exception e) {
                    return ConnectionStatus.ERROR;
                }
                return ConnectionStatus.READING;
            } else if (packageStatus.equals(PackageStatus.WAITING)) {
                return getNextStatusWhenPackageEnd();
            } else if (packageStatus.equals(PackageStatus.ERROR)) {
                return ConnectionStatus.ERROR;
            } else if (packageStatus.equals(PackageStatus.CLOSED)) {
                return ConnectionStatus.CLOSE;
            } else {
                return ConnectionStatus.READING;
            }
        } catch (IOException e) {
            return ConnectionStatus.ERROR;
        }
    }

    @Override
    public ConnectionStatus whenWriting() {
        try {
            PackageStatus packageStatus = this.packageWriter.write();
            if (packageStatus.equals(PackageStatus.END)) {
                return getNextStatusWhenPackageEnd();
            } else if (packageStatus.equals(PackageStatus.WAITING)) {
                return ConnectionStatus.WAITING;
            } else if (packageStatus.equals(PackageStatus.ERROR)) {
                return ConnectionStatus.ERROR;
            } else if (packageStatus.equals(PackageStatus.CLOSED)) {
                return ConnectionStatus.CLOSE;
            } else {
                return ConnectionStatus.READING;
            }
        } catch (IOException e) {
            return ConnectionStatus.ERROR;
        }
    }

    protected ConnectionStatus getNextStatusWhenPackageEnd() {
        if (this.packageWriter.getPackageQueueSize() == 0) {
            this.getConnectionMessage().getSelectionKey().interestOps(SelectionKey.OP_READ);
        } else {
            this.getConnectionMessage().getSelectionKey().interestOps(SelectionKey.OP_WRITE);
        }
        return ConnectionStatus.WAITING;
    }

    @Override
    public ConnectionStatus whenClosing() {
        if (this.getConnectionMessage() != null) {
            SessionManager.getSessionManager().removeSession(this.getConnectionMessage().getSocket().socket());
            ConnectionManager.getSolverManager().removeConnection(this.getConnectionMessage().getSocket().socket());
            this.getConnectionMessage().closeSocket();
        }
        return null;
    }

    @Override
    public ConnectionStatus whenError() {
        return ConnectionStatus.CLOSE;
    }

    @Override
    public ConnectionStatus whenWaiting() {
        if (this.packageWriter.getPackageQueueSize() == 0) {
            return ConnectionStatus.READING;
        } else {
            return ConnectionStatus.WRITING;
        }
    }

    public void addMessage(String url, byte[] message) {
        byte[] httpPackageBytes = HttpRequestPackageWriterFactory.getHttpReplyPackageWriterFactory()
                .setCommand("GET")
                .setHost("client")
                .setUrl(url)
                .setVersion("HTTP/1.1")
                .addMessage("Content-Length", message.length + "")
                .setBody(message).getHttpPackageBytes();
        this.packageWriter.addPackage(httpPackageBytes, 0);
        this.getConnectionMessage().getSelectionKey().interestOps(SelectionKey.OP_WRITE);
        this.getConnectionMessage().getSelectionKey().selector().wakeup();
    }
}
