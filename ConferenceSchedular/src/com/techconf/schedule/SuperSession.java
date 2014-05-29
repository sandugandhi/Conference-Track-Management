package com.techconf.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.techconf.exception.SchedulerException;
import com.techconf.json.SessionConfigData;

public abstract class SuperSession {

	protected int id;
	protected String type;
	protected int startTime;
	protected int endTime;
	protected int currentSessionEndTime;

	public static int militaryTimeConverter(int militaryTime) {
		Date sdate = null;
		try {
			sdate = new SimpleDateFormat("HHmm").parse(String.format("%04d",
					militaryTime));
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdate);
		return cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE);
	}

	public static String formatTime(int currentTime) {
		// TODO Auto-generated method stub
		int hour = currentTime / 60;
		String dTime;
		if (hour < 12)
			dTime = "AM";
		else
			dTime = "PM";
		if (hour > 12)
			hour = hour - 12;
		return String.format("%02d:%02d %s ", hour, (currentTime % 60), dTime);
	}

	public SuperSession(SessionConfigData session) {
		this.id = session.getId();
		this.type = session.getName();
		this.startTime = militaryTimeConverter(session.getStartTime());
		this.endTime = militaryTimeConverter(session.getEndTime());
	}

	public void print(int prevSessionEndTime) {
		// TODO Auto-generated method stub
	}

	// for session time end calculation
	public abstract int calcEndSessionTime();

	public abstract void schedule(List<Talk> validTalksList)
			throws SchedulerException;

}