package logic.event;

import logic.action.RemoveTypeAction;

public class RemoveTypeEvent extends AbstractEvent {
	
	String type;
	
	public RemoveTypeEvent() {
		super("remove type");
		this.addAction(new RemoveTypeAction());
	}
	
	public void setType(String type){
		this.type=type;
	}

	@Override
	public void doEvent() {
		RemoveTypeAction ac=(RemoveTypeAction) this.getAction("remove type");
		ac.setType(type);
		this.runAction();
	}

}
