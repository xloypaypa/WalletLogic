package logic.wallet;

import java.util.*;

import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import database.*;
import type.*;

public class Detail extends Wallet {
	public DetailType getLastDetail(){
		DetailType ans=new DetailType();
		if (allDetail.size()==0) return ans;
		else return allDetail.get(allDetail.size()-1);
	}
	public Vector <DetailType> getRangeDetail(Date from,Date to){
		Vector <DetailType> ans=new Vector<DetailType>();
		for (int i=0;i<allDetail.size();i++){
			DetailType now=allDetail.get(i);
			if (from.before(now.getTime())&&to.after(now.getTime())){
				ans.add(now);
			}
		}
		return ans;
	}
	
	public void export(String path){
		Calendar c=Calendar.getInstance();
		c.set(0, 0, 0);
		export(path,c.getTime(),new Date());
	}
	
	public void export(String path,Date from,Date to){
		int left,right;
		for (left=0;left<allDetail.size();left++){
			DetailType now=allDetail.get(left);
			if (from.before(now.getTime())) break;
		}
		for (right=allDetail.size()-1;right>=0;right--){
			DetailType now=allDetail.get(right);
			if (to.after(now.getTime())) break;
		}
		right++;
		
		new DetailDB(username, passWord).exportTxt(path,left,right);
	}
	
	public void importDetail(String path){
		Vector <String> file=HHD.readFile(path);
		Vector <DetailType> details=new Vector<DetailType>();
		Vector <Vector <String>> message=new DataBase(username, passWord).solveFile(file);
		for (int i=0;i<message.size();i++){
			DetailType dt=new DetailType();
			dt.solveTypeMessage(message.get(i));
			details.addElement(dt);
		}
		solveDetail(details);
	}
	
	public void getExcel(String path){
		try {
			ExcelWriter ew=new ExcelWriter(path);
			int p=ew.createPage("detail");
			ew.setMergeCells(p, 0, 0, 4, 0);
			ew.setRowHeight(p, 0, 600);
			ew.setColumnWidth(p, 0, 15);
			ew.setColumnWidth(p, 1, 20);
			ew.setColumnWidth(p, 2, 15);
			ew.setColumnWidth(p, 3, 15);
			ew.setColumnWidth(p, 4, 50);
			WritableFont bold = new WritableFont(WritableFont.ARIAL,20,WritableFont.BOLD);
			WritableCellFormat titleFormate = new WritableCellFormat(bold);
			titleFormate.setAlignment(jxl.format.Alignment.CENTRE);
		    titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			ew.addCell(p, 0, 0, "all details table",titleFormate);
			ew.addCell(p, 0, 1, "time");
			ew.addCell(p, 1, 1, "event");
			ew.addCell(p, 2, 1, "money type");
			ew.addCell(p, 3, 1, "money value");
			ew.addCell(p, 4, 1, "reason");
			
			for (int i=0;i<allDetail.size();i++){
				DetailType now=allDetail.get(i);
				ew.addCell(p, 0, i+2, now.getTime().getTime());
				ew.addCell(p, 1, i+2, now.getEvent());
				ew.addCell(p, 2, i+2, now.getType());
				ew.addCell(p, 3, i+2, now.getValue());
				ew.addCell(p, 4, i+2, now.getReason());
			}
			
			ew.writeEnd();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sort(){
		DetailType[] a = new DetailType[allDetail.size()];
		allDetail.toArray(a);
		Arrays.sort(a, 0, a.length, new Comparator<DetailType>(){
			@Override
			public int compare(DetailType o1, DetailType o2) {
				if (o1.getTime().before(o2.getTime())) return -1;
				else if (o1.getTime().after(o2.getTime())) return 1;
				else return 0;
			}
		});
		allDetail.removeAllElements();
		for (int i=0;i<a.length;i++){
			allDetail.addElement(a[i]);
		}
	}
	
}
