package logic;

import translater.Strings;
import logicListener.LogicMessageListener;

public class LogicWithUIMessage extends Logic {
	static LogicMessageListener message=new LogicMessageListener() {
		@Override
		public void logicUIAction() {}
	};
	
	public static void setListenser(LogicMessageListener message){
		LogicWithUIMessage.message=message;
	}
	
	public static void setErrorMessage(String msg){
		message.setErrorFlag(true);
		message.setMessage(Strings.getWord(msg));
	}
	
	public static void setLeftHalfBracketError(){
		message.setErrorFlag(true);
		message.setMessage(Strings.getWord("Please don't use '['!"));
	}
	
	public static void setExistentError(String name){
		message.setErrorFlag(true);
		message.setMessage(Strings.getWord("This "+name+" have exist."));
	}
	
	public static void setNeedInputError(String name){
		message.setErrorFlag(true);
		message.setMessage(Strings.getWord("Please input "+name+"."));
	}
	
	public static void setOKMessage(){
		message.setErrorFlag(false);
		message.setMessage(Strings.getWord("ok."));
	}
	
}
