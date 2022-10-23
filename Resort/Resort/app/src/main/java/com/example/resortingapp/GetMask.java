package com.example.resortingapp;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GetMask {
	public static String getDate(long publicData, int type) {
		final int Day_Mon_Year = 1;             // 31/12/2021
		final int HOUR_MINUTE = 2;             // 22:00
		final int Day_Mon_AND_HOUR_MINUTE = 3; // 31/12/2021 às 22:00
		final int Day_Mon = 4;                 // 31 janeiro

		Locale locale = new Locale("en", "PK");
		String timezone = "PKT";

		SimpleDateFormat diaSdf = new SimpleDateFormat("dd", locale);
		diaSdf.setTimeZone(TimeZone.getTimeZone(timezone));

		SimpleDateFormat mesSdf = new SimpleDateFormat("MM", locale);
		mesSdf.setTimeZone(TimeZone.getTimeZone(timezone));

		SimpleDateFormat anoSdf = new SimpleDateFormat("yyyy", locale);
		anoSdf.setTimeZone(TimeZone.getTimeZone(timezone));

		SimpleDateFormat horaSdf = new SimpleDateFormat("HH", locale);
		horaSdf.setTimeZone(TimeZone.getTimeZone(timezone));

		SimpleDateFormat minuitoSdf = new SimpleDateFormat("mm", locale);
		minuitoSdf.setTimeZone(TimeZone.getTimeZone(timezone));

		DateFormat dateFormat = DateFormat.getDateInstance();
		Date netDate = (new Date(publicData));
		dateFormat.format(netDate);

		String hour = horaSdf.format(netDate);
		String minute = minuitoSdf.format(netDate);

		String date = diaSdf.format(netDate);
		String mon = mesSdf.format(netDate);
		String year = anoSdf.format(netDate);

		if (type == 4) {
			switch (mon) {
				case "01":
					mon = "january";
					break;
				case "02":
					mon = "february";
					break;
				case "03":
					mon = "march";
					break;
				case "04":
					mon = "april";
					break;
				case "05":
					mon = "may";
					break;
				case "06":
					mon = "june";
					break;
				case "7":
					mon = "july";
					break;
				case "08":
					mon = "august";
					break;
				case "09":
					mon = "september";
					break;
				case "10":
					mon = "october";
					break;
				case "11":
					mon = "november";
					break;
				case "12":
					mon = "december";
					break;
				default:
					mon = "";
					break;
			}
		} //meses do year

		String time;
		switch (type) {
			case Day_Mon_Year:
				time = date + "/" + mon + "/" + year;
				break;
			case HOUR_MINUTE:
				time = hour + ":" + minute;
				break;
			case Day_Mon_AND_HOUR_MINUTE:
				time = date + "/" + mon + "/" + year + " às " + hour + ":" + minute;
				break;
			case Day_Mon:
				time = date + "/" + mon;
				break;
			default:
				time = "Erro";
				break;
		}
		return time;
	} //formatacao de datas

	public static String getValue(double value) {
		NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(
				new Locale("en", "PK"))
		);
		return nf.format(value);
	} //formatação de valores
}
