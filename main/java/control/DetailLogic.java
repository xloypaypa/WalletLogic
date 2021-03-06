package control;

import model.config.WalletDBConfig;
import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.db.DetailCollection;
import model.db.MoneyCollection;
import model.db.event.Event;
import model.entity.DetailEntity;
import net.sf.json.JSONObject;
import org.bson.Document;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by xlo on 2015/12/18.
 * it's the detail logic
 */
public class DetailLogic extends NeedLicenseLogic {
    public DetailLogic(Socket socket) {
        super(socket);
    }

    public void getMoneyDetail(String fromTime, String toTime) {
        event.setEventAction(() -> {
            Date from = new Date(Long.valueOf(fromTime));
            Date to = new Date(Long.valueOf(toTime));

            DetailCollection detailCollection = new DetailCollection();
            List<DetailEntity> dataList = detailCollection.findDetails(username, from, to);
            List<Map<String, String>> mapList = new LinkedList<>();
            for (DetailEntity now : dataList) {
                if (now.haveItem("moneyType")) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("moneyType", now.getItem("moneyType"));
                    map.put("value", now.getItem("value"));
                    map.put("date", now.getDate().getTime() + "");
                    mapList.add(map);
                } else if (now.getEvent().equals("transferMoney")) {
                    HashMap<String, String> map;
                    map = new HashMap<>();
                    map.put("moneyType", now.getItem("toMoneyType"));
                    map.put("value", now.getItem("toMoneyValue"));
                    map.put("date", now.getDate().getTime() + "");
                    mapList.add(map);

                    map = new HashMap<>();
                    map.put("moneyType", now.getItem("fromMoneyType"));
                    map.put("value", now.getItem("fromMoneyValue"));
                    map.put("date", now.getDate().getTime() + "");
                    mapList.add(map);
                }
            }
            DetailLogic.this.setSuccessMessage(event, "/getMoneyDetail", mapList);
            return true;
        });
        this.setFailedMessage(event, "/getMoneyDetail");
        event.submit();
    }

    public void getDetailDetail(String id) {
        event.setEventAction(() -> {
            DetailEntity detailEntity = new DetailCollection().getDetail(username, id);
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(detailEntity.getData().object);
            jsonObject.remove("roll back call");
            this.setSuccessMessage(event, "/getDetailDetail", jsonObject.toString());
            return true;
        });
        this.setFailedMessage(event, "/getDetailDetail");
        event.submit();
    }

    public void getAllDetail(String fromTime, String toTime) {
        event.setEventAction(() -> {
            Date from = new Date(Long.valueOf(fromTime));
            Date to = new Date(Long.valueOf(toTime));

            DetailCollection detailCollection = new DetailCollection();
            List<DetailEntity> dataList = detailCollection.findDetails(username, from, to);
            List<Map<String, String>> mapList = new LinkedList<>();
            for (DetailEntity now : dataList) {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", now.getId());
                map.put("event", now.getEvent());
                map.put("date", now.getDate().getTime() + "");
                mapList.add(map);
            }
            DetailLogic.this.setSuccessMessage(event, "/getAllDetail", mapList);
            return true;
        });
        this.setFailedMessage(event, "/getAllDetail");
        event.submit();
    }

    public void rollBack() {
        event.setEventAction(() -> {
            event.lock(WalletDBConfig.getConfig().getDBLockName(BudgetCollection.class),
                    WalletDBConfig.getConfig().getDBLockName(MoneyCollection.class),
                    WalletDBConfig.getConfig().getDBLockName(BudgetEdgeCollection.class));
            DetailCollection detailCollection = new DetailCollection();
            DetailEntity lastDetail = detailCollection.findLastDetail(username);
            List<Document> bsonArray = lastDetail.getRollbackMessage();
            for (Document bsonDocument : bsonArray) {
                String managerName = ((Document)bsonDocument.get("manager")).getString("value");
                String methodName = ((Document)bsonDocument.get("method")).getString("value");
                List<String> param = new ArrayList<>();
                @SuppressWarnings("unchecked") ArrayList<Document> paramArray = (ArrayList<Document>) bsonDocument.get("param");
                param.addAll(paramArray.stream().map(paramValue -> paramValue.getString("value")).collect(Collectors.toList()));

                call(username, managerName, methodName, param);
            }
            detailCollection = new DetailCollection();
            detailCollection.removeLastDetail(username);
            return true;
        });
        this.setDefaultMessage(event, "/rollBack");
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
        new Event() {
            @Override
            public boolean run() throws Exception {
                method.invoke(manager, value);
                return true;
            }
        }.call();
    }
}
