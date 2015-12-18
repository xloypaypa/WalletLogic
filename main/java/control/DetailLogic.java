package control;

import model.db.DBTable;
import model.db.DetailCollection;
import model.session.SessionManager;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xlo on 2015/12/18.
 * it's the detail logic
 */
public class DetailLogic extends LogicManager {
    public DetailLogic(Socket socket) {
        super(socket);
    }

    public void getDetail(String fromTime, String toTime) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            if (username == null) {
                return false;
            }

            Date from = new Date(Long.valueOf(fromTime));
            Date to = new Date(Long.valueOf(toTime));

            DetailCollection detailCollection = new DetailCollection();
            List<DBTable.DBData> dataList = detailCollection.findDetails(username, from, to);
            DetailLogic.this.setSuccessMessage(event, "/getDetail", dataList);
            return true;
        });
        this.setFailedMessage(event, "/getDetail");
        event.submit();
    }

    public void rollBack() {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            if (username == null) {
                return false;
            }

            DetailCollection detailCollection = new DetailCollection();
            BsonArray bsonArray = (BsonArray) detailCollection.findLastDetail(username).object.get("roll back call");
            for (BsonValue value : bsonArray.getValues()) {
                BsonDocument bsonDocument = (BsonDocument) value;
                String managerName = bsonDocument.getString("manager").getValue();
                String methodName = bsonDocument.getString("method").getValue();
                List<String> param = new ArrayList<>();
                BsonArray paramArray = bsonDocument.getArray("param");
                for (BsonValue paramValue : paramArray) {
                    BsonString paramString = (BsonString) paramValue;
                    param.add(paramString.getValue());
                }

                call(username, managerName, methodName, param);
            }
            return true;
        });
        this.setDefaultMessage(event, "/getDetail");
        event.submit();
    }

    private void call(String username, String managerName, String methodName, List<String> param) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> type = Class.forName(managerName);
        Class[] paramType = new Class[param.size()];
        Object[] value = new String[param.size()];
        for (int i = 0; i < param.size(); i++) {
            paramType[i] = String.class;
            value[i] = param.get(i);
        }
        Method method = type.getMethod(methodName, paramType);
        Object manager = type.getConstructor(String.class).newInstance(username);
        method.invoke(manager, value);
    }
}
