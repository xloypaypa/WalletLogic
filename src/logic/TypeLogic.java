package logic;

import data.IDDataKeeper;
import logic.action.money.AddTypeAction;
import logic.action.money.RemoveTypeAction;
import logic.action.money.RenameTypeAction;
import logic.check.ExistCheck;
import logic.check.NameCheck;
import logic.event.CheckThenActionEvent;

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
		
		event.doEvent();
	}
	
	public void removeType(String type){
		CheckThenActionEvent event=new CheckThenActionEvent("remove type");
		
		RemoveTypeAction rta=new RemoveTypeAction();
		rta.setType(type);
		event.addAction(rta);
		
		event.doEvent();
		
	}
}
