package model.db;

import org.bson.Document;

import java.util.Map;

/**
 * Created by xlo on 15-11-1.
 * it's the wallet collection
 */
public abstract class WalletCollection extends DBTable {
    @Override
    protected void updateUsing() {
        using.stream().filter(now -> !now.object.equals(now.past)).forEach(now
                -> collection.updateOne(new Document("_id", now.id),
                new Document("$set", now.object)));
    }

    @Override
    protected Map<String, Object> buildNewDocument() {
        return new Document();
    }

    @Override
    protected String getIdObjectKey() {
        return "_id";
    }
}
