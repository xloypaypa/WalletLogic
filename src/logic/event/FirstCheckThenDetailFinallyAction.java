package logic.event;

import logic.action.detail.WriteDetailAction;

public class FirstCheckThenDetailFinallyAction extends CheckThenActionEvent {
	
	WriteDetailAction detailAction;

	public FirstCheckThenDetailFinallyAction(String eventName) {
		super(eventName);
	}
	
	public void setDetailAction(WriteDetailAction detailAction){
		this.detailAction=detailAction;
	}
	
	@Override
	public void doEvent() {
		if (this.runCheck()){
			detailAction.action();
			this.runAction();
		}
	}

}
