package control;

import control.logic.manager.EdgeManager;
import model.config.WalletDBConfig;
import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.db.DetailCollection;
import model.session.SessionManager;
import org.bson.Document;

import java.net.Socket;
import java.util.Date;

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

            EdgeLogic.this.setSuccessMessage(event, "/getEdgeList", new BudgetEdgeCollection()
                    .getAllDataListData(new Document("username", username)));
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
                new DetailCollection().addDetail(username, new Date(), "addEdge", edgeManager.getUserRollBackMessage());
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
                new DetailCollection().addDetail(username, new Date(), "removeEdge", edgeManager.getUserRollBackMessage());
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
                new DetailCollection().addDetail(username, new Date(), "updateEdge", edgeManager.getUserRollBackMessage());
                return true;
            } else {
                return false;
            }
        });
        this.setDefaultMessage(event, "/updateEdge");
        event.submit();
    }



}
