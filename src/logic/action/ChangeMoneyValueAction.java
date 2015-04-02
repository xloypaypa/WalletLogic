package logic.action;

import type.MoneyType;
import data.MoneyKeeper;

public class ChangeMoneyValueAction extends AbstractAction {
	
	public ChangeMoneyValueAction() {
		super("change money value");
	}
	
	String type;
	double value;
	
	public void setType(String type){
		this.type=type;
	}
	
	public void setValue(double value){
		this.value=value;
	}

	@Override
	public void action() {
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		MoneyType money=(MoneyType) keeper.getItem(type);
		money.setValue(money.getValue()+value);
		keeper.update(type, money);
	}

}
