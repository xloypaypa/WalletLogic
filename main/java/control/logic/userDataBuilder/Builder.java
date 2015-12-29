package control.logic.userDataBuilder;

import control.logic.manager.Manager;
import org.bson.BsonString;
import org.bson.Document;

import java.util.ArrayList;

/**
 * Created by xlo on 2015/12/18.
 * it's the builder
 */
public abstract class Builder {

    protected Document managerCallMessageBuilder(Class<? extends Manager> manager, String method, String... params) {
        Document bsonDocument = new Document();
        bsonDocument.append("manager", new Document("value", manager.getName()));
        bsonDocument.append("method", new Document("value", method));

        ArrayList<Document> bsonArray = new ArrayList<>();
        for (String param : params) {
            bsonArray.add(new Document("value", param));
        }
        bsonDocument.append("param", bsonArray);

        return bsonDocument;
    }

}
