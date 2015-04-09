package logic.check;

import logic.LogicWithUIMessage;

public class ValueSignCheck extends AbstractCheck {
	
	double value;
	
	public ValueSignCheck() {
		super("value sign");
	}
	
	public void setValue(double value){
		this.value=value;
	}

	@Override
	public boolean check() {
		if (value<0){
			LogicWithUIMessage.setErrorMessage("The value should be more than zero.");
			LogicWithUIMessage.UIAction();
			return false;
		}
		else return true;
	}

}
