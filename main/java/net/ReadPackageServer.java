package net;

import net.server.AbstractServer;
import net.tool.connectionManager.ConnectionManager;
import net.tool.connectionSolver.ConnectionMessage;
import net.tool.connectionSolver.ConnectionMessageImpl;
import net.tool.connectionSolver.ConnectionStatus;
import net.tool.packageSolver.PackageStatus;
import net.tool.packageSolver.packageReader.HttpPackageReader;
import net.tool.packageSolver.packageReader.PackageReader;

import java.io.IOException;

/**
 * Created by xlo on 15-11-2.
 * it's the server read page
 */
public class ReadPackageServer extends AbstractServer {
    protected PackageReader packageReader;

    public ReadPackageServer() {
        super(new ConnectionMessageImpl());
    }

    public ConnectionStatus whenInit() {
        this.packageReader = new HttpPackageReader(this.getConnectionMessage().getSocket());
        //TODO build writer
        return ConnectionStatus.READING;
    }

    public ConnectionStatus whenConnecting() {
        return null;
    }

    public ConnectionStatus whenReading() {
        try {
            PackageStatus packageStatus = packageReader.read();
            if (packageStatus.equals(PackageStatus.END)) {
                //TODO commit event
                return ConnectionStatus.READING;
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
        return null;
    }

    public ConnectionStatus whenClosing() {
        if (this.getConnectionMessage() != null) {
            ConnectionManager.getSolverManager().removeConnection(this.getConnectionMessage().getSocket().socket());
            this.getConnectionMessage().closeSocket();
        }
        return null;
    }

    public ConnectionStatus whenError() {
        return ConnectionStatus.CLOSE;
    }

    public ConnectionStatus whenWaiting() {
        return ConnectionStatus.READING;
    }
}
