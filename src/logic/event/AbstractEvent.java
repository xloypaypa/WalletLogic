package logic.event;

import java.util.Vector;

import logic.Logic;
import logic.action.AbstractAction;
import logic.check.AbstractCheck;

public abstract class AbstractEvent extends Logic {
	
	String eventName;
	
	Vector<AbstractAction> actor=new Vector<>();
	Vector<AbstractCheck> check=new Vector<>();
	
	public AbstractEvent(String eventName) {
		this.eventName=eventName;
	}
	
	public void addAction(AbstractAction actor){
		this.actor.addElement(actor);
	}
	
	public void addCheck(AbstractCheck check){
		this.check.addElement(check);
	}
	
	public void cleanActor(){
		this.actor.removeAllElements();
	}
	
	public void cleanCheck(){
		this.check.removeAllElements();
	}
	
	public AbstractAction getAction(String name){
		for (int i=0;i<actor.size();i++){
			if (actor.get(i).isThisAction(name)) return actor.get(i);
		}
		return null;
	}
	
	public AbstractCheck getCheck(String name){
		for (int i=0;i<check.size();i++){
			if (check.get(i).isThisCheck(name)) return check.get(i);
		}
		return null;
	}
	
	public boolean runCheck(){
		for (int i=0;i<check.size();i++){
			if (!check.get(i).check()) return false;
		}
		return true;
	}
	
	public void runAction(){
		for (int i=0;i<actor.size();i++){
			actor.get(i).action();
		}
	}
	
	public boolean isThisEvent(String name){
		if (name.equals(eventName)) return true;
		else return false;
	}
	
	public abstract void doEvent();
}
