package logic;

import java.util.Date;

import data.UserPublicData;
import logic.action.detail.WriteDetailAction;
import logic.action.money.ExpenditureAction;
import logic.action.money.IncomeAction;
import logic.action.money.TransferAction;
import logic.action.reason.ReasonExpenditureAction;
import logic.action.reason.ReasonIncomeAction;
import logic.action.reason.TreeReasonExpenditureAction;
import logic.action.reason.TreeReasonIncomeAction;
import logic.check.MoneyEnoughCheck;
import logic.check.TreeReasonBudgetCheck;
import logic.check.ValueSignCheck;
import logic.event.CheckThenActionEvent;

public class CostLogic extends LogicWithUIMessage {
	
	public void income(String type,double value,String reason){
		CheckThenActionEvent event=new CheckThenActionEvent("income");
		
		IncomeAction ia=new IncomeAction();
		ia.setValue(type, value);
		event.addAction(ia);
		
		ValueSignCheck vc=new ValueSignCheck();
		vc.setValue(value);
		event.addCheck(vc);
		
		if (UserPublicData.getUserReason(UserPublicData.getNowUser()).equals("tree")){
			TreeReasonIncomeAction tria=new TreeReasonIncomeAction();
			tria.setValue(reason, value);
			event.addAction(tria);
		}else{
			ReasonIncomeAction ria=new ReasonIncomeAction();
			ria.setValue(reason, value);
			event.addAction(ria);
		}
		
		WriteDetailAction detail=new WriteDetailAction();
		detail.setValue(new Date(), "income", type, value, reason);
		event.addAction(detail);
		
		event.doEvent();
	}
	
	public void expenditure(String type,double value,String reason){
		CheckThenActionEvent event=new CheckThenActionEvent("expenditure");
		
		ExpenditureAction ea=new ExpenditureAction();
		ea.setValue(type, value);
		event.addAction(ea);
		
		ValueSignCheck vc=new ValueSignCheck();
		vc.setValue(value);
		event.addCheck(vc);
		
		MoneyEnoughCheck mec=new MoneyEnoughCheck();
		mec.setValue(type, value);
		event.addCheck(mec);
		
		if (UserPublicData.getUserReason(UserPublicData.getNowUser()).equals("tree")){
			TreeReasonExpenditureAction trea=new TreeReasonExpenditureAction();
			trea.setValue(reason, value);
			
			TreeReasonBudgetCheck trbc=new TreeReasonBudgetCheck();
			trbc.setValue(reason, value);
			
			event.addAction(trea);
			event.addCheck(trbc);
		}else{
			ReasonExpenditureAction rea=new ReasonExpenditureAction();
			rea.setValue(reason, value);
			event.addAction(rea);
		}
		
		WriteDetailAction detail=new WriteDetailAction();
		detail.setValue(new Date(), "expenditure", type, value, reason);
		event.addAction(detail);
		
		event.doEvent();
	}
	
	public void transfer(String from,String to,double value){
		CheckThenActionEvent event=new CheckThenActionEvent("transfer");
		
		TransferAction ta=new TransferAction();
		ta.setValue(from, to, value);
		event.addAction(ta);
		
		ValueSignCheck vc=new ValueSignCheck();
		vc.setValue(value);
		event.addCheck(vc);
		
		MoneyEnoughCheck mec=new MoneyEnoughCheck();
		mec.setValue(from, value);
		event.addCheck(mec);
		
		WriteDetailAction detail=new WriteDetailAction();
		detail.setValue(new Date(), "transfer", to, value, "");
		detail.addExtra("from type", from);
		event.addAction(detail);
		
		event.doEvent();
	}
	
}
