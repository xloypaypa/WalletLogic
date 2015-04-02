package logic.event;

import logic.action.RemoveReasonAction;

public class RemoveReasonEvent extends AbstractEvent {
	
	String name;
	
	public RemoveReasonEvent() {
		super("remove reason");
		this.addAction(new RemoveReasonAction());
	}
	
	public void setName(String name){
		this.name=name;
	}

	@Override
	public void doEvent() {
		RemoveReasonAction ra=(RemoveReasonAction) this.getAction("remove reason");
		ra.setValue(name);
		
		this.runAction();
	}

}
