package logic.event;

import logic.action.IncomeAction;
import logic.action.ReasonIncomeAction;
import logic.check.ValueSignCheck;

public class IncomeEvent extends AbstractEvent {
	
	String type,reason; double value;
	
	public IncomeEvent() {
		super("income");
		this.addAction(new IncomeAction());
		this.addAction(new ReasonIncomeAction());
		this.addCheck(new ValueSignCheck());
	}
	
	public void setValue(String type,double value,String reason){
		this.type=type;
		this.value=value;
		this.reason=reason;
	}

	@Override
	public void doEvent() {
		IncomeAction ia=(IncomeAction) this.getAction("income");
		ia.setValue(type, value);
		ReasonIncomeAction ra=(ReasonIncomeAction) this.getAction("reason income");
		ra.setValue(reason, value);
		ValueSignCheck vc=(ValueSignCheck) this.getCheck("value sign");
		vc.setValue(value);
		
		if (this.runCheck()){
			this.runAction();
		}
	}

}
