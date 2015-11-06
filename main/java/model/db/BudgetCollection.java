package model.db;

import org.bson.Document;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 2015/11/5.
 * it's the connection for budget
 */
public class BudgetCollection extends WalletCollection {

    public void addBudgetType(String username, String typename, double income, double expenditure) {
        this.lockCollection();
        Document document = new Document();
        document.append("username", username)
                .append("typename", typename)
                .append("income", income)
                .append("expenditure", expenditure);
        this.insert(document);
    }

    public DBData getBudget(String username, String typeName) {
        this.lockCollection();
        List<Map<String, Object>> iterator = this.collection.find(new Document().append("username", username).append("typename", typeName));
        if (iterator.size() == 0) {
            return null;
        }
        return this.addDocumentToUsing(iterator.get(0));
    }

    public DBData getBudgetData(String username, String typeName) {
        this.lockCollection();
        List<Map<String, Object>> iterator = this.collection.find(new Document().append("username", username).append("typename", typeName));
        if (iterator.size() == 0) {
            return null;
        }
        DBData dbData = this.getDocumentNotUsing(iterator.get(0));
        this.unlockCollection();
        return dbData;
    }

    public void removeBudget(String username, String typename) {
        this.lockCollection();
        Document document = new Document();
        document.append("username", username)
                .append("typename", typename);
        this.remove(document);
    }

    public List<DBData> getBudgetListData(String username) {
        this.lockCollection();
        List<Map<String, Object>> iterator = this.collection.find(new Document().append("username", username));
        List<DBData> ans = new LinkedList<>();
        for (Map<String, Object> now : iterator) {
            ans.add(this.getDocumentNotUsing(now));
        }
        this.unlockCollection();
        return ans;
    }
}