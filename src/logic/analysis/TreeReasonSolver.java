package logic.analysis;

import java.util.Vector;

import data.ReasonKeeper;
import type.ReasonTreeNodeType;
import type.ReasonType;
import type.Type;
import logic.Logic;

public class TreeReasonSolver extends Logic {
	
	public static double solveIncome(ReasonTreeNodeType node) {
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		double ans=node.getIncome();
		Vector <Integer> kids=node.getKid();
		for (int i=0;i<kids.size();i++){
			ReasonType now=(ReasonType) keeper.getAllItem().get(kids.get(i));
			ans-=now.getIncome();
		}
		return ans;
	}
	
	public static double solveExpenditure(ReasonTreeNodeType node) {
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		double ans=node.getExpenditure();
		Vector <Integer> kids=node.getKid();
		for (int i=0;i<kids.size();i++){
			ReasonType now=(ReasonType) keeper.getAllItem().get(kids.get(i));
			ans-=now.getExpenditure();
		}
		return ans;
	}
	
	public static Vector <ReasonTreeNodeType> getRoots(){
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		Vector <ReasonTreeNodeType> ans=new Vector<ReasonTreeNodeType>();
		Vector<Type> allType=keeper.getAllItem();
		for (int i=0;i<allType.size();i++){
			if(((ReasonTreeNodeType) allType.get(i)).getFather().equals("root")){
				ans.add((ReasonTreeNodeType) allType.get(i));
			}
		}
		return ans;
	}
	
	public static Vector <ReasonTreeNodeType> getKid(ReasonTreeNodeType now){
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		Vector <ReasonTreeNodeType> ans=new Vector<ReasonTreeNodeType>();
		Vector<Type> allReason=keeper.getAllItem();
		Vector <Integer> kids=now.getKid();
		for (int i=0;i<kids.size();i++){
			ans.addElement((ReasonTreeNodeType) allReason.get(kids.get(i)));
		}
		return ans;
	}

}
