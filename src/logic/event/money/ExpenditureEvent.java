package logic.event.money;

import data.UserPublicData;
import logic.action.money.ExpenditureAction;
import logic.action.reason.ReasonExpenditureAction;
import logic.action.reason.TreeReasonExpenditureAction;
import logic.check.MoneyEnoughCheck;
import logic.check.TreeReasonBudgetCheck;
import logic.check.ValueSignCheck;
import logic.event.AbstractEvent;

public class ExpenditureEvent extends AbstractEvent {
	
	String type,reason; double value;
	
	public ExpenditureEvent() {
		super("expenditure");
		this.addAction(new ExpenditureAction());
		
		if (UserPublicData.getUserReason(UserPublicData.getNowUser()).equals("tree")){
			this.addAction(new TreeReasonExpenditureAction());
			this.addCheck(new TreeReasonBudgetCheck());
		}else{
			this.addAction(new ReasonExpenditureAction());
		}
		this.addCheck(new ValueSignCheck());
		this.addCheck(new MoneyEnoughCheck());
	}
	
	public void setValue(String type,double value,String reason){
		this.type=type;
		this.value=value;
		this.reason=reason;
	}

	@Override
	public void doEvent() {
		ExpenditureAction ea=(ExpenditureAction) this.getAction("expenditure");
		ea.setValue(type, value);
		ReasonExpenditureAction ra=(ReasonExpenditureAction) this.getAction("reason expenditure");
		ra.setValue(reason, value);
		ValueSignCheck vc=(ValueSignCheck) this.getCheck("value sign");
		vc.setValue(value);
		MoneyEnoughCheck mc=(MoneyEnoughCheck) this.getCheck("money enough");
		mc.setValue(type, value);
		
		if (UserPublicData.getUserReason(UserPublicData.getNowUser()).equals("tree")){
			TreeReasonBudgetCheck trbc=(TreeReasonBudgetCheck) this.getCheck("tree reason budget");
			trbc.setValue(reason, value);
		}
		
		if (this.runCheck()){
			this.runAction();
		}
	}

}
