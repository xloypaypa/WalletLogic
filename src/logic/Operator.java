package logic;

import java.util.Date;

import data.DebtKeeper;
import data.DetailKeeper;
import data.MoneyKeeper;
import data.ReasonKeeper;
import data.TreeReasonKeeper;
import data.UserData;
import data.UserPublicData;

public class Operator extends Logic {
	
	static TypeLogic type;
	static CostLogic cost;
	static ReasonLogic reason;
	static DebtLogic debt;
	
	public static void login(String name,String pass){
		if (UserLogic.login(name, pass)){
			data=new UserData();
			data.addDataKeeper(new MoneyKeeper());
			data.addDataKeeper(new DebtKeeper());
			data.addDataKeeper(new DetailKeeper());
			data.loadAllData();
			
			type=new TypeLogic();
			cost=new CostLogic();
			debt=new DebtLogic();
			
			if (UserPublicData.getUserReason(name).equals("tree")){
				reason=new TreeReasonLogic();
				data.addDataKeeper(new TreeReasonKeeper());
			}else{
				reason=new ReasonLogic();
				data.addDataKeeper(new ReasonKeeper());
			}
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
	
	public static void income(String type,double value,String reason){
		cost.income(type, value,reason);
	}
	
	public static void expenditure(String type,double value,String reason){
		cost.expenditure(type, value,reason);
	}
	
	public static void transfer(String from, String to, double value){
		cost.transfer(from, to, value);
	}
	
	public static void addReason(String name){
		reason.addReason(name);
	}
	
	public static void renameReason(String past,String name){
		reason.renameReason(past, name);
	}
	
	public static void removeReason(String name){
		reason.removeReason(name);
	}
	
	public static void addTreeReason(String name,String father,double min,double max,int rank){
		
	}
	
	public static void renameTreeReason(String past,String name,String father,double min,double max,int rank){
		
	}
	
	public static void addBorrowing(String credior,double value,String moneyType,Date deadline,double rate,String rateType){
		debt.addBorrowing(credior, value, moneyType, deadline, rate, rateType);
	}
	
	public static void repayBorrowing(int id,double value,String moneyType){
		debt.repayBorrowing(id, value, moneyType);
	}
	
	public static void addLoan(String credior,double value,String moneyType,Date deadline,double rate,String rateType){
		debt.addLoan(credior, value, moneyType, deadline, rate, rateType);
	}
	
	public static void repayLoan(int id,double value,String moneyType){
		debt.repayLoan(id, value, moneyType);
	}
	
}
