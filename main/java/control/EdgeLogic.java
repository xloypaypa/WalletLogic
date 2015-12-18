package control;

import control.logic.manager.EdgeManager;
import model.config.WalletDBConfig;
import model.db.BudgetCollection;
import model.db.BudgetEdgeCollection;
import model.session.SessionManager;
import org.bson.Document;

import java.net.Socket;

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
            return username != null && new EdgeManager(username).addEdge(fromType, toType, script);

        });
        this.setDefaultMessage(event, "/addEdge");
        event.submit();
    }

    public void removeEdge(String fromType, String toType) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            return username != null && new EdgeManager(username).removeEdge(fromType, toType);
        });
        this.setDefaultMessage(event, "/removeEdge");
        event.submit();
    }

    public void updateEdge(String fromType, String toType, String script) {
        event.setEventAction(() -> {
            String username = SessionManager.getSessionManager().getUsername(socket);
            return username != null && new EdgeManager(username).updateEdge(fromType, toType, script);
        });
        this.setDefaultMessage(event, "/updateEdge");
        event.submit();
    }



}
