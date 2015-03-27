package type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class UserUpdateTime extends Type {
	String type="month";
	Date nextTime;
	
	public boolean needUpdate(Date last){
		if (last.after(nextTime)) return true;
		return false;
	}
	
	public void getNextTime(Date last){
		if (type=="month"){
			Calendar ans=Calendar.getInstance();
			int nextDay=Integer.valueOf(this.getExtraMessage("day"));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				ans.setTime(sdf.parse(sdf.format(last)));
				ans.set(Calendar.DAY_OF_MONTH, nextDay);
				int month=ans.get(Calendar.MONTH)+1;
				if (month==12){
					ans.set(Calendar.YEAR, ans.get(Calendar.YEAR)+1);
					ans.set(Calendar.MONTH, 0);
				}else{
					ans.set(Calendar.MONTH, month);
				}
				nextTime=ans.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if (type=="year"){
			Calendar ans=Calendar.getInstance();
			int nextDay=Integer.valueOf(this.getExtraMessage("day"));
			int nextMonth=Integer.valueOf(this.getExtraMessage("month"));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				ans.setTime(sdf.parse(sdf.format(last)));
				ans.set(Calendar.DAY_OF_MONTH, nextDay);
				ans.set(Calendar.MONTH, nextMonth-1);
				ans.set(Calendar.YEAR, ans.get(Calendar.YEAR)+1);
				nextTime=ans.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String format(){
		String ans=new String();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		ans+="[update type]\r\n"+type+"\r\n";
		ans+="[update next date]\r\n"+sdf.format(nextTime)+"\r\n";
		ans+=super.format();
		return ans;
	}
	
	@Override
	public String getTypeMessage() {
		String ans=new String();
		ans+="[begin]\r\n";
		ans+="[type name]\r\n";
		ans+="user update time\r\n";
		ans+="[type item]\r\n";
		ans+=this.getTypeNumber()+"\r\n";
		return ans;
	}
	
	@Override
	public int getTypeNumber() {
		return super.getTypeNumber()+2;
	}
	
	@Override
	public void solveTypeMessage(Vector<String> message) {
		for (int i=0;i<message.size();i+=2){
			String title=message.get(i);
			String body=message.get(i+1);
			if(title.equals("[update type]")){
				this.type=body;
			}else if (title.equals("[update next date]")){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try {
					this.nextTime=sdf.parse(body);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		super.solveTypeMessage(message);
	}
}
