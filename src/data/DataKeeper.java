package data;

import type.Type;

public interface DataKeeper {
	public boolean isThisKeeper(String keeper);
	public void add(Type type);
	public void loadData();
	public void releaseData();
}
