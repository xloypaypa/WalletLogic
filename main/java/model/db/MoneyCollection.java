package model.db;

import model.entity.MoneyEntity;
import org.bson.Document;

import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 2015/11/4.
 * it's the collection for saving user money
 */
public class MoneyCollection extends WalletCollection {

    public void createMoney(String username, String typeName, double value) {
        this.lockCollection();
        Document document = new Document();
        document.append("username", username)
                .append("typename", typeName)
                .append("value", value);
        this.insert(document);
    }

    public MoneyEntity getMoney(String username, String typeName) {
        this.lockCollection();
        List<Map<String, Object>> iterator = this.collection.find(new Document().append("username", username).append("typename", typeName));
        if (iterator.size() == 0) {
            return null;
        }
        return new MoneyEntity(this.addDocumentToUsing(iterator.get(0)));
    }

    public MoneyEntity getMoneyData(String username, String typeName) {
        this.lockCollection();
        List<Map<String, Object>> iterator = this.collection.find(new Document().append("username", username).append("typename", typeName));
        if (iterator.size() == 0) {
            return null;
        }
        DBData dbData = this.getDocumentNotUsing(iterator.get(0));
        this.unlockCollection();
        return new MoneyEntity(dbData);
    }

    public void removeMoney(String username, String typename) {
        this.lockCollection();
        Document document = new Document();
        document.append("username", username)
                .append("typename", typename);
        this.remove(document);
    }

    public List<DBData> getMoneyListData(String username) {
        return this.getAllDataListData(new Document().append("username", username));
    }

}
