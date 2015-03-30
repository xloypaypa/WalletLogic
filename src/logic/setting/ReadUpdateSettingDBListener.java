package logic.setting;

import type.UserUpdateTime;
import database.DataBaseReadLisenter;

public class ReadUpdateSettingDBListener extends DataBaseReadLisenter {
	UserUpdateTime updateTime;
	
	public ReadUpdateSettingDBListener(UserUpdateTime updateTime){
		this.setUpdate(updateTime);
	}

	@Override
	public boolean needAction() {
		if (DataBaseReadLisenter.getType().equals("user update time")) return true;
		else return false;
	}
	
	public void setUpdate(UserUpdateTime update){
		this.updateTime=update;
	}

	@Override
	public void loadAction() {
		this.updateTime.solveTypeMessage(getMessage());
	}

}
