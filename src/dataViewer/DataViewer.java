package dataViewer;

import java.util.Vector;

import logic.Logic;
import type.Type;
import data.TypeKeeper;

public class DataViewer extends Logic {
	
	protected TypeKeeper keeper;
	
	public void loadData(String name){
		keeper=(TypeKeeper) data.getData(name);
	}
	
	public Vector<Type> getAllItem(){
		return keeper.getAllItem();
	}
	
}
