package com.techconf.strategy;

import com.techconf.schedule.Session;

public class NonTalkSessionStrategy implements Strategy {
	public void print(Session s)
	{
		s.getEndSessionTime();
	}
	public void schedule(Session s)
	{
	}
}
