package logic.check;

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
			setErrorMessage("The value should be more than zero.");
			message.UIAction();
			return false;
		}
		else return true;
	}

}
