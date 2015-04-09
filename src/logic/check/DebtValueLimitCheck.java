package logic.check;

import logic.LogicWithUIMessage;
import type.DebtType;
import data.DebtKeeper;

public class DebtValueLimitCheck extends AbstractCheck {
	
	int id; double value;
	
	public DebtValueLimitCheck() {
		super("debt value limit");
	}
	
	public void setValue(int id,double value){
		this.id=id;
		this.value=value;
	}

	@Override
	public boolean check() {
		DebtKeeper keeper=(DebtKeeper) data.getData("debt");
		DebtType debt=(DebtType) keeper.getItem(id+"");
		if (debt.getMaxRepay()<value){
			LogicWithUIMessage.setDebtRepayLimitError(value);
			LogicWithUIMessage.UIAction();
			return false;
		}
		return true;
	}

}
