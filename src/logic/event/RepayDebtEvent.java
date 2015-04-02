package logic.event;

public abstract class RepayDebtEvent extends AbstractEvent {
	
	int id; double value; String moneyType;
	
	public RepayDebtEvent() {
		super("repay debt");
		loadCheck();
		loadAction();
	}
	
	public void setValue(int id,double value,String moneyType){
		this.id=id;
		this.value=value;
		this.moneyType=moneyType;
	}
	
	protected abstract void loadAction();
	protected abstract void loadCheck();

}
