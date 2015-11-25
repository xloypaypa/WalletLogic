package model.db;

import org.bson.Document;

import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 15-11-1.
 * it's the collection for username and password
 */
public class UserCollection extends WalletCollection {

    public void addUser(String username, String password) {
        this.lockCollection();
        Document document = new Document();
        document.append("username", username)
                .append("password", password);
        this.insert(document);
    }

    public DBData getUser(String username) {
        this.lockCollection();
        List<Map<String, Object>> iterator = this.collection.find(new Document().append("username", username));
        if (iterator.size() == 0) {
            return null;
        }
        return this.addDocumentToUsing(iterator.get(0));
    }

    public DBData getUserData(String username) {
        this.lockCollection();
        List<Map<String, Object>> iterator = this.collection.find(new Document().append("username", username));
        this.unlockCollection();
        if (iterator.size() == 0) {
            return null;
        }
        return this.getDocumentNotUsing(iterator.get(0));
    }

    public void removeUserData(String username) {
        this.lockCollection();
        List<Map<String, Object>> iterator = this.collection.find(new Document().append("username", username));
        if (iterator.size() != 0) {
            this.collection.deleteOne(iterator.get(0));
        }
    }

}
