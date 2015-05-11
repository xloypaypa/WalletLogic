package logic.check;


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
		return min<=max;
	}

}
