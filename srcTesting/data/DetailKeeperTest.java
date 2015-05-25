package data;

import static org.junit.Assert.*;

import org.junit.Test;

import database.operator.DetailOperator;
import tool.TimeGeter;
import type.DetailType;

public class DetailKeeperTest extends TypeTest {

	@Test
	public void test() {
		DetailOperator dk=new DetailOperator();
		DetailType dt=new DetailType();
		dt.setTime(TimeGeter.getTime(2000, 1, 1));
		dt.setEvent("event one");
		dt.setReason("reason one");
		dt.setType("type one");
		dt.setValue(12);
		dk.add(dt);
		
		dk.loadData();
		assertEquals(dt.format(), dk.getLastDetail().format());
		
		dt=new DetailType();
		dt.setTime(TimeGeter.getTime(1000, 1, 1));
		dt.setEvent("event two");
		dt.setReason("reason two");
		dt.setType("type two");
		dt.setValue(21);
		dk.add(dt);
		
		assertEquals("event two", dk.getLastDetail().getEvent());
		
		dk.loadData();
		assertEquals("event one", dk.getLastDetail().getEvent());
		
		assertEquals(1, dk.getRangeDetail(TimeGeter.getTime(1500, 1, 1),TimeGeter.getTime(2001, 1, 1)).size());
		assertEquals(1, dk.getRangeDetail(TimeGeter.getTime(900, 1, 1),TimeGeter.getTime(1000, 1, 1)).size());
		assertEquals(0, dk.getRangeDetail(TimeGeter.getTime(2000, 1, 1),TimeGeter.getTime(2001, 1, 1)).size());
	}

}
