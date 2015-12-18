package model.db.mongo;

import com.mongodb.client.MongoDatabase;
import model.db.VirtualDB;
import model.db.VirtualTable;

/**
 * Created by xlo on 2015/9/14.
 * it's the db of mongo
 */
public class WalletMongoDB implements VirtualDB {
    MongoDatabase mongoDatabase;

    protected WalletMongoDB(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    @Override
    public VirtualTable getTable(String name) {
        return new WalletMongoTable(mongoDatabase.getCollection(name));
    }
}
