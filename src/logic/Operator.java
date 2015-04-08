package logic;

import java.util.Date;

import logic.process.backup.debt.AddBorrowingBackup;
import logic.process.backup.debt.AddLoanBackup;
import logic.process.backup.debt.RepayBorrowingBackup;
import logic.process.backup.debt.RepayLoanBackup;
import logic.process.backup.money.AddTypeBackup;
import logic.process.backup.money.ExpenditureBackup;
import logic.process.backup.money.IncomeBackup;
import logic.process.backup.money.RemoveTypeBackup;
import logic.process.backup.money.RenameTypeBackup;
import logic.process.backup.money.TransferBackup;
import logic.process.backup.reason.AddReasonBackup;
import logic.process.backup.reason.AddTreeReasonBackup;
import logic.process.backup.reason.RemoveReasonBackup;
import logic.process.backup.reason.RemoveTreeReasonBackup;
import logic.process.backup.reason.RenameReasonBackup;
import logic.process.backup.reason.RenameTreeReasonBackup;
import data.DebtKeeper;
import data.DetailKeeper;
import data.MoneyKeeper;
import data.ReasonKeeper;
import data.TreeReasonKeeper;
import data.UserData;
import data.UserPublicData;

public class Operator extends Logic {
	
	private static TypeLogic type;
	private static CostLogic cost;
	private static ReasonLogic reason;
	private static DebtLogic debt;
	private static BackupLogic backup;
	
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
			backup=new BackupLogic();
			
			backup.addBackup(new AddBorrowingBackup());
			backup.addBackup(new AddLoanBackup());
			
			backup.addBackup(new RepayBorrowingBackup());
			backup.addBackup(new RepayLoanBackup());
			
			backup.addBackup(new ExpenditureBackup());
			backup.addBackup(new IncomeBackup());
			backup.addBackup(new TransferBackup());
			
			backup.addBackup(new AddTypeBackup());
			backup.addBackup(new RemoveTypeBackup());
			backup.addBackup(new RenameTypeBackup());
			
			if (UserPublicData.getUserReason(name).equals("tree")){
				reason=new TreeReasonLogic();
				data.addDataKeeper(new TreeReasonKeeper());
				
				backup.addBackup(new AddTreeReasonBackup());
				backup.addBackup(new RemoveTreeReasonBackup());
				backup.addBackup(new RenameTreeReasonBackup());
			}else{
				reason=new ReasonLogic();
				data.addDataKeeper(new ReasonKeeper());
				
				backup.addBackup(new AddReasonBackup());
				backup.addBackup(new RemoveReasonBackup());
				backup.addBackup(new RenameReasonBackup());
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
		((TreeReasonLogic)reason).addReason(name, father, min, max, rank);
	}
	
	public static void renameTreeReason(String past,String name,String father,double min,double max,int rank){
		((TreeReasonLogic)reason).renameReason(past, name, father, min, max, rank);
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
	
	public static void backup(){
		backup.backup();
	}
	
}
