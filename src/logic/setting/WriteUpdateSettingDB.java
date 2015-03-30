package logic.setting;

import type.UserUpdateTime;
import database.DataBaseWriteListener;

public class WriteUpdateSettingDB extends DataBaseWriteListener {
	UserUpdateTime updateTime;
	
	public WriteUpdateSettingDB(UserUpdateTime updateTime){
		this.setUpdate(updateTime);
	}
	
	public void setUpdate(UserUpdateTime update){
		this.updateTime=update;
	}

	@Override
	public void writeAction() {
		this.setMessage(updateTime);
	}

}
