package logic;

import type.MoneyType;
import data.MoneyKeeper;

public class TypeLogic extends LogicWithUIMessage {
	
	public boolean addType(String type){
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		
		if (!checkName(type, keeper)) return false;
		
		MoneyType money=new MoneyType();
		money.setType(type); money.setValue(0);
		keeper.add(money);
		
		setOKMessage();
		
		return true;
	}
	
	public boolean renameType(String type, String name){
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		
		if (!checkName(name, keeper)) return false;
		
		MoneyType money=(MoneyType) keeper.getItem(type);
		money.setType(name);
		keeper.update(type, money);
		
		setOKMessage();
		
		return true;
	}
	
	public boolean removeType(String type){
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		keeper.delete(type);
		return true;
	}

	private boolean checkName(String name, MoneyKeeper keeper) {
		if (name.length()==0){
			setNeedInputError("type name");
			message.logicUIAction();
			return false;
		}else if (name.contains("[")){
			setLeftHalfBracketError();
			message.logicUIAction();
			return false;
		}else if (keeper.itemExist(name)){
			setExistentError("type");
			message.logicUIAction();
			return false;
		}
		return true;
	}
}
