package logic.action.debt;

import java.util.Date;
import java.util.Vector;
import logic.action.AbstractAction;
import type.DebtType;
import type.Type;
import data.DebtKeeper;
import data.TypeKeeper;

public abstract class AddDebtAction extends AbstractAction {
	
	int id;
	String creditor,rateType;
	double value,rate;
	Date deadline;
	
	public AddDebtAction() {
		super("add debt");
	}
	
	public void setValue(int id, String credior,double value,Date deadline,double rate,String rateType){
		this.id=id;
		this.creditor=credior;
		this.value=value;
		this.deadline=deadline;
		this.rate=rate;
		this.rateType=rateType;
	}

	public static int getNewID() {
		int max=-1;
		Vector <Type> ret=((TypeKeeper) data.getData("debt")).getAllItem();
		for (int i=0;i<ret.size();i++){
			DebtType now=(DebtType) ret.get(i);
			if (max<now.getID()) max=now.getID();
		}
		return max+1;
	}

	protected void setDebtMessage(DebtKeeper keeper, DebtType debt) {
		debt.setCreditor(creditor);
		debt.setDeadline(deadline);
		debt.setRate(rateType, rate);
		debt.setLastUpdateTime(new Date());
		debt.setStartingTime(new Date());
		debt.setValue(value);
		debt.setID(id);
	}

}
