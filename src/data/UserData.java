package data;

import java.util.Vector;

public class UserData {
	Vector <DataKeeper> allData=new Vector<>();
	
	public void addDataKeeper(DataKeeper data){
		this.allData.addElement(data);
	}
	
	public void loadAllData(){
		for (int i=0;i<allData.size();i++){
			allData.get(i).loadData();
		}
	}
	
	public void reloadAllData(){
		for (int i=0;i<allData.size();i++){
			allData.get(i).releaseData();
			allData.get(i).loadData();
		}
	}
	
	public void loadData(String dataName){
		for (int i=0;i<this.allData.size();i++){
			if (this.allData.get(i).isThisKeeper(dataName)){
				allData.get(i).loadData();
			}
		}
	}
	
	public void reloadData(String dataName){
		for (int i=0;i<this.allData.size();i++){
			if (this.allData.get(i).isThisKeeper(dataName)){
				allData.get(i).releaseData();
				allData.get(i).loadData();
			}
		}
	}
	
	public DataKeeper getData(String dataName){
		for (int i=0;i<this.allData.size();i++){
			if (this.allData.get(i).isThisKeeper(dataName)){
				return allData.get(i);
			}
		}
		return null;
	}
}