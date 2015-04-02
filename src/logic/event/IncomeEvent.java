package logic.event;

import logic.action.IncomeAction;
import logic.check.ValueSignCheck;

public class IncomeEvent extends AbstractEvent {
	
	String type; double value;
	
	public IncomeEvent() {
		super("income");
		this.addAction(new IncomeAction());
		this.addCheck(new ValueSignCheck());
	}
	
	public void setValue(String type,double value){
		this.type=type;
		this.value=value;
	}

	@Override
	public void doEvent() {
		IncomeAction ia=(IncomeAction) this.getAction("income");
		ia.setValue(type, value);
		ValueSignCheck vc=(ValueSignCheck) this.getCheck("value sign");
		vc.setValue(value);
		
		if (this.runCheck()){
			this.runAction();
		}
	}

}
