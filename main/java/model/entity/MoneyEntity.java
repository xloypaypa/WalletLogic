package model.entity;

import model.db.DBTable;

/**
 * Created by xlo on 15-12-20.
 * it's the money entity
 */
public class MoneyEntity extends Entity {

    public MoneyEntity(DBTable.DBData data) {
        super(data);
    }

    public String getName() {
        return this.getObject().get("typename").toString();
    }

    public double getValue() {
        return Double.parseDouble(this.getObject().get("value").toString());
    }

    public String setName(String typename) {
        return this.getObject().put("typename", typename).toString();
    }

    public double setValue(double value) {
        return Double.parseDouble(this.getObject().put("value", value).toString());
    }

}
