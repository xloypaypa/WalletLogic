package logic.setting;

import java.util.Date;

import logic.User;
import logic.history.ReasonHistory;
import database.SettingDB;
import type.UserUpdateTime;

public class UserSettings extends User {
	static UserUpdateTime updateTime=new UserUpdateTime();
	
	public static void loadSetting(){
		SettingDB db=new SettingDB(username, passWord);
		db.addDataBaseReadListenser(new ReadUpdateSettingDBListener(updateTime));
		db.load();
	}
	
	public static void setMonth(int day){
		updateTime.setMonth(day);
		updateSetting();
	}
	
	public static void setYear(int month,int day){
		updateTime.setYear(month, day);
		updateSetting();
	}
	
	public void update(){
		if (!updateTime.needUpdate(new Date())) return ;
		
		ReasonHistory.update();
		updateTime.getNextTime(new Date());
	}
	
	public static void updateSetting(){
		SettingDB db=new SettingDB(username, passWord);
		db.addDataBaseWriteListenser(new WriteUpdateSettingDB(updateTime));
		db.push();
	}
}
