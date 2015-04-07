package logic.process.backup.debt;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import data.DebtKeeper;
import type.DebtType;
import logic.process.AbstractProcess;

public class RepayDebtBackup extends AbstractProcess {

	public RepayDebtBackup(String processName) {
		super(processName);
	}

	@Override
	public void backup() {
		String id=detail.getExtraMessage("past debt id");
		DebtType debt=new DebtType();
		debt.setCreditor(detail.getExtraMessage("past creditor"));
		
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			debt.setDeadline(sdf.parse(detail.getExtraMessage("past debt deadline")));
			debt.setLastUpdateTime(sdf.parse(detail.getExtraMessage("past debt update")));
			debt.setStartingTime(sdf.parse(detail.getExtraMessage("past debt starting")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		debt.setDebtType(detail.getExtraMessage("past debt type"));
		debt.setID(Integer.valueOf(detail.getExtraMessage("past debt id")));
		debt.setRate(debt.getExtraMessage("past debt rate type"), Double.valueOf(debt.getExtraMessage("debt past rate")));
		debt.setValue(Double.valueOf(detail.getExtraMessage("past debt value")));
		
		DebtKeeper keeper=(DebtKeeper) data.getData("debt");
		keeper.update(id, debt);
	}

}
