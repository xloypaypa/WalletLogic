package logic;

import data.DebtKeeper;
import data.DetailKeeper;
import data.MoneyKeeper;
import data.ReasonKeeper;
import data.UserData;

public class Operator extends Logic {
	
	static TypeLogic type;
	static CostLogic cost;
	
	public static void login(String name,String pass){
		if (UserLogic.login(name, pass)){
			data=new UserData();
			data.addDataKeeper(new MoneyKeeper());
			data.addDataKeeper(new ReasonKeeper());
			data.addDataKeeper(new DebtKeeper());
			data.addDataKeeper(new DetailKeeper());
			data.loadAllData();
			
			type=new TypeLogic();
			cost=new CostLogic();
		}
	}
	
	public static void register(String name,String pass,String repeat,String reason){
		UserLogic.register(name, pass, repeat, reason);
	}
	
	public static void addMoneyType(String name){
		type.addType(name);
	}
	
	public static void renameMoneyType(String past,String name){
		type.renameType(past, name);
	}
	
	public static void removeMoneyType(String name){
		type.removeType(name);
	}
	
	public static void income(String type,double value){
		cost.income(type, value);
	}
	
	public static void expenditure(String type,double value){
		cost.expenditure(type, value);
	}
	
	public static void transfer(String from, String to, double value){
		cost.transfer(from, to, value);
	}
	
}
