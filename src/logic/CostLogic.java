package logic;

import logic.event.money.ExpenditureEvent;
import logic.event.money.IncomeEvent;
import logic.event.money.TransferEvent;

public class CostLogic extends LogicWithUIMessage {
	
	public void income(String type,double value,String reason){
		IncomeEvent event=new IncomeEvent();
		event.setValue(type, value,reason);
		event.doEvent();
	}
	
	public void expenditure(String type,double value,String reason){
		ExpenditureEvent event=new ExpenditureEvent();
		event.setValue(type, value,reason);
		event.doEvent();
	}
	
	public void transfer(String from,String to,double value){
		TransferEvent event=new TransferEvent();
		event.setValue(from, to, value);
		event.doEvent();
	}
	
}
