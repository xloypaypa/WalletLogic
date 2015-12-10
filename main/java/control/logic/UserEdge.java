package control.logic;

import javafx.util.Pair;
import model.db.BudgetEdgeCollection;
import model.db.DBTable;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by xlo on 2015/12/9.
 * it's the user edge
 */
public class UserEdge {

    private Map<String, Node> nodes;

    public UserEdge(String username) {
        BudgetEdgeCollection edgeCollection = new BudgetEdgeCollection();
        List<DBTable.DBData> userEdge = edgeCollection.findEdgeList(new Document("username", username));
        this.nodes = new HashMap<>();
        userEdge.stream().filter(now -> !nodes.containsKey(now.object.get("from").toString()))
                .forEach(now -> nodes.put(now.object.get("from").toString(), new Node()));
        userEdge.stream().filter(now -> !nodes.containsKey(now.object.get("to").toString()))
                .forEach(now -> nodes.put(now.object.get("to").toString(), new Node()));
        for (DBTable.DBData now : userEdge) {
            nodes.get(now.object.get("from").toString())
                    .next.add(new Pair<>(now.object.get("to").toString(), now.object.get("script").toString()));
            nodes.get(now.object.get("to").toString())
                    .pre.add(new Pair<>(now.object.get("from").toString(), now.object.get("script").toString()));
        }
    }

    public boolean checkCouldAddEdge(String from, String to) {
        return !nodes.containsKey(from) || !nodes.containsKey(to) || !haveNext(to, from);
    }

    public List<Pair<String, String>> getFather(String typename) {
        return new ArrayList<>(nodes.get(typename).next);
    }

    private boolean haveNext(String now, String aim) {
        if (now.equals(aim)) {
            return true;
        }
        Node node = nodes.get(now);
        for (Pair<String, String> next : node.next) {
            if (haveNext(next.getKey(), aim)) {
                return true;
            }
        }
        return false;
    }

    private class Node {
        private List<Pair<String, String>> next = new ArrayList<>(), pre = new ArrayList<>();

    }

}
