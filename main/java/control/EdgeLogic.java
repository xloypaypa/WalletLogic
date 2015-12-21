package control;

import control.logic.manager.EdgeManager;
import model.config.WalletDBConfig;
import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.db.DetailCollection;
import model.entity.EdgeEntity;
import model.session.SessionManager;

import java.net.Socket;
import java.util.*;

/**
 * Created by xlo on 2015/11/6.
 * it's the edge logic
 */
public class EdgeLogic extends LogicManager {

    public EdgeLogic(Socket socket) {
        super(socket);
    }

    public void getEdgeList() {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            if (username == null) {
                return false;
            }

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
            String username = SessionManager.getSessionManager().getUsername(socket);
            EdgeManager edgeManager = new EdgeManager(username);
            if (username != null && edgeManager.addEdge(fromType, toType, script)) {
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
            String username = SessionManager.getSessionManager().getUsername(socket);
            EdgeManager edgeManager = new EdgeManager(username);
            if (username != null && edgeManager.removeEdge(fromType, toType)) {
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
            String username = SessionManager.getSessionManager().getUsername(socket);
            EdgeManager edgeManager = new EdgeManager(username);
            if (username != null && edgeManager.updateEdge(fromType, toType, fromType, toType, script)) {
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
