package com.techconf.json;

import java.util.List;

public class ScheduleConfigData {

	public String getInputfilepath() {
		return inputfilepath;
	}

	public void setInputfilepath(String inputfilepath) {
		this.inputfilepath = inputfilepath;
	}

	public String getMinuteSuffix() {
		return minuteSuffix;
	}

	public void setMinuteSuffix(String minuteSuffix) {
		this.minuteSuffix = minuteSuffix;
	}

	public String getLightningSuffix() {
		return lightningSuffix;
	}

	public void setLightningSuffix(String lightningSuffix) {
		this.lightningSuffix = lightningSuffix;
	}

	public int getMaxTalkMinutes() {
		return maxTalkMinutes;
	}

	public void setMaxTalkMinutes(int maxTalkMinutes) {
		this.maxTalkMinutes = maxTalkMinutes;
	}

	public int getMinTalkMinutes() {
		return minTalkMinutes;
	}

	public void setMinTalkMinutes(int minTalkMinutes) {
		this.minTalkMinutes = minTalkMinutes;
	}

	public int getLightningMinutes() {
		return lightningMinutes;
	}

	public void setLightningMinutes(int lightningMinutes) {
		this.lightningMinutes = lightningMinutes;
	}

	public int getMinNetworkingStartTime() {
		return minNetworkingStartTime;
	}

	public void setMinNetworkingStartTime(int minNetworkingStartTime) {
		this.minNetworkingStartTime = minNetworkingStartTime;
	}

	public List<SessionConfigData> getSessionlist() {
		return sessionlist;
	}

	public void setSessionlist(List<SessionConfigData> sessionlist) {
		this.sessionlist = sessionlist;
	}

	private String inputfilepath;
	private String minuteSuffix;
	private String lightningSuffix;
	private int maxTalkMinutes;
	private int minTalkMinutes;
	private int lightningMinutes;
	private int minNetworkingStartTime;

	private List<SessionConfigData> sessionlist;

	public ScheduleConfigData() {
		// TODO Auto-generated constructor stub
		super();
	}

}
