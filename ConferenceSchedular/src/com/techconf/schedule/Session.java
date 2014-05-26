package com.techconf.schedule;

/**
 * Session populates the talks after the solver returns the selected talks
 * For now, it contains various time-date formatting responsibilities as well as the final 
 * talks printing responsibility. These are to be moved out of session.
 * **/

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.techconf.json.SessionConfigData;
import com.techconf.strategy.Strategy;

public class Session {
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public int getID() {
		return id;
	}

	public void setID(int iD) {
		id = iD;
	}

	public List<Talk> getTalks() {
		return talks;
	}

	public void setTalks(List<Talk> talks) {
		this.talks = talks;
	}

	private int id;
	private String type;
	private int startTime;
	private int endTime;  // represents the configured end time of the session
	private static int endSessionTime; // actual end time of the session
	private List<Talk> talks;

	public void addTalk(Talk t) {
		talks.add(new Talk(t));
		calcEndSessionTime();
	}

	// for session time end calculation
	public int calcEndSessionTime() {
		int tsum = 0;
		for (Talk t : talks) {
			tsum += t.getTimeDuration();
		}
		setEndSessionTime(tsum + this.startTime);
		return endSessionTime;
	}
	

	public Session(String str) {
		SessionConfigData d = ScheduleProperties.getSessionConfigData(str);
		this.id = d.getId();
		this.type = d.getName();
		this.startTime = militaryTimeConverter(d.getStartTime());
		this.endTime = militaryTimeConverter(d.getEndTime());
		// System.out.println(smilitaryTime +" " +this.startTime);
		// System.out.println(emilitaryTime +" "+this.endTime);
		talks = new ArrayList<>();
	}

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
	
	/**
	 *  This printSession method is supposed to print in multiple ways.
	 *  For now, this is only printing on console and input is being given from console / file
	 *  Later, all print() methods should be moved out to another library for printing in JSON 
	 *  or normal File or Console etc
	 *  TO DO :  Special status for networking and   
	 * */
	public void print() {
		// TODO Auto-generated method stub

		if ("lunch".equals(this.type)) {
			int duration = this.endTime - this.startTime;
			System.out.println(formatTime(this.startTime) + "Lunch " + duration
					+ "min");
			return;
		}

		int currentTime = this.startTime;
		for (Talk talk : talks) {
			String s = formatTime(currentTime);
			talk.print(s);
			//JSON binding may follow here for JSON output
			currentTime += talk.getTimeDuration();
		}

		if ("afternoon".equals(this.type)) {
			int t = this.calcEndSessionTime();
			//int t = getEndSessionTime();
			int minNetworkingStartTime=0;
			try{
				minNetworkingStartTime = militaryTimeConverter(Integer
					.parseInt(ScheduleProperties
							.getProperty("minNetworkingStartTime")));
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return;
			}
			if (t > minNetworkingStartTime) {
				System.out.println(formatTime(t) + "Networking Event\n");
			} else {
				System.out.println(formatTime(minNetworkingStartTime)
						+ "Networking Event\n");
			}
		}
	}

	private static String formatTime(int currentTime) {
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

	public int getEndSessionTime() {
		return endSessionTime;
	}

	public void setEndSessionTime(int endSessionTime) {
		this.endSessionTime = endSessionTime;
	}
}
