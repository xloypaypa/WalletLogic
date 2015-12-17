package control.logic;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xlo on 2015/12/17.
 * it's the edge node
 */
public class EdgeNode {

    private List<Pair<String, String>> next, pre;

    public EdgeNode() {
        this.next = new ArrayList<>();
        this.pre = new ArrayList<>();
    }

    public void addNext(Pair<String, String> edge) {
        next.add(edge);
    }

    public void addPre(Pair<String, String> edge) {
        pre.add(edge);
    }

    public List<Pair<String, String>> getNext() {
        return next;
    }

    public List<Pair<String, String>> getPre() {
        return pre;
    }
}
