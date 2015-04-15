package logic.check;

import logic.LogicWithUIMessage;

public class TransferSameTypeCheck extends AbstractCheck {
	
	String from, to;
	
	public TransferSameTypeCheck() {
		super("transfer same type");
	}
	
	public void setValue(String from, String to){
		this.from = from;
		this.to = to;
	}

	@Override
	public boolean check() {
		if (from.equals(to)){
			LogicWithUIMessage.setErrorMessage("from type and aim type should not be same!");
			LogicWithUIMessage.UIAction();
			return false;
		}
		return true;
	}

}
