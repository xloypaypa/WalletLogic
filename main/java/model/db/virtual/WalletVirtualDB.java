package model.db.virtual;

import model.db.VirtualDB;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xlo on 2015/9/1.
 * it's the virtual db
 */
public class WalletVirtualDB implements VirtualDB {

    protected Map<String, WalletVirtualTable> collectionMap = new HashMap<>();

    @Override
    public synchronized WalletVirtualTable getTable(String collectionName) {
        if (!this.collectionMap.containsKey(collectionName)) {
            this.collectionMap.put(collectionName, new WalletVirtualTable());
        }
        return this.collectionMap.get(collectionName);
    }
}
