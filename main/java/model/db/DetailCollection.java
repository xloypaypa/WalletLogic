package model.db;

import org.bson.Document;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by xlo on 2015/12/17.
 * it's the detail collection
 */
public class DetailCollection extends WalletCollection {

    public void addDetail(String username, Date date, String event, Document document) {
        this.lockCollection();
        document.append("username", username)
                .append("date", date.getTime())
                .append("event", event);
        this.insert(document);
    }

    public List<DBData> getDetails(Document document) {
        this.lockCollection();
        return this.collection.find(document).stream()
                .map(this::addDocumentToUsing).collect(Collectors.toCollection(LinkedList::new));
    }

    public List<DBData> findDetails(String username, Date from, Date to) {
        Document document = new Document();
        document.append("username", username)
                .append("date", new Document("$gt", from.getTime()).append("$lt", to.getTime()));
        return this.getAllDataListData(document);
    }

}
