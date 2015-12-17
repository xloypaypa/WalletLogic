package control.logic;

import javafx.util.Pair;
import model.db.BudgetEdgeCollection;
import model.db.DBTable;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 2015/12/9.
 * it's the user edge
 */
public class UserEdge {

    private Map<String, EdgeNode> nodes;

    public UserEdge(String username) {
        BudgetEdgeCollection edgeCollection = new BudgetEdgeCollection();
        List<DBTable.DBData> userEdge = edgeCollection.findEdgeList(new Document("username", username));
        this.nodes = new HashMap<>();
        initKeyByType(userEdge);
        loadEdge(userEdge);
    }

    public boolean checkCouldAddEdge(String from, String to) {
        return !nodes.containsKey(from) || !nodes.containsKey(to) || !haveNext(to, from);
    }

    public List<Pair<String, String>> getFather(String typename) {
        if (!nodes.containsKey(typename)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(nodes.get(typename).getNext());
    }

    private void initKeyByType(List<DBTable.DBData> userEdge) {
        userEdge.stream().filter(now -> !nodes.containsKey(now.object.get("from").toString()))
                .forEach(now -> nodes.put(now.object.get("from").toString(), new EdgeNode()));
        userEdge.stream().filter(now -> !nodes.containsKey(now.object.get("to").toString()))
                .forEach(now -> nodes.put(now.object.get("to").toString(), new EdgeNode()));
    }

    private void loadEdge(List<DBTable.DBData> userEdge) {
        for (DBTable.DBData now : userEdge) {
            nodes.get(now.object.get("from").toString())
                    .addNext(new Pair<>(now.object.get("to").toString(), now.object.get("script").toString()));
            nodes.get(now.object.get("to").toString())
                    .addPre(new Pair<>(now.object.get("from").toString(), now.object.get("script").toString()));
        }
    }

    private boolean haveNext(String now, String aim) {
        if (now.equals(aim)) {
            return true;
        }
        EdgeNode node = nodes.get(now);
        for (Pair<String, String> next : node.getNext()) {
            if (haveNext(next.getKey(), aim)) {
                return true;
            }
        }
        return false;
    }

}
