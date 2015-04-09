package logic.check;

import logic.LogicWithUIMessage;
import data.TreeReasonKeeper;
import type.ReasonTreeNodeType;

public class TreeReasonBudgetCheck extends AbstractCheck {
	
	String name; double value;
	
	public TreeReasonBudgetCheck() {
		super("tree reason budget");
	}
	
	public void setValue(String name,double value){
		this.name=name;
		this.value=value;
	}

	@Override
	public boolean check() {
		if (name.equals("root")) return false;
		TreeReasonKeeper keeper=(TreeReasonKeeper) data.getData("reason");
		ReasonTreeNodeType now=(ReasonTreeNodeType) keeper.getItem(name);
		while (true){
			if (now.getExpenditure()+value > now.getMax()){
				LogicWithUIMessage.setErrorMessage("This reason is over-expenditure.");
				LogicWithUIMessage.UIAction();
				return false;
			}
			if (now.getFather().equals("root")) break;
			now=(ReasonTreeNodeType) keeper.getItem(now.getFather());
		}
		return true;
	}

}
