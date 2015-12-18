package control.logic.userDataBuilder;

import control.logic.manager.EdgeManager;
import control.logic.manager.MoneyManager;
import org.bson.BsonArray;

/**
 * Created by xlo on 2015/12/18.
 * it's the user data builder
 */
public class UserRollBackBuilder extends Builder {

    private BsonArray rollBackArray;

    public UserRollBackBuilder() {
        rollBackArray = new BsonArray();
    }

    public BsonArray getRollBackArray() {
        return rollBackArray;
    }

    public void createMoney(String typename) {
        rollBackArray.add(this.managerCallMessageBuilder(MoneyManager.class, "removeMoney", typename));
    }

    public void renameMoney(String typename, String newName) {
        rollBackArray.add(this.managerCallMessageBuilder(MoneyManager.class, "renameMoney", newName, typename));
    }

    public void removeMoney(String typename, String value) {
        rollBackArray.add(this.managerCallMessageBuilder(MoneyManager.class, "createMoney", typename, value));
    }

    public void transferMoney(String from, String to, String value) {
        rollBackArray.add(this.managerCallMessageBuilder(MoneyManager.class, "transferMoney", to, from, value));
    }

    public void addEdge(String from, String to) {
        rollBackArray.add(this.managerCallMessageBuilder(EdgeManager.class, "removeEdge", from, to));
    }

    public void removeEdge(String from, String to, String script) {
        rollBackArray.add(this.managerCallMessageBuilder(EdgeManager.class, "addEdge", from, to, script));
    }

    public void updateEdge(String from, String to, String script) {
        rollBackArray.add(this.managerCallMessageBuilder(EdgeManager.class, "updateEdge", from, to, script));
    }
}
