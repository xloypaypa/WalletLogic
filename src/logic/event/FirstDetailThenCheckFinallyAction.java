package logic.event;

import logic.action.detail.WriteDetailAction;

public class FirstDetailThenCheckFinallyAction extends CheckThenActionEvent {

	public FirstDetailThenCheckFinallyAction(String eventName) {
		super(eventName);
	}
	
	WriteDetailAction detailAction;
	
	public void setDetailAction(WriteDetailAction detailAction){
		this.detailAction=detailAction;
	}
	
	@Override
	public void doEvent() {
		this.detailAction.action();
		super.doEvent();
	}

}
