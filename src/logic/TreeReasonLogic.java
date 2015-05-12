package logic;

import java.util.Date;

import data.IDDataKeeper;
import logic.action.detail.DetailSaveTreeReasonAction;
import logic.action.detail.WriteDetailAction;
import logic.action.reason.AddTreeReasonAction;
import logic.action.reason.RemoveTreeReasonAction;
import logic.action.reason.RenameTreeReasonAction;
import logic.check.ExistCheck;
import logic.check.NameCheck;
import logic.check.TreeReasonLoopCheck;
import logic.check.TreeReasonMinMaxCheck;
import logic.check.ValueSignCheck;
import logic.event.CheckThenActionEvent;
import logic.event.FirstCheckThenDetailFinallyAction;

public class TreeReasonLogic extends ReasonLogic {
	
	public void addReason(String name,String father,double min,double max,int rank){
		CheckThenActionEvent event=new CheckThenActionEvent("add reason");
		
		AddTreeReasonAction ara=new AddTreeReasonAction();
		ara.setValue(name);
		ara.setValue(father, min, max, rank);
		
		NameCheck nc=new NameCheck();
		nc.setName(name);
		
		TreeReasonMinMaxCheck trmmc=new TreeReasonMinMaxCheck();
		trmmc.setValue(min, max);
		
		ValueSignCheck minVsc, maxVsc;
		minVsc=new ValueSignCheck();
		minVsc.setValue(min);
		maxVsc=new ValueSignCheck();
		maxVsc.setValue(max);
		
		ExistCheck ec=new ExistCheck();
		ec.setKeeper((IDDataKeeper) data.getData("reason"));
		ec.setID(name);
		
		event.addAction(ara);
		event.addCheck(ec);
		event.addCheck(nc);
		event.addCheck(maxVsc);
		event.addCheck(minVsc);
		event.addCheck(trmmc);
		
		WriteDetailAction detail=new WriteDetailAction();
		detail.setValue(new Date(), "add tree reason", "", 0, name);
		detail.addExtra("operator name", name);
		detail.addExtra("operator father", father);
		detail.addExtra("operator min", min+"");
		detail.addExtra("operator max", max+"");
		detail.addExtra("operator rank", rank+"");
		event.addAction(detail);
		
		event.doEvent();
	}
	
	public void renameReason(String past,String name,String father,double min,double max,int rank){
		FirstCheckThenDetailFinallyAction event=new FirstCheckThenDetailFinallyAction("rename reason");
		
		RenameTreeReasonAction rtra=new RenameTreeReasonAction();
		rtra.setValue(past, name);
		rtra.setValue(father, min, max, rank);
		
		NameCheck nc=new NameCheck();
		nc.setName(name);
		
		if (!past.equals(name)){
			ExistCheck ec=new ExistCheck();
			ec.setKeeper((IDDataKeeper) data.getData("reason"));
			ec.setID(name);
			event.addCheck(ec);
		}
		
		TreeReasonLoopCheck trlc=new TreeReasonLoopCheck();
		trlc.setValue(past, father);
		
		ValueSignCheck minVsc, maxVsc;
		minVsc=new ValueSignCheck();
		minVsc.setValue(min);
		maxVsc=new ValueSignCheck();
		maxVsc.setValue(max);
		
		event.addCheck(maxVsc);
		event.addCheck(minVsc);
		event.addAction(rtra);
		event.addCheck(nc);
		event.addCheck(trlc);
		
		DetailSaveTreeReasonAction detail=new DetailSaveTreeReasonAction();
		detail.setValue(new Date(), "rename tree reason", "", 0, name);
		detail.saveReason(past);
		detail.addExtra("operator past", past);
		detail.addExtra("operator name", name);
		detail.addExtra("operator father", father);
		detail.addExtra("operator min", min+"");
		detail.addExtra("operator max", max+"");
		detail.addExtra("operator rank", rank+"");
		event.setDetailAction(detail);
		
		event.doEvent();
	}
	
	@Override
	public void removeReason(String name) {
		FirstCheckThenDetailFinallyAction event=new FirstCheckThenDetailFinallyAction("remove reason");
		
		RemoveTreeReasonAction rra=new RemoveTreeReasonAction();
		rra.setValue(name);
		
		event.addAction(rra);
		
		DetailSaveTreeReasonAction detail=new DetailSaveTreeReasonAction();
		detail.setValue(new Date(), "remove tree reason", "", 0, name);
		detail.saveReason(name);
		event.setDetailAction(detail);
		
		event.doEvent();
	}

}
