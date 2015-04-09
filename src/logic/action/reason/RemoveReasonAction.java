package logic.action.reason;

import logic.LogicWithUIMessage;
import logic.action.AbstractAction;
import data.ReasonKeeper;

public class RemoveReasonAction extends AbstractAction {
	
	String name;
	
	public RemoveReasonAction() {
		super("remove reason");
	}
	
	public void setValue(String name){
		this.name=name;
	}

	@Override
	public void action() {
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		keeper.delete(name);
		LogicWithUIMessage.setOKMessage();
	}

}
