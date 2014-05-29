package com.techconf.schedule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Track {
	static int count = 0;
	private final int ID;
	private List<SuperSession> sessions;

	public int getID() {
		return ID;
	}

	public Track() {
		this.ID = ++count;
		sessions = new ArrayList<SuperSession>();
	}

	public void addNewSession(SuperSession s) {
		sessions.add(s);
	}

	public void print() {
		// TODO Auto-generated method stub

		Iterator<SuperSession> iter = sessions.iterator();
		int currentSessionEndTime = 0, prevSessionEndTime = 0;
		while (iter.hasNext()) {
			SuperSession s = iter.next();
			currentSessionEndTime = s.calcEndSessionTime();
			s.print(prevSessionEndTime);
			prevSessionEndTime = currentSessionEndTime;
		}
	}
}
