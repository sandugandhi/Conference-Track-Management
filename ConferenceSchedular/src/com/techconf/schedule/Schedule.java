package com.techconf.schedule;

import java.util.List;
/**
 *  This is the entry point class containing main()
 * 
 * @author sandesh gandhi
 * @email  sandugandhi@gmail.com
 * @version 0.1
 * A Schedule consists of a list of Tracks of the event.
 * Responsibilities : Create a schedule and print it. 
 * 
 * Each track is composed of multiple sessions like morning, lunch, afternoon and networking  
 * Each session is composed of multiple talks, each on a unique topic.
 * 
 * System settings:
 * Ubuntu 14.04 LTS Trusty Tahr  x86_64 
 * Eclipse Java EE Juno
 * JDK  JavaSE 1.8.05  (java-8-oracle)
 * Requires : junit 4.11   hamcrest-core-1.3  jackson-all-1.9.11  json-simple-1.1.1
 *
 * TroubleShooting:
 * After creating the jar (executable), it does not seem to include the resources files 
 * (input configuration files ) in the res/ folder. Instead, they are placed directly at root.
 *  Hence, resulting into "File not found error"
 * **/

public class Schedule {

	private List<Track> tracklist;

	public Schedule() {
		tracklist = null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String inputfile=null;
			 if(args.length > 0)
			 {
				 inputfile=args[0]; 	
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
		// TODO Auto-generated method stub
		System.out.println("\nOutput : \n");
		for (Track t : tracklist) {
			System.out.println("Track : " + t.getID() + "\n");
			t.printTrack();
		}
		System.out.println("============================");
	}

	/**
	 * plan the schedule by requesting for tracks list
	 */
	public void plan(String inputfile ) {
		// TODO Auto-generated method stub
		ScheduleController sc = new ScheduleController();
		sc.setInviteeList(inputfile);
		tracklist = sc.getTrackList();
		
	}
}
