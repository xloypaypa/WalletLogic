package control;

import model.db.DBTable;
import net.PackageServer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.tool.connectionManager.ConnectionManager;

import java.nio.channels.SocketChannel;
import java.util.List;

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

    public void setSuccessMessage(SendEvent event, String url) {
        setSuccessMessage(event, url, "ok");
    }

    public void setSuccessMessage(SendEvent event, String url, String message) {
        byte[] bytes = buildDefaultResult(message);
        event.sendWhileSuccess(url, bytes);
    }

    public void setSuccessMessage(SendEvent event, String url, List<DBTable.DBData> list) {
        byte[] bytes = buildArrayResult(list);
        event.sendWhileSuccess(url, bytes);
    }

    public void setFailedMessage(SendEvent event, String url) {
        setFailedMessage(event, url, "fail");
    }

    public void setFailedMessage(SendEvent event, String url, String message) {
        byte[] bytes = buildDefaultResult(message);
        event.sendWhileFailed(url, bytes);
    }

    protected byte[] buildDefaultResult(String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", message);
        return jsonObject.toString().getBytes();
    }

    protected byte[] buildArrayResult(List<DBTable.DBData> list) {
        JSONArray jsonArray = new JSONArray();
        for (DBTable.DBData now : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(now.object);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString().getBytes();
    }
}
