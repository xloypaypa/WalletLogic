package logic.action;

public abstract class RepayDebtAction extends AbstractAction {
	
	int id;
	String type;
	double value;
	
	public RepayDebtAction() {
		super("repay debt");
	}
	
	public void setValue(int id,double value,String type){
		this.id=id;
		this.value=value;
		this.type=type;
	}

}
