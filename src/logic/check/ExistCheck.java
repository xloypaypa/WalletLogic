package logic.check;

import logic.LogicWithUIMessage;
import data.IDDataKeeper;

public class ExistCheck extends AbstractCheck {
	
	String id;
	IDDataKeeper keeper;
	
	public ExistCheck() {
		super("exist");
	}
	
	public void setID(String id){
		this.id=id;
	}
	
	public void setKeeper(IDDataKeeper keeper){
		this.keeper=keeper;
	}

	@Override
	public boolean check() {
		if (keeper.itemExist(id)){
			LogicWithUIMessage.setExistentError("name");
			LogicWithUIMessage.UIAction();
			return false;
		}
		return true;
	}

}
