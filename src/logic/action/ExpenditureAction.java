package logic.action;

import type.MoneyType;
import data.MoneyKeeper;

public class ExpenditureAction extends AbstractAction {
	
	String type; double value;
	
	public ExpenditureAction() {
		super("expenditure");
	}
	
	public void setValue(String type,double value){
		this.type=type;
		this.value=value;
	}

	@Override
	public void action() {
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		MoneyType money=(MoneyType) keeper.getItem(type);
		money.setType(type); money.setValue(money.getValue()-value);
		keeper.update(type, money);
		setOKMessage();
	}

}
