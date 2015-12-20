package control.logic.manager;

import control.logic.userDataBuilder.UserRollBackBuilder;
import org.bson.BsonArray;
import org.bson.Document;

/**
 * Created by xlo on 2015/12/18.
 * it's the manager
 */
public abstract class Manager {

    protected String username;
    protected Document document;
    protected UserRollBackBuilder userRollBackBuilder;

    protected Manager(String username) {
        this.username = username;
        this.document = new Document();
        userRollBackBuilder = new UserRollBackBuilder();
    }

    public Document getDetailMessage() {
        return this.document;
    }

    public BsonArray getUserRollBackMessage() {
        return userRollBackBuilder.getRollBackArray();
    }
}
