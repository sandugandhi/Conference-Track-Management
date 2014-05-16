package com.techconf.schedule;

import java.util.List;
import java.util.ArrayList;

import com.techconf.exception.SchedulerException;

public class Track {
	static int count = 0;
	private final int ID;
	private List<Session> sessions;
	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	private int trackEndTime;

	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	public Track() {
		this.ID = ++count;
		sessions = new ArrayList<Session>();
	}

	public Session addNewSession(String sessionName) throws SchedulerException {
		Session s = new Session(sessionName);
		sessions.add(s);
		return s;
	}

	public void printTrack() {
		// TODO Auto-generated method stub

		for (Session s : sessions) {
			s.printSession();
		}

	}

	public int getTrackEndTime() {
		return trackEndTime;
	}

	public void setTrackEndTime(int trackEndTime) {
		this.trackEndTime = trackEndTime;
	}

	public void calculateTrackEndTime(Session s) {
		// TODO Auto-generated method stub
		s.calcEndSessionTime();
	}

}
