package logic.check;

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
			setExistentError("name");
			message.UIAction();
			return false;
		}
		return true;
	}

}
