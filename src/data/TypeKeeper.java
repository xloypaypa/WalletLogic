package data;

import java.util.Vector;

import database.AbstractDataBase;
import type.Type;

public abstract class TypeKeeper extends UserPublicData implements DataKeeper {
	
	protected Vector <Type> allType=new Vector<>();
	protected AbstractDataBase db;
	
	public TypeKeeper() {
		loadDataBase();
	}
	
	public Vector <Type> getAllItem(){
		return allType;
	}

	@Override
	public void add(Type type) {
		allType.addElement(type);
		db.addItem(type);
	}

	@Override
	public void loadData() {
		allType=db.load();
	}

	@Override
	public void releaseData() {
		allType.removeAllElements();
	}
	
	protected abstract void loadDataBase();

}
