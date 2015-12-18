package model.db;

import org.bson.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by xlo on 2015/12/17.
 * it's the detail collection
 */
public class DetailCollection extends WalletCollection {

    public void addDetail(String username, Date date, String event, Document document) {
        document.append("username", username)
                .append("date", date.getTime())
                .append("event", event);
        this.insertData(document);
    }

    public DBData findLastDetail(String username) {
        List<DBData> allDataListData = this.getAllDataListData(new Document("username", username));
        sortData(allDataListData);
        if (allDataListData.size() != 0) {
            return allDataListData.get(0);
        } else {
            return null;
        }
    }

    public List<DBData> findDetails(String username, Date from, Date to) {
        Document document = new Document();
        document.append("username", username)
                .append("date", new Document("$gt", from.getTime()).append("$lt", to.getTime()));
        List<DBData> allDataListData = this.getAllDataListData(document);
        sortData(allDataListData);
        return allDataListData;
    }

    private void sortData(List<DBData> allDataListData) {
        allDataListData.sort((o1, o2) -> {
            Long v1 = (Long) o1.object.get("date");
            Long v2 = (Long) o2.object.get("date");
            return -v1.compareTo(v2);
        });
    }

}
