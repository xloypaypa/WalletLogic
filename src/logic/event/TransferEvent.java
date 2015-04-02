package logic.event;

import logic.action.TransferAction;
import logic.check.MoneyEnoughCheck;
import logic.check.ValueSignCheck;

public class TransferEvent extends AbstractEvent {
	
	String from,to; double value;
	
	public TransferEvent() {
		super("transfer");
		this.addAction(new TransferAction());
		this.addCheck(new ValueSignCheck());
		this.addCheck(new MoneyEnoughCheck());
	}
	
	public void setValue(String from,String to,double value){
		this.from=from;
		this.to=to;
		this.value=value;
	}

	@Override
	public void doEvent() {
		TransferAction ta=(TransferAction) this.getAction("transfer");
		ta.setValue(from, to, value);
		ValueSignCheck vc=(ValueSignCheck) this.getCheck("value sign");
		vc.setValue(value);
		MoneyEnoughCheck mc=(MoneyEnoughCheck) this.getCheck("money enough");
		mc.setValue(from, value);
		
		if (this.runCheck()){
			this.runAction();
		}
	}

}
