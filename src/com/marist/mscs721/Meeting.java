package com.marist.mscs721;

/**
 * import the required packages
 */
import java.sql.Timestamp;

/**
 * class to demonstrate meeting
 */
public class Meeting {
	// initialize the  start time , end time and subject values
	private Timestamp startTime = null; 
	private Timestamp stopTime = null; 
	private String subject = null;     

	
	public Meeting(Timestamp newStartTime, Timestamp newEndTime, String newSubject) {
		setStartTime(newStartTime);
		setStopTime(newEndTime);
		// to check whether the subject is empty or not
		if (newSubject.isEmpty()) {
			setSubject("N/A");
		}
		else {
			setSubject(newSubject);
		}
	}
	@Override
	public String toString() {
		// convert the start time and end time to string
		return this.getStartTime().toString() + " ; " + this.getStopTime() + " ; " + getSubject();
	}
	
	public Timestamp getStartTime() {
		// return the start time
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		// set the start time
		this.startTime = startTime;
	}

	public Timestamp getStopTime() {
		// return the end time
		return stopTime;
	}

	public void setStopTime(Timestamp stopTime) {
		// set the end time
		this.stopTime = stopTime;
	}

	// to get subject details
	public String getSubject() {
		return subject;
	}

	
	public void setSubject(String subject) {
		// set the subject name
		this.subject = subject;
	}

}
