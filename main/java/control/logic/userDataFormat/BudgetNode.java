package control.logic.userDataFormat;

/**
 * Created by xlo on 15/12/10.
 * it's the budget node
 */
public class BudgetNode {

    private String name;
    private double income, expenditure, nowIncome, nowExpenditure;

    public BudgetNode(String name, double income, double expenditure, double nowIncome, double nowExpenditure) {
        this.name = name;
        this.income = income;
        this.expenditure = expenditure;
        this.nowIncome = nowIncome;
        this.nowExpenditure = nowExpenditure;
    }

    public BudgetNode(BudgetNode budgetNode) {
        this.name = budgetNode.name;
        this.income = budgetNode.income;
        this.expenditure = budgetNode.expenditure;
        this.nowIncome = budgetNode.nowIncome;
        this.nowExpenditure = budgetNode.nowExpenditure;
    }

    public void setNowExpenditure(double nowExpenditure) {
        this.nowExpenditure = nowExpenditure;
    }

    public void setNowIncome(double nowIncome) {
        this.nowIncome = nowIncome;
    }

    public double getExpenditure() {
        return expenditure;
    }

    public double getIncome() {
        return income;
    }

    public double getNowExpenditure() {
        return nowExpenditure;
    }

    public double getNowIncome() {
        return nowIncome;
    }

    public String getName() {
        return name;
    }
}
