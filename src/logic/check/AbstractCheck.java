package logic.check;

import logic.LogicWithUIMessage;

public abstract class AbstractCheck extends LogicWithUIMessage {
	
	String checkName;
	
	public AbstractCheck(String checkName) {
		this.checkName=checkName;
	}
	
	public boolean isThisCheck(String name){
		if (name.equals(this.checkName)) return true;
		else return false;
	}
	
	public abstract boolean check();
	
}
