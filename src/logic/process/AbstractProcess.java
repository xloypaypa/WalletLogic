package logic.process;

import data.DetailKeeper;
import type.DetailType;
import logic.LogicWithUIMessage;

public abstract class AbstractProcess extends LogicWithUIMessage {
	
	String processName;
	protected DetailType detail;
	
	public AbstractProcess(String processName) {
		this.processName=processName;
	}
	
	public boolean isThisProcess(String name) {
		if (this.processName.equals(name)) return true;
		else return false;
	}
	
	public void setDetail(DetailType detail) {
		this.detail=detail;
	}
	
	public void process(){
		this.backup();
		
		DetailKeeper keeper=new DetailKeeper();
		keeper.removeLastDetail();
	}
	
	public abstract void backup();

}
