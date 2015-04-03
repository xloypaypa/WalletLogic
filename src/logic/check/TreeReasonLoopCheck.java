package logic.check;

import data.ReasonKeeper;
import type.ReasonTreeNodeType;

public class TreeReasonLoopCheck extends AbstractCheck {
	
	String from,to;
	
	public TreeReasonLoopCheck() {
		super("tree reason loop");
	}
	
	public void setValue(String from,String to){
		this.from=from;
		this.to=to;
	}

	@Override
	public boolean check() {
		if (to.equals("root")) return true;
		
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		
		ReasonTreeNodeType now=(ReasonTreeNodeType) keeper.getItem(from);
		if (now.getFather().equals("root")) return true;
		
		now=(ReasonTreeNodeType) keeper.getItem(now.getFather());
		while (true){
			if (now.getName().equals(from)) return false;
			else if (now.getFather().equals("root")) break;
			now=(ReasonTreeNodeType) keeper.getItem(now.getFather());
		}
		return true;
	}

}
