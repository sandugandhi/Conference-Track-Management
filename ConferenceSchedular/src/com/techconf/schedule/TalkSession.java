package com.techconf.schedule;

/**
 * Session populates the talks after the solver returns the selected talks
 * For now, it contains various time-date formatting responsibilities as well as the final 
 * talks printing responsibility. These are to be moved out of session.
 * **/

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.techconf.exception.SchedulerException;
import com.techconf.json.SessionConfigData;
import com.techconf.schedulelogic.KnapSackSolver;
import com.techconf.schedulelogic.KnapSackSolverRequest;
import com.techconf.schedulelogic.KnapSackSolverResponse;

public class TalkSession extends SuperSession {

	private List<Talk> talks;

	public void addTalk(Talk t) {
		talks.add(new Talk(t));
	}

	// for session time end calculation
	public int calcEndSessionTime() {
		int tsum = 0;
		for (Talk t : talks) {
			tsum += t.getTimeDuration();
		}
		currentSessionEndTime = (tsum + this.startTime);
		return currentSessionEndTime;
	}

	public TalkSession(SessionConfigData session) {
		super(session);
		talks = new ArrayList<>();
	}

	public void schedule(List<Talk> validTalksList) throws SchedulerException {
		KnapSackSolverRequest req = setKnapSackSolverRequest(validTalksList);
		KnapSackSolverResponse res = new KnapSackSolverResponse();
		useSolver(req, res);
		updateValidTalkList(validTalksList, res);
		return;
	}

	private void useSolver(KnapSackSolverRequest req, KnapSackSolverResponse res) {
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
	}

	private KnapSackSolverRequest setKnapSackSolverRequest(
			List<Talk> validTalksList) {
		int W = this.getEndTime() - this.getStartTime();
		int N = validTalksList.size();

		int[] profit = new int[N + 1];
		int[] weight = new int[N + 1];
		int i = 1;
		// profit and weight are same in this case
		for (Talk proft : validTalksList) {
			profit[i] = weight[i] = proft.getTimeDuration();
			i++;
		}
		KnapSackSolverRequest req = new KnapSackSolverRequest();
		req.setMaxKnapSackSize(W);
		req.setNumSize(N);
		req.setProfit(profit);
		req.setWeight(weight);
		return req;
	}

	private void updateValidTalkList(List<Talk> validTalksList,
			KnapSackSolverResponse res) {
		int i;
		boolean[] take;
		take = res.getTake();
		i = 1;
		for (ListIterator<Talk> iter = validTalksList.listIterator(); iter
				.hasNext();) {
			Talk talk = iter.next();
			if (take[i]) {
				talk.setIncluded(true);
				this.addTalk(talk);
				iter.remove();
			}
			i++;
		}
	}

	public int getStartTime() {
		// TODO Auto-generated method stub
		return startTime;
	}

	public int getEndTime() {
		// TODO Auto-generated method stub
		return endTime;
	}

	public void print(int prevEndSessionTime) {
		// TODO Auto-generated method stub

		int currentTime = this.startTime;
		for (Talk talk : talks) {
			String s = formatTime(currentTime);
			talk.print(s);
			// JSON binding may follow here for JSON output
			currentTime += talk.getTimeDuration();
		}
	}

}
