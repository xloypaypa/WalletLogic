package logic.check;


public class NameCheck extends AbstractCheck {
	
	String value;
	
	public NameCheck() {
		super("name");
	}
	
	public void setName(String name){
		this.value=name;
	}

	@Override
	public boolean check() {
		if (value.length()==0){
			setNeedInputError("type name");
			message.UIAction();
			return false;
		}else if (value.contains("[")){
			setLeftHalfBracketError();
			message.UIAction();
			return false;
		}
		return true;
	}

}
