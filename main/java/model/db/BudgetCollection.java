package model.db;

import model.entity.BudgetEntity;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by xlo on 2015/11/5.
 * it's the connection for budget
 */
public class BudgetCollection extends WalletCollection {

    public void addBudgetType(String username, String typename, double income, double expenditure, double nowIncome, double nowExpenditure) {
        this.lockCollection();
        Document document = new Document();
        document.append("username", username)
                .append("typename", typename)
                .append("income", income)
                .append("expenditure", expenditure)
                .append("now income", nowIncome)
                .append("now expenditure", nowExpenditure);
        this.insert(document);
    }

    public BudgetEntity getBudget(String username, String typeName) {
        this.lockCollection();
        List<Map<String, Object>> iterator = this.collection.find(new Document().append("username", username).append("typename", typeName));
        if (iterator.size() == 0) {
            return null;
        }
        return new BudgetEntity(this.addDocumentToUsing(iterator.get(0)));
    }

    public BudgetEntity getBudgetData(String username, String typeName) {
        this.lockCollection();
        List<Map<String, Object>> iterator = this.collection.find(new Document().append("username", username).append("typename", typeName));
        if (iterator.size() == 0) {
            return null;
        }
        DBData dbData = this.getDocumentNotUsing(iterator.get(0));
        this.unlockCollection();
        return new BudgetEntity(dbData);
    }

    public void removeBudget(String username, String typename) {
        this.lockCollection();
        Document document = new Document();
        document.append("username", username)
                .append("typename", typename);
        this.remove(document);
    }

    public List<BudgetEntity> getBudgetListData(String username) {
        List<DBData> listData = this.getAllDataListData(new Document().append("username", username));
        return listData.stream().map(BudgetEntity::new).collect(Collectors.toCollection(LinkedList::new));
    }
}
