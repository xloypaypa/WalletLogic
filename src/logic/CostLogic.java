package logic;

import logic.event.ExpenditureEvent;
import logic.event.IncomeEvent;
import logic.event.TransferEvent;

public class CostLogic extends LogicWithUIMessage {
	
	public void income(String type,double value){
		IncomeEvent event=new IncomeEvent();
		event.setValue(type, value);
		event.doEvent();
	}
	
	public void expenditure(String type,double value){
		ExpenditureEvent event=new ExpenditureEvent();
		event.setValue(type, value);
		event.doEvent();
	}
	
	public void transfer(String from,String to,double value){
		TransferEvent event=new TransferEvent();
		event.setValue(from, to, value);
		event.doEvent();
	}
	
}
