package net;

import model.config.CommandConfig;
import model.session.SessionManager;
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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.channels.SelectionKey;
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
                    sendEvent(httpRequestHeadSolver.getUrl(), this.packageReader.getBody());
                } catch (Exception e) {
                    return ConnectionStatus.ERROR;
                }
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
                this.message.remove(0);
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
        if (this.message.isEmpty()) {
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
        this.message.add(httpPackageBytes);
        this.getConnectionMessage().getSelectionKey().interestOps(SelectionKey.OP_WRITE);
        this.getConnectionMessage().getSelectionKey().selector().wakeup();
        this.packageWriter.addPackage(httpPackageBytes, 0);
    }

    protected void sendEvent(URL url, byte[] message) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        CommandConfig config = CommandConfig.getConfig();
        CommandConfig.PostInfo postInfo = config.findPostInfo(url.getPath());
        JSONObject jsonObject = JSONObject.fromObject(new String(message));

        Object manager = buildManager(config, postInfo);
        Method method = getMethod(config, postInfo);
        Object[] data = getData(postInfo, jsonObject);
        method.invoke(manager, data);
    }

    private String[] getData(CommandConfig.PostInfo postInfo, JSONObject jsonObject) {
        String[] data = new String[postInfo.getMethodData().size()];
        for (int i = 0; i < data.length; i++) {
            String title = postInfo.getMethodData().get(i);
            data[i] = jsonObject.getString(title);

        }
        return data;
    }

    private Method getMethod(CommandConfig config, CommandConfig.PostInfo postInfo) throws ClassNotFoundException, NoSuchMethodException {
        Class[] param = new Class[postInfo.getMethodData().size()];
        for (int i = 0; i < param.length; i++) {
            param[i] = String.class;

        }
        return config.getMethod(postInfo.getManager(), postInfo.getMethod(), param);
    }

    private Object buildManager(CommandConfig config, CommandConfig.PostInfo postInfo) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> constructor = config.getManagerConstructor(postInfo.getManager());
        return constructor.newInstance(this.getConnectionMessage().getSocket());
    }
}
