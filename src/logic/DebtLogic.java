package logic;

import java.text.SimpleDateFormat;
import java.util.Date;

import logic.action.debt.AddBorrowingAction;
import logic.action.debt.AddDebtAction;
import logic.action.debt.AddLoanAction;
import logic.action.debt.RepayDebtAction;
import logic.action.detail.DetailSaveDebtAction;
import logic.action.detail.WriteDetailAction;
import logic.action.money.ExpenditureAction;
import logic.action.money.IncomeAction;
import logic.check.AfterTimeChecker;
import logic.check.DebtValueLimitCheck;
import logic.check.MoneyEnoughCheck;
import logic.check.ValueSignCheck;
import logic.event.CheckThenActionEvent;
import logic.event.FirstCheckThenDetailFinallyAction;

public class DebtLogic extends LogicWithUIMessage {
	
	public void addBorrowing(String creditor,double value,String moneyType,Date deadline,double rate,String rateType){
		int id=AddDebtAction.getNewID();
		CheckThenActionEvent event=new CheckThenActionEvent("add borrowing");
		AddDebtAction aba=new AddBorrowingAction();
		aba.setValue(id, creditor, value, deadline, rate, rateType);
		event.addAction(aba);
		
		IncomeAction ia=new IncomeAction();
		ia.setValue(moneyType, value);
		event.addAction(ia);
		
		ValueSignCheck vsc=new ValueSignCheck();
		vsc.setValue(value);
		event.addCheck(vsc);
		
		AfterTimeChecker atc=new AfterTimeChecker();
		atc.setValue(new Date(), deadline);
		event.addCheck(atc);
		
		DetailSaveDebtAction detail=new DetailSaveDebtAction();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		detail.setValue(new Date(), "add borrowing", moneyType, value, "");
		detail.addExtra("debt id", id+"");
		detail.addExtra("operator creditor", creditor);
		detail.addExtra("operator value", value+"");
		detail.addExtra("operator money type", moneyType);
		detail.addExtra("operator deadline", sdf.format(deadline));
		detail.addExtra("operator rate", rate+"");
		detail.addExtra("operator rate type", rateType);
		detail.addExtra("operator type", "borrowing");
		event.addAction(detail);
		
		event.doEvent();
	}
	
	public void repayBorrowing(int id,double value,String moneyType){
		FirstCheckThenDetailFinallyAction event=new FirstCheckThenDetailFinallyAction("repay borrowing");
		
		RepayDebtAction rba=new RepayDebtAction();
		rba.setValue(id, value, moneyType);
		event.addAction(rba);
		
		ExpenditureAction ea=new ExpenditureAction();
		ea.setValue(moneyType, value);
		event.addAction(ea);
		
		DebtValueLimitCheck dvlc=new DebtValueLimitCheck();
		dvlc.setValue(id, value);
		event.addCheck(dvlc);
		
		ValueSignCheck vsc=new ValueSignCheck();
		vsc.setValue(value);
		event.addCheck(vsc);
		
		MoneyEnoughCheck mec=new MoneyEnoughCheck();
		mec.setValue(moneyType, value);
		event.addCheck(mec);
		
		DetailSaveDebtAction detail=new DetailSaveDebtAction();
		detail.setValue(new Date(), "repay borrowing", moneyType, value, "");
		detail.saveDetail(id+"");
		detail.addExtra("operator id", id+"");
		detail.addExtra("operator value", value+"");
		detail.addExtra("operator money type", moneyType);
		event.setDetailAction(detail);
		
		event.doEvent();
	}
	
	public void addLoan(String creditor,double value,String moneyType,Date deadline,double rate,String rateType){
		int id=AddDebtAction.getNewID();
		CheckThenActionEvent event=new CheckThenActionEvent("add loan");
		AddLoanAction aba=new AddLoanAction();
		aba.setValue(id,creditor, value, deadline, rate, rateType);
		event.addAction(aba);
		
		ExpenditureAction ia=new ExpenditureAction();
		ia.setValue(moneyType, value);
		event.addAction(ia);
		
		ValueSignCheck vsc=new ValueSignCheck();
		vsc.setValue(value);
		event.addCheck(vsc);
		
		AfterTimeChecker atc=new AfterTimeChecker();
		atc.setValue(new Date(), deadline);
		event.addCheck(atc);
		
		MoneyEnoughCheck mec=new MoneyEnoughCheck();
		mec.setValue(moneyType, value);
		event.addCheck(mec);
		
		WriteDetailAction detail=new WriteDetailAction();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		detail.setValue(new Date(), "add loan", moneyType, value, "");
		detail.addExtra("debt id", id+"");
		detail.addExtra("operator creditor", creditor);
		detail.addExtra("operator value", value+"");
		detail.addExtra("operator money type", moneyType);
		detail.addExtra("operator deadline", sdf.format(deadline));
		detail.addExtra("operator rate", rate+"");
		detail.addExtra("operator rate type", rateType);
		detail.addExtra("operator type", "loan");
		event.addAction(detail);
		
		event.doEvent();
	}
	
	public void repayLoan(int id,double value,String moneyType){
		FirstCheckThenDetailFinallyAction event=new FirstCheckThenDetailFinallyAction("repay loan");
		
		RepayDebtAction rba=new RepayDebtAction();
		rba.setValue(id, value, moneyType);
		event.addAction(rba);
		
		IncomeAction ea=new IncomeAction();
		ea.setValue(moneyType, value);
		event.addAction(ea);
		
		DebtValueLimitCheck dvlc=new DebtValueLimitCheck();
		dvlc.setValue(id, value);
		event.addCheck(dvlc);
		
		ValueSignCheck vsc=new ValueSignCheck();
		vsc.setValue(value);
		event.addCheck(vsc);
		
		DetailSaveDebtAction detail=new DetailSaveDebtAction();
		detail.setValue(new Date(), "repay loan", moneyType, value, "");
		detail.saveDetail(id+"");
		detail.addExtra("operator id", id+"");
		detail.addExtra("operator value", value+"");
		detail.addExtra("operator money type", moneyType);
		event.setDetailAction(detail);
		
		event.doEvent();
	}

}
