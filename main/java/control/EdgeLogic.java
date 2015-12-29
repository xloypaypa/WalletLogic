package control;

import control.logic.manager.EdgeManager;
import model.config.WalletDBConfig;
import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.db.DetailCollection;
import model.entity.EdgeEntity;

import java.net.Socket;
import java.util.*;

/**
 * Created by xlo on 2015/11/6.
 * it's the edge logic
 */
public class EdgeLogic extends NeedLicenseLogic {

    public EdgeLogic(Socket socket) {
        super(socket);
    }

    public void getEdgeList() {
        event.setEventAction(() -> {
            List<EdgeEntity> listData = new BudgetEdgeCollection().getEdgeListData(username);
            List<Map<String, String>> mapList = new LinkedList<>();
            for (EdgeEntity now : listData) {
                HashMap<String, String> map = new HashMap<>();
                map.put("from", now.getFrom());
                map.put("to", now.getTo());
                map.put("script", now.getScript());
                mapList.add(map);
            }
            EdgeLogic.this.setSuccessMessage(event, "/getEdgeList", mapList);
            return true;
        });
        this.setFailedMessage(event, "/getEdgeList");
        event.submit();
    }

    public void addEdge(String fromType, String toType, String script) {
        event.setEventAction(() -> {
            event.lock(WalletDBConfig.getConfig().getDBLockName(BudgetCollection.class),
                    WalletDBConfig.getConfig().getDBLockName(BudgetEdgeCollection.class));
            EdgeManager edgeManager = new EdgeManager(username);
            if (edgeManager.addEdge(fromType, toType, script)) {
                new DetailCollection().addDetail(username, new Date(), "addEdge",
                        edgeManager.getUserRollBackMessage(), edgeManager.getDetailMessage());
                return true;
            } else {
                return false;
            }
        });
        this.setDefaultMessage(event, "/addEdge");
        event.submit();
    }

    public void removeEdge(String fromType, String toType) {
        event.setEventAction(() -> {
            EdgeManager edgeManager = new EdgeManager(username);
            if (edgeManager.removeEdge(fromType, toType)) {
                new DetailCollection().addDetail(username, new Date(), "removeEdge",
                        edgeManager.getUserRollBackMessage(), edgeManager.getDetailMessage());
                return true;
            } else {
                return false;
            }
        });
        this.setDefaultMessage(event, "/removeEdge");
        event.submit();
    }

    public void updateEdge(String fromType, String toType, String script) {
        event.setEventAction(() -> {
            EdgeManager edgeManager = new EdgeManager(username);
            if (edgeManager.updateEdge(fromType, toType, fromType, toType, script)) {
                new DetailCollection().addDetail(username, new Date(), "updateEdge",
                        edgeManager.getUserRollBackMessage(), edgeManager.getDetailMessage());
                return true;
            } else {
                return false;
            }
        });
        this.setDefaultMessage(event, "/updateEdge");
        event.submit();
    }



}
