package logic.event.money;

import data.UserPublicData;
import logic.action.money.IncomeAction;
import logic.action.reason.ReasonIncomeAction;
import logic.action.reason.TreeReasonIncomeAction;
import logic.check.ValueSignCheck;
import logic.event.AbstractEvent;

public class IncomeEvent extends AbstractEvent {
	
	String type,reason; double value;
	
	public IncomeEvent() {
		super("income");
		this.addAction(new IncomeAction());
		if (UserPublicData.getUserReason(UserPublicData.getNowUser()).equals("tree")){
			this.addAction(new TreeReasonIncomeAction());
		}else{
			this.addAction(new ReasonIncomeAction());
		}
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
