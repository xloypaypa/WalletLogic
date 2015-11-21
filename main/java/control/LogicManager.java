package control;

import model.db.DBTable;
import model.session.SessionManager;
import net.PackageServer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 2015/11/3.
 * it's the logic manager
 */
public abstract class LogicManager {
    protected SocketChannel socketChannel;
    protected PackageServer packageServer;

    public LogicManager(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        this.packageServer = SessionManager.getSessionManager().getPackageServer(socketChannel.socket());
    }

    public void setDefaultMessage(SendEvent event, String url) {
        setSuccessMessage(event, url);
        setFailedMessage(event, url);
    }

    public void setSuccessMessage(SendEvent event, String url) {
        setSuccessMessage(event, url, "ok");
    }

    public void setSuccessMessage(SendEvent event, String url, Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(map);
        event.sendWhileSuccess(url, jsonObject.toString().getBytes());
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
