package com.techconf.schedule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.techconf.exception.SchedulerException;
import com.techconf.json.SessionConfigData;
public class SessionContext {
	
	Log log = LogFactory.getLog(SessionContext.class);
	public SuperSession getSessionfromType(SessionConfigData session) throws SchedulerException {
		String sessionName = session.getName();
		
		if ("morning".equals(sessionName) || "afternoon".equals(sessionName)) {
			SuperSession s = new TalkSession(session);
			return s;
		} else if ("lunch".equals(sessionName) || "networking".equals(sessionName)) {
			SuperSession s = new IntervalSession(session);
			return s;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("getSessionfromType : sessionName ");
			sb.append(sessionName);
			sb.append(" is not valid");
			log.fatal(sb.toString());
			throw new SchedulerException(sb.toString());
			}
	}
	
};
