package control.logic.userDataBuilder;

import control.logic.manager.Manager;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonString;

/**
 * Created by xlo on 2015/12/18.
 * it's the builder
 */
public abstract class Builder {

    protected BsonDocument managerCallMessageBuilder(Class<? extends Manager> manager, String method, String... params) {
        BsonDocument bsonDocument = new BsonDocument();
        bsonDocument.append("manager", new BsonString(manager.getName()));
        bsonDocument.append("method", new BsonString(method));

        BsonArray bsonArray = new BsonArray();
        for (String param : params) {
            bsonArray.add(new BsonString(param));
        }
        bsonDocument.append("param", bsonArray);

        return bsonDocument;
    }

}
