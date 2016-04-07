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
	private String building;
	private String location;
	
	
	// method to the set the values like room name, capacity and meetings	 
	public Room(String newName, int newCapacity, String newBuilding, String newLocation) {
		setName(newName);
		setCapacity(newCapacity);
		setMeetings(new ArrayList<Meeting>());
		setBuilding(newBuilding);
		setLocation(newLocation);
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
	
	public void setBuilding(String building){
		this.building = building;
	}
	
	public String getBuilding(){
		return building; 
	}
	
	public void setLocation(String location){
		this.location = location;
	}
	
	public String getLocation(){
		return location;
	}
}
