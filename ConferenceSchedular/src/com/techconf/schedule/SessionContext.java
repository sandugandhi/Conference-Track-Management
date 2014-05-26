package com.techconf.schedule;

import com.techconf.strategy.Strategy;

public class SessionContext {
	// Configured with a ConcreteStrategy object and maintains
	// a reference to a Strategy object 
	    private Strategy strategy;
	 
	    public SessionContext(Strategy strategy) {
	        this.strategy = strategy;
	    }
	    public void executePrintStrategy(Session s) {
	       this.strategy.print(s);
	    }
	    public void executeScheduleStrategy(Session s) {
		       this.strategy.schedule(s);
		    }

};
