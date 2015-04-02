package logic.action;

import logic.LogicWithUIMessage;

public abstract class AbstractAction extends LogicWithUIMessage {
	
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
