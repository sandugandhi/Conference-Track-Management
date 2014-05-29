package com.techconf.schedule;

import java.util.List;

/**
 * This is the entry point class containing main()
 * 
 * @author sandesh gandhi
 * @email sandugandhi@gmail.com
 * @version 0.1 A Schedule consists of a list of Tracks of the event.
 *          Responsibilities : Create a schedule and print it.
 * 
 *          Each track is composed of multiple sessions like morning, lunch,
 *          afternoon and networking. 
 *          Each session could be a talk session (composed of multiple talks) or
 *          an interval session (lunch / networking )
 * 
 *          System settings: 
 *          Ubuntu 14.04 LTS Trusty Tahr x86_64 Eclipse Java EE
 *          Juno JDK JavaSE 1.8.05 (java-8-oracle) 
 *          Requires : 
 *          junit 4.11 
 *          hamcrest-core-1.3 
 *          jackson-core-2.2.3.jar
 *          jackson-databind-2.2.3.jar
 *          jackson-annotations-2.3.3.jar
 *          commons-logging-1.1.3.jar
 *          org.apache.log4j_1.2.15.jar
 * **/

/**
 * Schedule contains an input list given by the user and an output list called
 * tracklist which populated after planning the schedule from input list
 * **/
public class Schedule {

	private List<Track> tracklist;
	private List<String> inputList;

	public Schedule() {
		inputList = null;
		tracklist = null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String inputfile = null;
			if (args.length > 0) {
				inputfile = args[0];
			}
			Schedule schedule = new Schedule();
			schedule.plan(inputfile);
			schedule.print();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * print the schedule
	 */
	public void print() {
		System.out.println("\nInput : \n");
		for (String s : inputList) {
			System.out.println(s);
		}
		System.out.println("\nOutput : \n");
		for (Track t : tracklist) {
			System.out.println("Track : " + t.getID() + "\n");
			t.print();
		}
		System.out.println("============================");
	}

	/**
	 * plan the schedule by requesting for tracks list
	 * 
	 * @throws Exception
	 */
	public void plan(String inputfile) throws Exception {
		InputFileReader ifr = new InputFileReader();
		inputList = ifr.readInput(inputfile);
		ScheduleController sc = new ScheduleController();
		tracklist = sc.getTrackList(inputList);
	}
}
