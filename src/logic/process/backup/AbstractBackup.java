package logic.process.backup;

import data.DetailKeeper;
import logic.process.AbstractProcess;

public abstract class AbstractBackup extends AbstractProcess {

	public AbstractBackup(String processName) {
		super(processName);
	}

	@Override
	public void process() {
		this.backup();
		
		DetailKeeper keeper=(DetailKeeper) data.getData("detail");
		keeper.removeLastDetail();
	}

}
