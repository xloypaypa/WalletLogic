package logic;

import java.util.Vector;

import logic.process.AbstractProcess;

public class ProcessLogic extends Logic {
	
	Vector <AbstractProcess> allProcess=new Vector<>();
	
	public void addProcess(AbstractProcess backup){
		allProcess.addElement(backup);
	}

}
