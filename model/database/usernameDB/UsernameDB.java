package database.usernameDB;

import java.util.HashSet;
import java.util.Set;

import database.AbstractDataBase;

public abstract class UsernameDB extends AbstractDataBase {

	protected String username;
	
	protected Set<String> ingore = new HashSet<String>();
	
	public UsernameDB() {
		ingore.add("username");
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

}
