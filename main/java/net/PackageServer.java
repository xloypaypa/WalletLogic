package net;

import control.UserLogic;
import net.server.AbstractServer;
import net.sf.json.JSONObject;
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
import java.net.URL;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xlo on 15-11-2.
 * it's the server read page
 */
public class PackageServer extends AbstractServer {
    protected PackageReader packageReader;
    protected PackageWriter packageWriter;
    protected List<byte[]> message;

    public PackageServer() {
        super(new ConnectionMessageImpl());
    }

    @Override
    public ConnectionStatus whenInit() {
        this.packageReader = new HttpPackageReader(this.getConnectionMessage().getSocket());
        this.packageWriter = new HttpPackageWriter(this.getConnectionMessage().getSocket());
        this.message = new LinkedList<>();
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
                sendEvent(httpRequestHeadSolver.getUrl(), this.packageReader.getBody());
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
        if (this.message.isEmpty()) {
            this.getConnectionMessage().getSelectionKey().interestOps(SelectionKey.OP_READ);
        } else {
            this.getConnectionMessage().getSelectionKey().interestOps(SelectionKey.OP_WRITE);
        }
        return ConnectionStatus.WAITING;
    }

    @Override
    public ConnectionStatus whenClosing() {
        if (this.getConnectionMessage() != null) {
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
        if (this.message.isEmpty()) {
            return ConnectionStatus.READING;
        } else {
            return ConnectionStatus.WRITING;
        }
    }

    public void addMessage(byte[] message) {
        this.message.add(HttpRequestPackageWriterFactory.getHttpReplyPackageWriterFactory()
        .setCommand("GET")
        .setHost("client")
        .setUrl("/reply")
        .setVersion("HTTP/1.1")
        .setBody(message).getHttpPackageBytes());
        this.getConnectionMessage().getSelectionKey().interestOps(SelectionKey.OP_WRITE);
        this.packageWriter.addPackage(message, 0);
    }

    protected void sendEvent(URL url, byte[] message) {
        System.out.println(url.toString() + " " + new String(message));
        JSONObject jsonObject = JSONObject.fromObject(new String(message));
        UserLogic userLogic = new UserLogic(this.getConnectionMessage().getSocket());
        userLogic.register(jsonObject.getString("username"), jsonObject.getString("password"));
    }
}
