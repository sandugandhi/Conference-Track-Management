package com.techconf.schedule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import com.techconf.exception.SchedulerException;
import com.techconf.schedulelogic.KnapSackSolver;
import com.techconf.schedulelogic.KnapSackSolverRequest;
import com.techconf.schedulelogic.KnapSackSolverResponse;

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

	private List<String> inviteeList;
	private List<Talk> validTalksList;

	public ScheduleController() {
		// TODO Auto-generated constructor stub

	}

	public List<String> getInviteeList() {
		return inviteeList;
	}

	public void setInviteelist(List<String> inviteeList) {
		this.inviteeList = inviteeList;
	}

	/**
	 * To separate input validating from populating validTalkList Tests:
	 * Inviteelist cannot be null Inviteelist cannot have null rows Inviteelist
	 * cannot have numbers at more than one place
	 * **/
	private void validateInviteeList() {
		// TODO Auto-generated method stub
	}

	/**
	 * Load talk list from input file.
	 * 
	 * @param fileName
	 * @return
	 */
	public List<String> setInviteeList(String filename) {
		if (filename == null || "".equals(filename)) {
			filename = ScheduleProperties.getProperty("inputfile");
		}
		inviteeList = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			// Read File Line By Line
			String strLine;
			while ((strLine = br.readLine()) != null) {
				inviteeList.add(strLine);
			}
			validateInviteeList();
			printInviteeList();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return inviteeList;
	}

	private void printInviteeList() {
		// TODO Auto-generated method stub
		System.out.println("Input : \n");
		for (String s : inviteeList) {
			System.out.println(s);
		}
		System.out.println("");
	}

	/**
	 * The purpose of this method is to get the populated Tracks list to the
	 * Schedule
	 * 
	 * **/
	public List<Track> getTrackList() {

		// TODO Auto-generated method stub
		List<Track> tracks = new ArrayList<Track>();
		try {
			populateValidTalks(inviteeList);
			Set<String> s = ScheduleProperties.getSessionConfigDataKeys();

			while (validTalksList.size() > 1) {
				Iterator<String> iter = s.iterator();
				Track track = new Track();
				tracks.add(track);
				while (iter.hasNext() && validTalksList.size() > 1) {
					schedule(track, (String) iter.next());
					removeSelectedTalks();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tracks;
	}

	private void removeSelectedTalks() {
		for (ListIterator<Talk> iter = validTalksList.listIterator(); iter
				.hasNext();) {
			Talk a = iter.next();
			if (a.isIncluded()) {
				iter.remove();
			}
		}
	}

	private void schedule(Track t, String sessionName)
			throws SchedulerException {

		Session s = t.addNewSession(sessionName);
		if (sessionName.equals("lunch") || sessionName.equals("networking"))
			return;
		if (validTalksList.size() < 1)
			return;
		int W = s.getEndTime() - s.getStartTime();
		int N = validTalksList.size();

		int[] profit = new int[N + 1];
		int[] weight = new int[N + 1];
		int i = 1;
		for (Talk proft : validTalksList) {
			weight[i] = proft.getTimeDuration();
			i++;
		}
		i = 1;
		for (Talk wght : validTalksList) {
			profit[i] = wght.getTimeDuration();
			i++;
		}
		KnapSackSolverRequest req = new KnapSackSolverRequest();
		req.setMaxKnapSackSize(W);
		req.setNumSize(N);
		req.setProfit(profit);
		req.setWeight(weight);
		KnapSackSolverResponse res = new KnapSackSolverResponse();
		boolean[] take;
		KnapSackSolver knapSolver = new KnapSackSolver();
		try {
			knapSolver.solver(req, res);
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		take = res.getTake();
		i = 1;
		for (Talk talk : validTalksList) {
			if (take[i]) {
				talk.setIncluded(true);
				s.addTalk(talk);
			}
			i++;
		}
		return;
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
		if (talkList == null) {
			throw new SchedulerException("Empty Talk List");
		}

		validTalksList = new ArrayList<Talk>();
		validTalksList.add(new Talk("dummy", 0)); // for knapSack index will
		// start from 1
		String minSuffix = "min";
		String lightningSuffix = "lightning";
		int maxTalkTime = 0, minTalkTime = 0;
		try {
			maxTalkTime = Integer.parseInt(ScheduleProperties
					.getProperty("maxTalkMinutes"));
			minTalkTime = Integer.parseInt(ScheduleProperties
					.getProperty("minTalkMinutes"));
		} catch (NumberFormatException nfe) {
			throw new SchedulerException(
					"Unable to parse time range from properties file");
		}
		// Iterate list and validate time.
		for (String talk : talkList) {
			talk = talk.replaceAll("\\s+", " ").trim();
			// System.out.println("["+talk+"]");
			if (talk.length() < 6) // minimum length could be "a 1min"
			{ // Just ignore bad input and continue. Don't throw exception or
				// terminate
				continue;
			}
			int lastSpaceIndex = talk.lastIndexOf(" ");
			// if talk does not have any space, means either title or time is
			// missing.
			if (lastSpaceIndex == -1) {
				throw new SchedulerException("Invalid talk, " + talk
						+ ". Talk time must be specified.");
			}

			String name = talk.substring(0, lastSpaceIndex);
			String timeStr = talk.substring(lastSpaceIndex + 1);
			// If title is missing or blank.
			if (name == null || "".equals(name.trim())) {
				throw new SchedulerException("Invalid talk name, " + talk);
			}
			// If time is not ended with min or lightning.
			else if (!timeStr.endsWith(minSuffix)
					&& !timeStr.endsWith(lightningSuffix)) {
				throw new SchedulerException("Invalid talk time, " + talk
						+ ". Time must be in min or in lightning");
			}
			int time = 0;
			// Parse time from the time string .
			try {
				if (timeStr.endsWith(minSuffix)) {
					time = Integer.parseInt(timeStr.substring(0,
							timeStr.indexOf(minSuffix)));
				} else if (timeStr.endsWith(lightningSuffix)) {
					time = Integer.parseInt(ScheduleProperties
							.getProperty("lightningMinutes"));
				}
			} catch (NumberFormatException nfe) {
				throw new SchedulerException(
						"populateValidTalks : Error in parsing the properties file"
								+ " or input configuration file");
			}

			if (time <= maxTalkTime && time >= minTalkTime) {
				// Add talk to the valid talk List.
				validTalksList.add(new Talk(name, time));
				// System.out.println("Considering : " + name);
			} else {
				// System.out.println("Ignoring : " + name);
			}
		}
		return;
	}
}
