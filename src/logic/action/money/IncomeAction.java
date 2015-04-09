package logic.action.money;

import logic.LogicWithUIMessage;
import logic.action.AbstractAction;
import type.MoneyType;
import data.MoneyKeeper;

public class IncomeAction extends AbstractAction {
	
	String type; double value;
	
	public IncomeAction() {
		super("income");
	}
	
	public void setValue(String type,double value){
		this.type=type;
		this.value=value;
	}

	@Override
	public void action() {
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		MoneyType money=(MoneyType) keeper.getItem(type);
		money.setType(type); money.setValue(money.getValue()+value);
		keeper.update(type, money);
		LogicWithUIMessage.setOKMessage();
	}

}
