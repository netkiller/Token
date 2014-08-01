package com.token.android.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.content.Context;

public class DateUtil {

	public static String getDate(Context context, String param1, String param2,
			String param3, String param4, int reTime) {
		SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
		SimpleDateFormat MM = new SimpleDateFormat("MM");
		SimpleDateFormat dd = new SimpleDateFormat("dd");
		SimpleDateFormat HH = new SimpleDateFormat("HH");
		SimpleDateFormat mm = new SimpleDateFormat("mm");

		yyyy.setTimeZone(TimeZone.getTimeZone("UTC"));
		MM.setTimeZone(TimeZone.getTimeZone("UTC"));
		dd.setTimeZone(TimeZone.getTimeZone("UTC"));
		HH.setTimeZone(TimeZone.getTimeZone("UTC"));
		mm.setTimeZone(TimeZone.getTimeZone("UTC"));

		return yyyy.format(new Date())
				+ param1
				+ MM.format(new Date())
				+ param2
				+ dd.format(new Date())
				+ param3
				+ HH.format(new Date())
				+ param4
				+ (Integer.parseInt(mm.format(new Date())) - (Integer
						.parseInt(mm.format(new Date())) % reTime));

	}

	public static int getSS() {
		SimpleDateFormat sdf = new SimpleDateFormat("ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return Integer.parseInt(sdf.format(new Date()));
	}

	public static int getMM() {
		SimpleDateFormat sdf = new SimpleDateFormat("mm");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return Integer.parseInt(sdf.format(new Date()));
	}
}
