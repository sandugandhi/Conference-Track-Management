package com.techconf.schedule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.techconf.json.ScheduleConfigData;
import com.techconf.json.SessionConfigData;

/**
 * This class sets the Invited presenters List from ScheduleProperties
 * First, the validTalksList is populated from the inviteeList after filtering input data validation
 * Then, a new Track is created for the purpose if adding talks.
 * A new session is created within in this Track.
 * validTalksList is passed to the KnapSack solver.
 * The solver marks the selected Talks which are added to the session within the Track.
 * The already selected tracks are deleted from the validTalksList
 * The above process continues until validTalksList is empty(dummy) 
 */
/**
 * @author sandesh
 * 
 */
public class ScheduleController {

	public ScheduleController() {
		// TODO Auto-generated constructor stub

	}

	private List<Talk> validTalksList;
	private Log log = LogFactory.getLog(ScheduleController.class);

	/**
	 * The purpose of this method is to get the populated Tracks list to the
	 * Schedule
	 * 
	 * @throws Exception
	 * 
	 * **/
	public List<Track> getTrackList(List<String> inviteeList) throws Exception {

		// TODO Auto-generated method stub
		List<Track> tracks = new ArrayList<Track>();
		try {
			populateValidTalks(inviteeList);
			List<SessionConfigData> sessionlist = ScheduleConfigurator
					.getScheduleConfigData().getSessionlist();
			while (validTalksList.size() > 0) {
				Iterator<SessionConfigData> iter = sessionlist.iterator();
				Track track = new Track();
				tracks.add(track);
				while (iter.hasNext()) {
					SessionConfigData session = (SessionConfigData) iter.next();
					SessionContext sc = new SessionContext();
					SuperSession supersession = sc.getSessionfromType(session);
					track.addNewSession(supersession);
					if (validTalksList.size() > 0)
						supersession.schedule(validTalksList);
				}
			}
		} catch (Exception e) {
			log.debug("Exception in ScheduleController::getTrackList");
			e.printStackTrace();
		}
		return tracks;
	}

	/**
	 * Validate talk list, check the time for talk and initialize validTalkList
	 * object accordingly.
	 * 
	 * @param talkList
	 * @throws Exception
	 */

	private void populateValidTalks(List<String> talkList) throws Exception {
		// If talksList is null throw exception invalid list to schedule.
		if (talkList == null || talkList.isEmpty()) {
			log.warn("Input talklist is either not set or empty");
			return;
		}

		validTalksList = new ArrayList<Talk>();
		ScheduleConfigData scd = ScheduleConfigurator.getScheduleConfigData();

		int maxTalkTime = scd.getMaxTalkMinutes();
		int minTalkTime = scd.getMinTalkMinutes();
		int talktime = 0;
		// Iterate list and validate time.
		for (String talk : talkList) {
			talk = talk.replaceAll("\\s+", " ").trim();
			Pattern pattern = Pattern
					.compile("(.*)(\\s){1}([0-2]?[0-9]?[0-9]{1}min|lightning)\\b");
			Matcher matcher = pattern.matcher(talk);
			if (!matcher.matches()) {
				log.warn("Talk:[" + talk
						+ "] was ignored. Check whether it is valid");
				continue;
			}

			talktime = calculateTalkTime(matcher.group(3));
			if (talktime <= maxTalkTime && talktime >= minTalkTime) {
				// Add talk to the valid talk List.
				validTalksList.add(new Talk(matcher.group(1), talktime));
				// System.out.println("Considering : " + name);
			} else {
				log.warn("Talk:[" + talk
						+ "] was ignored. The talk time was out of bounds");
			}
		}
	}

	private int calculateTalkTime(String endingStr) {
		ScheduleConfigData scd = ScheduleConfigurator.getScheduleConfigData();
		String minuteSuffix = scd.getMinuteSuffix();
		String lightningSuffix = scd.getLightningSuffix();
		int talktime = 0;
		try {
			if (endingStr.endsWith(minuteSuffix)) {
				talktime = Integer.parseInt(endingStr.substring(0,
						endingStr.indexOf(minuteSuffix)));
			} else if (endingStr.endsWith(lightningSuffix)) {
				talktime = scd.getLightningMinutes();
			}
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		return talktime;
	}
}
