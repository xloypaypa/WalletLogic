package logic.check;

import java.util.Date;

import logic.LogicWithUIMessage;

public class AfterTimeChecker extends AbstractCheck {
	
	Date time,now;
	
	public AfterTimeChecker() {
		super("after time");
	}
	
	public void setValue(Date now,Date time){
		this.time=time;
		this.now=now;
	}

	@Override
	public boolean check() {
		if (time.before(now)){
			LogicWithUIMessage.setErrorMessage("This time before now time!");
			LogicWithUIMessage.UIAction();
			return false;
		}
		return true;
	}

}
