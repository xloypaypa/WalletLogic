package logic.check;

import logic.LogicWithUIMessage;


public class NameCheck extends AbstractCheck {
	
	String value;
	
	public NameCheck() {
		super("name");
	}
	
	public void setName(String name){
		this.value=name;
	}

	@Override
	public boolean check() {
		if (value.length()==0){
			LogicWithUIMessage.setNeedInputError("type name");
			LogicWithUIMessage.UIAction();
			return false;
		}else if (value.contains("[")){
			LogicWithUIMessage.setLeftHalfBracketError();
			LogicWithUIMessage.UIAction();
			return false;
		}
		return true;
	}

}
