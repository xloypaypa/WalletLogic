package model.db;

import model.entity.DetailEntity;
import org.bson.BsonArray;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xlo on 2015/12/17.
 * it's the detail collection
 */
public class DetailCollection extends WalletCollection {

    public void addDetail(String username, Date date, String event, BsonArray rollBackMessage, Document document) {
        document.append("username", username)
                .append("date", date.getTime())
                .append("roll back call", rollBackMessage)
                .append("event", event);
        this.insertData(document);
    }

    public DetailEntity findLastDetail(String username) {
        List<DBData> allDataListData = this.getAllDataListData(new Document("username", username));
        sortData(allDataListData);
        if (allDataListData.size() != 0) {
            return new DetailEntity(allDataListData.get(0));
        } else {
            return null;
        }
    }

    public List<DetailEntity> findDetails(String username, Date from, Date to) {
        Document document = new Document();
        document.append("username", username)
                .append("date", new Document("$gt", from.getTime()).append("$lt", to.getTime()));
        List<DBData> allDataListData = this.getAllDataListData(document);
        sortData(allDataListData);

        return allDataListData.stream().map(DetailEntity::new).collect(Collectors.toCollection(LinkedList::new));
    }

    public void removeLastDetail(String username) {
        this.lockCollection();
        List<DBData> allDataListData = this.getAllDataListData(new Document("username", username));
        sortData(allDataListData);
        if (allDataListData.size() != 0) {
            remove(new Document(getIdObjectKey(), allDataListData.get(0).id));
        }
    }

    private void sortData(List<DBData> allDataListData) {
        allDataListData.sort((o1, o2) -> {
            ObjectId v1 = (ObjectId) o1.object.get("_id");
            ObjectId v2 = (ObjectId) o2.object.get("_id");
            return -v1.compareTo(v2);
        });
    }

}
