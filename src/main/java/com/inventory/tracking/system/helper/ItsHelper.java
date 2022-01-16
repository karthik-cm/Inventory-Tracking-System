package com.inventory.tracking.system.helper;

import java.sql.Timestamp;
import java.util.Calendar;

public class ItsHelper {
	
	public static Timestamp getCurrentTimestamp() {
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		return currentTimestamp;
	}
	
}
