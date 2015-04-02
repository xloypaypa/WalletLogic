package logic.action;

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
		setOKMessage();
	}

}
