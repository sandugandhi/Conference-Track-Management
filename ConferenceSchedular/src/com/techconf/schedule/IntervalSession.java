package com.techconf.schedule;

import java.util.List;

import com.techconf.exception.SchedulerException;
import com.techconf.json.SessionConfigData;

public class IntervalSession extends SuperSession {

	public IntervalSession(SessionConfigData session) {
		super(session);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void schedule(List<Talk> validTalksList) throws SchedulerException {
		// TODO Auto-generated method stub
	}

	@Override
	public void print(int prevSessionEndTime) {
		// TODO Auto-generated method stub
		if (prevSessionEndTime == 0) {
			prevSessionEndTime = this.startTime;
		}
		if ("lunch".equals(this.type)) {
			int duration = this.endTime - this.startTime;
			System.out.println(formatTime(this.startTime)
					+ "Lunch "
					+ duration
					+ ScheduleConfigurator.getScheduleConfigData()
							.getMinuteSuffix());
			return;
		}
		if ("networking".equals(this.type)) {
			int minNetworkingStartTime = 0;
			try {
				minNetworkingStartTime = militaryTimeConverter(ScheduleConfigurator
						.getScheduleConfigData().getMinNetworkingStartTime());
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			if (prevSessionEndTime > minNetworkingStartTime) {
				System.out.println(formatTime(prevSessionEndTime)
						+ "Networking Event\n");
			} else {
				System.out.println(formatTime(minNetworkingStartTime)
						+ "Networking Event\n");
			}
		}

	}

	@Override
	public int calcEndSessionTime() {
		// TODO Auto-generated method stub
		return this.endTime;
	}
}
