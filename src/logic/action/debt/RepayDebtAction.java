package logic.action.debt;

import type.DebtType;
import data.DebtKeeper;
import logic.LogicWithUIMessage;
import logic.action.AbstractAction;

public class RepayDebtAction extends AbstractAction {
	
	protected int id;
	protected double value;
	
	public RepayDebtAction() {
		super("repay debt");
	}
	
	public void setValue(int id,double value){
		this.id=id;
		this.value=value;
	}
	
	@Override
	public void action() {
		DebtKeeper keeper=(DebtKeeper) data.getData("debt");
		DebtType debt=(DebtType) keeper.getItem(id+"");
		debt.repayment(value);
		if (debt.getMaxRepay()>1e-3) keeper.update(id+"", debt);
		else keeper.delete(id+"");
		LogicWithUIMessage.setOKMessage();
	}

}
