package com.token.android.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.content.Context;

public class DateUtil {

	public static String getDate(Context context) {
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
				+ PreferencesUtils.getString(context, "a")
				+ MM.format(new Date())
				+ PreferencesUtils.getString(context, "b")
				+ dd.format(new Date())
				+ PreferencesUtils.getString(context, "c")
				+ HH.format(new Date())
				+ PreferencesUtils.getString(context, "d")
				+ mm.format(new Date());

	}

	public static int getSS() {
		SimpleDateFormat sdf = new SimpleDateFormat("ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return Integer.parseInt(sdf.format(new Date()));
	}
}
