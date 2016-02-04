package com.marist.mscs721;

/** 
 * import package
 */
import java.util.ArrayList;


/**
 * Class to demonstrate Room
 */
public class Room {	
	
	private String name;
	private int capacity;
	private ArrayList<Meeting> meetings;
	
	
	// method to the set the values like roomname, capacity and meetings	 
	public Room(String newName, int newCapacity) {
		setName(newName);
		setCapacity(newCapacity);
		setMeetings(new ArrayList<Meeting>());
	}

	// method to add meetings
	public void addMeeting(Meeting newMeeting) {
		this.getMeetings().add(newMeeting);
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getCapacity() {
		return capacity;
	}


	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	public ArrayList<Meeting> getMeetings() {
		return meetings;
	}


	public void setMeetings(ArrayList<Meeting> meetings) {
		this.meetings = meetings;
	}
	
}
