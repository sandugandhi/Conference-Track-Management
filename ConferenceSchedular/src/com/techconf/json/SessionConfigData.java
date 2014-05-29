package com.techconf.json;

public class SessionConfigData {
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	int id;
	String name;
	int startTime;
	int endTime;

	public SessionConfigData() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return getId() + ", " + getName() + ", " + getStartTime() + ", "
				+ getEndTime();
	}

}