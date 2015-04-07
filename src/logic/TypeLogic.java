package logic;

import java.util.Date;

import data.IDDataKeeper;
import logic.action.detail.DetailSaveMoneyAction;
import logic.action.detail.WriteDetailAction;
import logic.action.money.AddTypeAction;
import logic.action.money.RemoveTypeAction;
import logic.action.money.RenameTypeAction;
import logic.check.ExistCheck;
import logic.check.NameCheck;
import logic.event.CheckThenActionEvent;
import logic.event.FirstCheckThenDetailFinallyAction;

public class TypeLogic extends LogicWithUIMessage {
	
	public void addType(String type){
		CheckThenActionEvent event=new CheckThenActionEvent("add type");
		
		AddTypeAction ata=new AddTypeAction();
		ata.setName(type);
		event.addAction(ata);
		
		NameCheck nc=new NameCheck();
		nc.setName(type);
		event.addCheck(nc);
		
		ExistCheck ec=new ExistCheck();
		ec.setKeeper((IDDataKeeper) data.getData("money"));
		ec.setID(type);
		event.addCheck(ec);
		
		WriteDetailAction detail=new WriteDetailAction();
		detail.setValue(new Date(), "add type", type, 0, "");
		event.addAction(detail);
		
		event.doEvent();
	}
	
	public void renameType(String type, String name){
		CheckThenActionEvent event=new CheckThenActionEvent("rename type");
		
		RenameTypeAction rta=new RenameTypeAction();
		rta.setValue(type, name);
		event.addAction(rta);
		
		NameCheck nc=new NameCheck();
		nc.setName(name);
		event.addCheck(nc);
		
		ExistCheck ec=new ExistCheck();
		ec.setID(name);
		ec.setKeeper((IDDataKeeper) data.getData("money"));
		event.addCheck(ec);
		
		WriteDetailAction detail=new WriteDetailAction();
		detail.setValue(new Date(), "rename type", name, 0, "");
		detail.addExtra("past type name", type);
		event.addAction(detail);
		
		event.doEvent();
	}
	
	public void removeType(String type){
		FirstCheckThenDetailFinallyAction event=new FirstCheckThenDetailFinallyAction("remove type");
		
		RemoveTypeAction rta=new RemoveTypeAction();
		rta.setType(type);
		event.addAction(rta);
		
		DetailSaveMoneyAction detail=new DetailSaveMoneyAction();
		detail.setValue(new Date(), "remove type", type, 0, "");
		detail.saveMoney(type);
		event.setDetailAction(detail);
		
		event.doEvent();
		
	}
}
