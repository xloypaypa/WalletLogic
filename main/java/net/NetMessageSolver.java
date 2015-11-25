package net;

import model.config.CommandConfig;
import model.config.EncryptionConfig;
import model.session.SessionManager;
import model.tool.RSA;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 2015/11/5.
 * it's the solver for net message
 */
public class NetMessageSolver {
    public static void sendEvent(URL url, byte[] message, SocketChannel socketChannel) throws Exception {
        if (SessionManager.getSessionManager().getSessionMessage(socketChannel.socket()).isServerEncryption()) {
            message = RSA.decrypt(EncryptionConfig.getConfig().getKeyPair().getPrivate(), message);
        }

        CommandConfig config = CommandConfig.getConfig();
        CommandConfig.PostInfo postInfo = config.findPostInfo(url.getPath());

        Object manager = buildManager(config, postInfo, socketChannel);
        Method method = getMethod(config, postInfo);
        Object[] data = getData(postInfo, message);
        method.invoke(manager, data);
    }

    private static Object[] getData(CommandConfig.PostInfo postInfo, byte[] message) {
        if (postInfo.isArray()) {
            JSONArray jsonArray = JSONArray.fromObject(new String(message));
            List<Map<String, String>> list = new LinkedList<>();
            for (Object now : jsonArray) {
                JSONObject object = (JSONObject) now;
                Map<String, String> map = new HashMap<>();
                for (String title : postInfo.getMethodData()) {
                    map.put(title, object.getString(title));
                }
                list.add(map);
            }
            Object[] result = new Object[1];
            result[0] = list;
            return result;
        } else if (postInfo.isData()) {
            Object[] result = new Object[1];
            result[0] = message;
            return result;
        } else {
            JSONObject jsonObject = JSONObject.fromObject(new String(message));
            String[] data = new String[postInfo.getMethodData().size()];
            for (int i = 0; i < data.length; i++) {
                String title = postInfo.getMethodData().get(i);
                data[i] = jsonObject.getString(title);
            }
            return data;
        }
    }

    private static Method getMethod(CommandConfig config, CommandConfig.PostInfo postInfo) throws ClassNotFoundException, NoSuchMethodException {
        if (postInfo.isArray()) {
            Class[] param = {List.class};
            return config.getMethod(postInfo.getManager(), postInfo.getMethod(), param);
        } else if (postInfo.isData()) {
            Class[] param = {byte[].class};
            return config.getMethod(postInfo.getManager(), postInfo.getMethod(), param);
        } else {
            Class[] param = new Class[postInfo.getMethodData().size()];
            for (int i = 0; i < param.length; i++) {
                param[i] = String.class;
            }
            return config.getMethod(postInfo.getManager(), postInfo.getMethod(), param);
        }
    }

    private static Object buildManager(CommandConfig config, CommandConfig.PostInfo postInfo, SocketChannel socketChannel) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> constructor = config.getManagerConstructor(postInfo.getManager());
        return constructor.newInstance(socketChannel.socket());
    }
}
