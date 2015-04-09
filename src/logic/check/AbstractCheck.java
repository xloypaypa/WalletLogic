package logic.check;

import logic.Logic;

public abstract class AbstractCheck extends Logic {
	
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
