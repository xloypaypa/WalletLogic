package logic.check;

import logic.LogicWithUIMessage;


public class TreeReasonMinMaxCheck extends AbstractCheck {
	
	double min,max;
	
	public TreeReasonMinMaxCheck() {
		super("tree reason min max");
	}
	
	public void setValue(double min, double max) {
		this.min=min;
		this.max=max;
	}

	@Override
	public boolean check() {
		if (min>max) {
			LogicWithUIMessage.setErrorMessage("Min expenditure should be lower than the max.");
			LogicWithUIMessage.UIAction();
			return false;
		}
		return true;
	}

}
