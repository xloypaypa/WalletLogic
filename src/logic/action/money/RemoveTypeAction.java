package logic.action.money;

import logic.LogicWithUIMessage;
import logic.action.AbstractAction;
import data.MoneyKeeper;

public class RemoveTypeAction extends AbstractAction {
	
	String type;
	
	public RemoveTypeAction() {
		super("remove type");
	}
	
	public void setType(String type){
		this.type=type;
	}

	@Override
	public void action() {
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		keeper.delete(type);
		LogicWithUIMessage.setOKMessage();
	}

}
