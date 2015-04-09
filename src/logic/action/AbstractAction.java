package logic.action;

import logic.Logic;

public abstract class AbstractAction extends Logic {
	
	String actionName;
	
	public AbstractAction(String actionName) {
		this.actionName=actionName;
	}
	
	public boolean isThisAction(String name){
		if (name.equals(actionName)) return true;
		else return false;
	}
	
	public abstract void action();
}
