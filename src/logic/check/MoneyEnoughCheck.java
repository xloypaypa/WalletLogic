package logic.check;

import type.MoneyType;
import data.MoneyKeeper;

public class MoneyEnoughCheck extends AbstractCheck {
	
	String type; double value;
	
	public MoneyEnoughCheck() {
		super("money enough");
	}
	
	public void setValue(String type,double value){
		this.type=type;
		this.value=value;
	}

	@Override
	public boolean check() {
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		MoneyType money=(MoneyType) keeper.getItem(type);
		if (money.getValue()<value){
			setErrorMessage("Don't have enough money.");
			message.UIAction();
			return false;
		}
		return true;
	}

}
