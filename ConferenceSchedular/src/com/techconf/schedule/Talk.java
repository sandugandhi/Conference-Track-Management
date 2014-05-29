package com.techconf.schedule;

import java.util.UUID;

/**
 * class Talk storing topic and talk time duration
 */
public class Talk {
	private final String ID;
	private String talkTopic;
	private int timeDuration;
	private boolean isIncluded;

	public boolean isIncluded() {
		return isIncluded;
	}

	public void setIncluded(boolean isIncluded) {
		this.isIncluded = isIncluded;
	}

	/**
	 * Constructor for Talk.
	 * 
	 * @param inputTitle
	 * @param talkTopic
	 * @param time
	 */
	public Talk(String talkTopic, int timeDuration) {
		this.ID = this.generateUniqueKey();
		this.talkTopic = talkTopic;
		this.timeDuration = timeDuration;
		this.isIncluded = false;
	}

	public Talk(Talk t) {
		// TODO Auto-generated constructor stub
		this.ID = t.ID;
		this.talkTopic = t.talkTopic;
		this.timeDuration = t.timeDuration;
		this.isIncluded = true;
	}

	public String generateUniqueKey() {

		String id = UUID.randomUUID().toString();
		return id;
	}

	/**
	 * To get time duration for the talk.
	 * 
	 * @return
	 */
	public int getTimeDuration() {
		return timeDuration;
	}

	/**
	 * To get the talkTopic of the talk.
	 * 
	 * @return
	 */
	public String getTalkTopic() {
		return talkTopic;
	}

	public void print(String startTime) {
		StringBuffer sb = new StringBuffer();
		sb.append(startTime);
		sb.append(this.getTalkTopic());
		sb.append(" ");
		sb.append(this.getTimeDuration());
		sb.append(ScheduleConfigurator.getScheduleConfigData()
				.getMinuteSuffix());
		System.out.println(sb.toString());
	}
}