package data;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import type.DetailType;
import type.Type;
import database.noPassword.NoPasswordDataBase;
import database.password.DetailDataBase;

public class DetailKeeper extends TypeKeeper implements DataKeeper {
	
	public DetailKeeper() {
		super("detail");
	}
	
	@Override
	public void loadData() {
		super.loadData();
		this.sort();
	};

	@Override
	protected void loadDataBase() {
		DetailDataBase ans=new DetailDataBase() {
			@Override
			public Type getNewType() {
				return new DetailType();
			}
		};
		ans.setAimFile("/"+username+"/"+keeperName+".txt");
		ans.setPassword(password);
		this.db=ans;
	}
	
	public DetailType getLastDetail(){
		DetailType ans=new DetailType();
		if (allType.size()==0) return ans;
		else return (DetailType) allType.get(allType.size()-1);
	}
	
	public void removeLastDetail() {
		if (allType.size()==0) return ;
		
		allType.removeElementAt(allType.size()-1);
		((DetailDataBase) this.db).removeLastItem();
	}
	
	public Vector <DetailType> getRangeDetail(Date from,Date to){
		Vector <DetailType> ans=new Vector<DetailType>();
		for (int i=0;i<allType.size();i++){
			DetailType now=(DetailType) allType.get(i);
			if (from.before(now.getTime())&&to.after(now.getTime())){
				ans.add(now);
			}
		}
		return ans;
	}
	
	public void sort(){
		DetailType[] a = new DetailType[allType.size()];
		allType.toArray(a);
		Arrays.sort(a, 0, a.length, new Comparator<DetailType>(){
			@Override
			public int compare(DetailType o1, DetailType o2) {
				if (o1.getTime().before(o2.getTime())) return -1;
				else if (o1.getTime().after(o2.getTime())) return 1;
				else return 0;
			}
		});
		allType.removeAllElements();
		for (int i=0;i<a.length;i++){
			allType.addElement(a[i]);
		}
	}
	
	public void export(String path){
		NoPasswordDataBase db=new NoPasswordDataBase() {
			@Override
			public Type getNewType() {return null;}
			
			@Override
			public void addItem(Type type) {
				aimPath=path;
				super.addItem(type);
			}
		};
		
		for (int i=0;i<allType.size();i++){
			db.addItem(allType.get(i));
		}
	}
	
	public void load(String path){
		this.releaseData();
		NoPasswordDataBase db=new NoPasswordDataBase() {
			
			@Override
			public Type getNewType() {
				return new DetailType();
			}
			
			@Override
			public Vector<Type> load() {
				aimPath=path;
				return super.load();
			}
		};
		allType=db.load();
	}

}
