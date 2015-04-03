package logic;

import java.util.Date;

import logic.event.debt.AddBorrowingEvent;
import logic.event.debt.AddLoanEvent;
import logic.event.debt.RepayBorrowingEvent;
import logic.event.debt.RepayLoanEvent;

public class DebtLogic extends LogicWithUIMessage {
	
	public void addBorrowing(String credior,double value,String moneyType,Date deadline,double rate,String rateType){
		AddBorrowingEvent event=new AddBorrowingEvent();
		event.setValue(credior, value, moneyType, deadline, rate, rateType);
		event.doEvent();
	}
	
	public void repayBorrowing(int id,double value,String moneyType){
		RepayBorrowingEvent event=new RepayBorrowingEvent();
		event.setValue(id, value, moneyType);
		event.doEvent();
	}
	
	public void addLoan(String credior,double value,String moneyType,Date deadline,double rate,String rateType){
		AddLoanEvent event=new AddLoanEvent();
		event.setValue(credior, value, moneyType, deadline, rate, rateType);
		event.doEvent();
	}
	
	public void repayLoan(int id,double value,String moneyType){
		RepayLoanEvent event=new RepayLoanEvent();
		event.setValue(id, value, moneyType);
		event.doEvent();
	}

}
