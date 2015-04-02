package logic.check;

import java.util.Date;

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
			setErrorMessage("This time before now time!");
			message.UIAction();
			return false;
		}
		return true;
	}

}
