package logic;

import java.util.Date;

import logic.action.debt.AddBorrowingAction;
import logic.action.debt.AddDebtAction;
import logic.action.debt.AddLoanAction;
import logic.action.debt.RepayDebtAction;
import logic.action.money.ExpenditureAction;
import logic.action.money.IncomeAction;
import logic.check.AfterTimeChecker;
import logic.check.DebtValueLimitCheck;
import logic.check.MoneyEnoughCheck;
import logic.check.ValueSignCheck;
import logic.event.CheckThenActionEvent;

public class DebtLogic extends LogicWithUIMessage {
	
	public void addBorrowing(String credior,double value,String moneyType,Date deadline,double rate,String rateType){
		CheckThenActionEvent event=new CheckThenActionEvent("add borrowing");
		AddDebtAction aba=new AddBorrowingAction();
		aba.setValue(credior, value, deadline, rate, rateType);
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
		
		event.doEvent();
	}
	
	public void repayBorrowing(int id,double value,String moneyType){
		CheckThenActionEvent event=new CheckThenActionEvent("repay borrowing");
		
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
		
		event.doEvent();
	}
	
	public void addLoan(String credior,double value,String moneyType,Date deadline,double rate,String rateType){
		CheckThenActionEvent event=new CheckThenActionEvent("add loan");
		AddLoanAction aba=new AddLoanAction();
		aba.setValue(credior, value, deadline, rate, rateType);
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
		
		event.doEvent();
	}
	
	public void repayLoan(int id,double value,String moneyType){
		CheckThenActionEvent event=new CheckThenActionEvent("repay loan");
		
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
		
		event.doEvent();
	}

}
