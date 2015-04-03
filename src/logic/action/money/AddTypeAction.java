package logic.action.money;

import logic.action.AbstractAction;
import type.MoneyType;
import data.MoneyKeeper;

public class AddTypeAction extends AbstractAction {
	
	public AddTypeAction() {
		super("add type");
	}
	
	String value;
	
	public void setName(String name){
		this.value=name;
	}

	@Override
	public void action() {
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		MoneyType money=new MoneyType();
		money.setType(value); money.setValue(0);
		keeper.add(money);
		setOKMessage();
	}

}
