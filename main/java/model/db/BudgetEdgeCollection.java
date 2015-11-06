package model.db;

import org.bson.Document;

import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 2015/11/6.
 * it's the budge edge collection
 */
public class BudgetEdgeCollection extends WalletCollection {

    public void addEege(String from, String to, String script) {
        this.lockCollection();
        Document document = new Document();
        document.append("from", from)
                .append("to", to)
                .append("script", script);
        this.insert(document);
    }

    public DBData getEdge(String from, String to) {
        this.lockCollection();
        List<Map<String, Object>> list =  this.collection.find(new Document().append("from", from).append("to", to));
        if (list.size()==0) {
            return null;
        } else {
            return addDocumentToUsing(list.get(0));
        }
    }

    public DBData getEdgeData(String from, String to) {
        this.lockCollection();
        List<Map<String, Object>> list =  this.collection.find(new Document().append("from", from).append("to", to));
        DBData ans;
        if (list.size()==0) {
            ans = null;
        } else {
            ans = getDocumentNotUsing(list.get(0));
        }
        this.unlockCollection();
        return ans;
    }

    public void remove(String from, String to) {
        this.lockCollection();
        this.remove(new Document("from", from).append("to", to));
    }
}
