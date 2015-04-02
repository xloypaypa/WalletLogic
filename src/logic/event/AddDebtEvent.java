package logic.event;

import java.util.Date;


public abstract class AddDebtEvent extends AbstractEvent {
	
	String creditor,rateType,moneyType;
	double value,rate;
	Date deadline;
	
	public AddDebtEvent() {
		super("add debt");
		loadCheck();
		loadAction();
	}
	
	public void setValue(String credior,double value,String moneyType,Date deadline,double rate,String rateType){
		this.creditor=credior;
		this.value=value;
		this.moneyType=moneyType;
		this.deadline=deadline;
		this.rate=rate;
		this.rateType=rateType;
	}
	
	protected abstract void loadAction();
	protected abstract void loadCheck();

}
