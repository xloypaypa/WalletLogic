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

    public void addDetail(String username, Date date, Document document) {
        this.lockCollection();
        Document result = new Document();
        result.append("username", username).append("date", date.getTime()).append("message", document);
        this.insert(result);
    }

    public List<DBData> getDetails(String username, Date from, Date to) {
        this.lockCollection();
        Document document = new Document();
        document.append("username", username)
                .append("date", new Document("$gt", from.getTime()).append("$lt", to.getTime()));
        List<Map<String, Object>> list = this.collection.find(document);
        List<DBData> ans = list.stream().map(this::addDocumentToUsing).collect(Collectors.toCollection(LinkedList::new));
        this.unlockCollection();
        return ans;
    }

}
