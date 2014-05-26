package com.techconf.strategy;

import com.techconf.schedule.Session;

/**
 *  For printing the schedule into any format (JSON / Console sysout / File) 
 *  as per required by the implementor class 
 * 
 * **/
public abstract interface Strategy {

	public void print(Session s);

	public void schedule(Session s);
}

