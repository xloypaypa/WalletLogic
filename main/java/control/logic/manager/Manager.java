package control.logic.manager;

import org.bson.Document;

/**
 * Created by xlo on 2015/12/18.
 * it's the manager
 */
public abstract class Manager {

    protected String username;
    protected Document document;

    protected Manager(String username) {
        this.username = username;
        this.document = new Document();
    }

    public Document getUserRollBackMessage() {
        return this.document;
    }
}
