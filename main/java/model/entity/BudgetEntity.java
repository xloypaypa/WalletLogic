package model.entity;

import model.db.DBTable;

/**
 * Created by xlo on 15/12/20.
 * it's the budget entity
 */
public class BudgetEntity extends Entity {
    public BudgetEntity(DBTable.DBData data) {
        super(data);
    }

    public String getName() {
        return this.getObject().get("typename").toString();
    }

    public double getIncome() {
        return Double.parseDouble(this.getObject().get("income").toString());
    }

    public double getExpenditure() {
        return Double.parseDouble(this.getObject().get("expenditure").toString());
    }

    public double getNowIncome() {
        return Double.parseDouble(this.getObject().get("now income").toString());
    }

    public double getNowExpenditure() {
        return Double.parseDouble(this.getObject().get("now expenditure").toString());
    }

    public String setName(String typename) {
        return this.getObject().put("typename", typename).toString();
    }

    public double setIncome(double income) {
        return Double.parseDouble(this.getObject().put("income", income).toString());
    }

    public double setExpenditure(double expenditure) {
        return Double.parseDouble(this.getObject().put("expenditure", expenditure).toString());
    }

    public double setNoeIncome(double nowIncome) {
        return Double.parseDouble(this.getObject().put("now income", nowIncome).toString());
    }

    public double setNowExpenditure(double nowExpenditure) {
        return Double.parseDouble(this.getObject().put("now expenditure", nowExpenditure).toString());
    }
}
