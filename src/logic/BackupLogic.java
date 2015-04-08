package logic;

import java.util.Vector;

import data.DetailKeeper;
import type.DetailType;
import logic.process.AbstractProcess;

public class BackupLogic extends LogicWithUIMessage {
	
	Vector <AbstractProcess> allProcess=new Vector<>();
	
	public void addBackup(AbstractProcess backup){
		allProcess.addElement(backup);
	}
	
	public void backup(){
		DetailKeeper keeper=(DetailKeeper) data.getData("detail");
		DetailType last=keeper.getLastDetail();
		boolean flag=false;
		for (int i=0;i<allProcess.size();i++){
			if (allProcess.get(i).isThisProcess(last.getEvent())){
				allProcess.get(i).setDetail(last);
				allProcess.get(i).process();
				flag=true;
			}
		}
		if (!flag){
			setErrorMessage("Last detail can't be removed.");
			message.UIAction();
		}
	}

}
