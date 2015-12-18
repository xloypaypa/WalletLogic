package control;

import model.db.DBTable;
import model.db.DetailCollection;
import model.session.SessionManager;

import java.net.Socket;
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
}
