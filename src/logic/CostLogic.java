package logic;

import type.MoneyType;
import data.MoneyKeeper;

public class CostLogic extends LogicWithUIMessage {
	
	public boolean income(String type,double value){
		if (!checkValue(value)) return false;
		
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		MoneyType money=(MoneyType) keeper.getItem(type);
		money.setValue(money.getValue()+value);
		keeper.update(type, money);
		
		setOKMessage();
		return true;
	}
	
	public boolean expenditure(String type,double value){
		if (!checkValue(value)) return false;
		
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		MoneyType money=(MoneyType) keeper.getItem(type);
		
		if (!checkEnoughMoney(value, money)) return false;
		
		money.setValue(money.getValue()-value);
		keeper.update(type, money);
		
		setOKMessage();
		return true;
	}
	
	public boolean transfer(String from,String to,double value){
		if (!checkValue(value)) return false;
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		MoneyType m1=(MoneyType) keeper.getItem(from);
		MoneyType m2=(MoneyType) keeper.getItem(to);
		
		if (!checkEnoughMoney(value, m1)) return false;
		
		m1.setValue(m1.getValue()-value);
		m2.setValue(m2.getValue()+value);
		keeper.update(from, m1);
		keeper.update(to, m2);
		
		setOKMessage();
		return true;
	}

	private boolean checkEnoughMoney(double value, MoneyType m1) {
		if (m1.getValue()<value){
			setErrorMessage("Don't have enough money.");
			message.logicUIAction();
			return false;
		}
		return true;
	}

	private boolean checkValue(double value) {
		if (value<0){
			setErrorMessage("The value should be more than zero.");
			message.logicUIAction();
			return false;
		}
		return true;
	}
	
}
