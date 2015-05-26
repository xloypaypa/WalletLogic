package database.usernameDB;

import java.util.HashSet;
import java.util.Set;

import database.AbstractDataBase;

public abstract class UsernameDB extends AbstractDataBase {

	protected String username;
	
	protected Set<String> ingore;
	
	public UsernameDB(String username, String aimPath) {
		ingore = new HashSet<String>();
		ingore.add("username");
		this.aimPath = aimPath;
		this.username = username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

}
