package model.entity;

import model.db.DBTable;

/**
 * Created by xlo on 15-12-20.
 * it's the money entity
 */
public class MoneyEntity extends Entity {

    protected MoneyEntity(DBTable.DBData data) {
        super(data);
    }

    public String getMoneyName() {
        return this.getObject().get("typename").toString();
    }

    public double getValue() {
        return Double.valueOf(this.getObject().get("value").toString());
    }

    public String setMoneyName(String typename) {
        return this.getObject().put("typename", typename).toString();
    }

    public double setMoneyValue(double value) {
        return Double.valueOf(this.getObject().put("value", value).toString());
    }

}
