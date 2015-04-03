package data;

import type.ReasonTreeNodeType;
import type.Type;
import database.DataBase;

public class TreeReasonKeeper extends ReasonKeeper {
	
	@Override
	public void loadData() {
		super.loadData();
		
	}
	
	protected void loadDataBase() {
		DataBase ans=new DataBase() {
			@Override
			public Type getNewType() {
				return new ReasonTreeNodeType();
			}
		};
		ans.setAimFile("/"+username+"/"+keeperName+".txt");
		ans.setPassword(password);
		this.db=ans;
	}
	
	protected void buildTree(){
		for (int i=0;i<allType.size();i++){
			ReasonTreeNodeType now=(ReasonTreeNodeType) allType.get(i);
			now.clearKid();
		}
		for (int i=0;i<allType.size();i++){
			String father=((ReasonTreeNodeType) allType.get(i)).getFather();
			int fPos=getPos(father);
			((ReasonTreeNodeType) allType.get(i)).setFatherPos(fPos);
			if (fPos!=-1){
				((ReasonTreeNodeType) allType.get(fPos)).addKid(i);
			}
		}
	}
	
}
