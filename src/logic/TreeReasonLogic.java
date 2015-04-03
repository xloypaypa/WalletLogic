package logic;

import data.IDDataKeeper;
import logic.action.reason.AddTreeReasonAction;
import logic.action.reason.RenameTreeReasonAction;
import logic.check.ExistCheck;
import logic.check.NameCheck;
import logic.check.TreeReasonLoopCheck;
import logic.event.CheckThenActionEvent;

public class TreeReasonLogic extends ReasonLogic {
	
	public void addReason(String name,String father,double min,double max,int rank){
		CheckThenActionEvent event=new CheckThenActionEvent("add reason");
		
		AddTreeReasonAction ara=new AddTreeReasonAction();
		ara.setValue(name);
		ara.setValue(father, min, max, rank);
		
		NameCheck nc=new NameCheck();
		nc.setName(name);
		
		ExistCheck ec=new ExistCheck();
		ec.setKeeper((IDDataKeeper) data.getData("reason"));
		ec.setID(name);
		
		event.addAction(ara);
		event.addCheck(ec);
		event.addCheck(nc);
		
		event.doEvent();
	}
	
	public void renameReason(String past,String name,String father,double min,double max,int rank){
		CheckThenActionEvent event=new CheckThenActionEvent("rename reason");
		
		RenameTreeReasonAction rtra=new RenameTreeReasonAction();
		rtra.setValue(past, name);
		rtra.setValue(father, min, max, rank);
		
		NameCheck nc=new NameCheck();
		nc.setName(name);
		
		ExistCheck ec=new ExistCheck();
		ec.setKeeper((IDDataKeeper) data.getData("reason"));
		ec.setID(name);
		
		TreeReasonLoopCheck trlc=new TreeReasonLoopCheck();
		trlc.setValue(past, father);
		
		event.addAction(rtra);
		event.addCheck(ec);
		event.addCheck(nc);
		event.addCheck(trlc);
		
		event.doEvent();
	}

}
