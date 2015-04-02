package logic;

import logic.event.AddTypeEvent;
import logic.event.RemoveTypeEvent;
import logic.event.RenameTypeEvent;

public class TypeLogic extends LogicWithUIMessage {
	
	public void addType(String type){
		AddTypeEvent event= new AddTypeEvent();
		event.setType(type);
		event.doEvent();
	}
	
	public void renameType(String type, String name){
		RenameTypeEvent event=new RenameTypeEvent();
		event.setValue(type, name);
		event.doEvent();
	}
	
	public void removeType(String type){
		RemoveTypeEvent event=new RemoveTypeEvent();
		event.setType(type);
		event.doEvent();
	}
}
