package logic.wallet;

import static org.junit.Assert.*;

import org.junit.Test;

import tool.TimeGeter;
import type.DetailType;

public class DetailTest extends WalletTest {
	
	@Test
	public void testBaseFeature(){
		Detail detail=new Detail();
		DetailType dt=new DetailType();
		dt.setTime(TimeGeter.getTime(2000, 1, 1));
		detail.addDetail(dt);
		dt.setTime(TimeGeter.getTime(2001, 1, 1));
		detail.addDetail(dt);
		assertEquals(2,detail.getDetail().size());
		assertEquals(1,detail.getRangeDetail(TimeGeter.getTime(2000, 12, 1),TimeGeter.getTime(2002, 1, 1)).size());
		assertEquals(0,detail.getRangeDetail(TimeGeter.getTime(2001, 1, 2),TimeGeter.getTime(2002, 1, 1)).size());
		assertEquals(dt.getAllMessage(), detail.getLastDetail().getAllMessage());
	}
	
	@Test
	public void testSort(){
		Detail detail=new Detail();
		DetailType dt=new DetailType();
		DetailType dt2=new DetailType();
		dt.setTime(TimeGeter.getTime(2001, 1, 1));
		detail.addDetail(dt);
		dt2.setTime(TimeGeter.getTime(2000, 1, 1));
		detail.addDetail(dt2);
		
		detail.sort();
		
		assertEquals(2,detail.getDetail().size());
		assertEquals(1,detail.getRangeDetail(TimeGeter.getTime(2000, 12, 1),TimeGeter.getTime(2002, 1, 1)).size());
		assertEquals(0,detail.getRangeDetail(TimeGeter.getTime(2001, 1, 2),TimeGeter.getTime(2002, 1, 1)).size());
		assertEquals(dt.getAllMessage(), detail.getLastDetail().getAllMessage());
	}

}
