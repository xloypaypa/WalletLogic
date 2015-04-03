package logic.event;

public class CheckThenActionEvent extends AbstractEvent {

	public CheckThenActionEvent(String eventName) {
		super(eventName);
	}

	@Override
	public void doEvent() {
		if (this.runCheck()){
			this.runAction();
		}
	}

}
