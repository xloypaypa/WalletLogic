package logic.event;

import logic.action.ExpenditureAction;
import logic.check.MoneyEnoughCheck;
import logic.check.ValueSignCheck;

public class ExpenditureEvent extends AbstractEvent {
	
	String type; double value;
	
	public ExpenditureEvent() {
		super("expenditure");
		this.addAction(new ExpenditureAction());
		this.addCheck(new ValueSignCheck());
		this.addCheck(new MoneyEnoughCheck());
	}
	
	public void setValue(String type,double value){
		this.type=type;
		this.value=value;
	}

	@Override
	public void doEvent() {
		ExpenditureAction ea=(ExpenditureAction) this.getAction("expenditure");
		ea.setValue(type, value);
		ValueSignCheck vc=(ValueSignCheck) this.getCheck("value sign");
		vc.setValue(value);
		MoneyEnoughCheck mc=(MoneyEnoughCheck) this.getCheck("money enough");
		mc.setValue(type, value);
		
		if (this.runCheck()){
			this.runAction();
		}
	}

}
