package com.marist.mscs721;

/**
 * @author Sandeep Reddy Salla
 *
 * The program room scheduler provides features to add
 * new rooms, schedule them and we can also delete the
 * rooms.It also provides features to list the rooms
 * and its schedule.
 * Functionality to export and import the data is provided.
 *
 */

/**
 * import required packages
 * */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * This class demonstrates RoomScheduler Process
 */
public class RoomScheduler {
	private RoomScheduler(){
		
	}
	protected static Scanner keyboard = new Scanner(System.in);
	private static final Logger logger = Logger.getLogger("com.marist.mscs721.RoomScheduler");	
//	String logFile = "c://logfile.log";
//	FileHandler fh = new FileHandler(logFile);
	/**
	 * Entry point of the RoomScheduler program
	 */
	
	public static void main(String[] args) {		
		Boolean end = false;
		ArrayList<Room> rooms = new ArrayList<Room>();

		while (!end) {
			// It will switch to a particular method based on the selection
			switch (mainMenu()) {

			case 1:
				logger.info(addRoom(rooms));
				break;
			case 2:
				logger.info(removeRoom(rooms));
				break;
			case 3:
				logger.info(scheduleRoom(rooms));
				break;
			case 4:
				logger.info(listSchedule(rooms));
				break;
			case 5:
				logger.info(listRooms(rooms));
				break;
			case 6:
				logger.info(exportData(rooms));
				break;
			case 7:
				logger.info(importData(rooms));
				break;
			default: 
				logger.info("Invalid selection");
				break;
			}

		}

	}

	/**
	 * method to display the scheduled room list
	 * 
	 * @param roomList
	 *            : contains all rooms
	 */
	protected static String listSchedule(ArrayList<Room> roomList) {
		try {
			String roomName = getRoomName();
			logger.info(roomName + " Schedule");
			logger.info("---------------------");
			// retrieve details related to meetings of a room
			for (Meeting m : getRoomFromName(roomList, roomName).getMeetings()) {
				logger.info(m.toString());
			}
		} catch (Exception e) {			
			logger.log(Level.SEVERE,"",e);
		}
		return "";
	}

	/**
	 * method to display the main menu It displays the option number and name to
	 * the user
	 */
	protected static int mainMenu() {

		System.out.println("Main Menu:");
		System.out.println(" 1 - Add a room");
		System.out.println(" 2 - Remove a room");
		System.out.println(" 3 - Schedule a room");
		System.out.println(" 4 - List Schedule");
		System.out.println(" 5 - List Rooms");
		System.out.println(" 6 - Export data");
		System.out.println(" 7 - Import data");
		System.out.println("Enter your selection: ");

		return keyboard.nextInt();

	}

	/**
	 * method to add a new room
	 * 
	 * @param roomList
	 *            : rooms are added to the list
	 * @return: It will return the name of the room that is added
	 */
	protected static String addRoom(ArrayList<Room> roomList) {
		try {
			System.out.println("Add a room:");
			String name = getRoomName();
			// to check whether the room name is empty or not
			if (name != null && !name.isEmpty()) {
				System.out.println("Room capacity?");
				int capacity = keyboard.nextInt();
				Room newRoom = new Room(name, capacity);
				roomList.add(newRoom); // add room to the list

				return "Room '" + newRoom.getName() + "' added successfully!";
			} else {
				System.out.println("Name should not be empty");
			}
		} catch (InputMismatchException e) {		
			logger.info("Please enter only numeric values");
		}
		return "";
	}

	/**
	 * method to delete the existing room
	 */
	protected static String removeRoom(ArrayList<Room> roomList) {
		try {			
			System.out.println("Remove a room:");
			roomList.remove(findRoomIndex(roomList, getRoomName()));
			return "Room removed successfully!";
		} catch (IndexOutOfBoundsException e) {		
			logger.log(Level.INFO,"Please enter a valid room name",e);
		}
		return "";
	}

	/**
	 * method to display the rooms along with its capacity	
	 */
	protected static String listRooms(ArrayList<Room> roomList) {
		String returnMsg = "";
		try {
			System.out.println("Room Name - Capacity");
			System.out.println("---------------------");
			// retrieve room and capacity from the list
			for (Room room : roomList) {
				System.out.println(room.getName() + " - " + room.getCapacity());
			}

			System.out.println("---------------------");
			returnMsg = roomList.size() + " Room(s)";
		} catch (Exception e) {			
			logger.log(null, "Error", e);
		}
		return returnMsg;
	}

	/**
	 * method to schedule a room	
	 */
	
	protected static String scheduleRoom(ArrayList<Room> roomList) {
		try {
			System.out.println("Schedule a room:");
			String name = getRoomName();

			// check whether room exists or not
			for (Room room : roomList) {
				if (room.getName().contains(name)) {
					System.out.println("Start Date? (yyyy-mm-dd):");
					String startDate = keyboard.next();

					System.out.println("Start Time? (hh:mm)");
					String startTime = keyboard.next();
					startTime = startTime + ":00";

					System.out.println("End Date? (yyyy-mm-dd):");
					String endDate = keyboard.next();
					System.out.println("End Time? (hh:mm)");
					String endTime = keyboard.next();
					endTime = endTime + ":00";
					
					Timestamp startTimestamp = Timestamp.valueOf(startDate
							+ " " + startTime);

					Timestamp endTimestamp = Timestamp.valueOf(endDate + " "
							+ endTime);

					System.out.println("Subject?");
					String subject = "";
					Scanner scanner = new Scanner(System.in);
					subject = scanner.nextLine();

					Room curRoom = getRoomFromName(roomList, name);
					Meeting meeting = new Meeting(startTimestamp, endTimestamp,
							subject);
					curRoom.addMeeting(meeting);

					return "Successfully scheduled meeting!\n\n";

				} else {
					System.out.println("room does not exist");
				}
			}

		} catch (Exception e) {
			System.out.println("Exception Message" + e);
		}		
		return "";
	}

	/**
	 * Method to get the name of the room	 
	 */
	protected static Room getRoomFromName(ArrayList<Room> roomList, String name) {
		return roomList.get(findRoomIndex(roomList, name));
	}

	/**
	 * to find the indexes for list of rooms	
	 */
	protected static int findRoomIndex(ArrayList<Room> roomList, String roomName) {
		int roomIndex = 0;
		for (Room room : roomList) {
			if (room.getName().compareTo(roomName) == 0) {
				break;
			}
			roomIndex++;
		}

		return roomIndex;
	}

	/**
	 * method getRoomName is used to accept the input
	 */
	protected static String getRoomName() {
		System.out.println("Room Name?");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine().trim(); // trim is used to remove white spaces
	}

	/**
	 * Method to export data like rooms, capacity and meetings array in json
	 * format into a file	 
	 */
	protected static String exportData(ArrayList<Room> roomList) {

		JSONObject json = new JSONObject();

		for (Room room : roomList) {
			json.put("RoomName", room.getName());
			json.put("Capacity", room.getCapacity());
			JSONArray meetings = new JSONArray();

			for (Meeting m : room.getMeetings()) {
				meetings.add(m.toString());
			}
			json.put("Meetings", meetings);

			// write data to a file
			try (PrintWriter file = new PrintWriter(new BufferedWriter(
					new FileWriter("D:\\JavaWorkSpace\\result.json", true)))) {
				file.write(json.toJSONString());
				file.write("\n");
				file.flush();
				file.close();
			} catch (IOException e) {
				logger.log(Level.INFO,"Exception Message",e);
			}
			System.out.println("Data exported successfully");
		}
		return "";
	}

	/**
	 * Method to import json data from the specific path
	 */
	protected static String importData(ArrayList<Room> roomList) {
		BufferedReader br = null;
		JSONParser parser = new JSONParser();

		try {
			String currLine;
			// read data from the file
			br = new BufferedReader(new FileReader(
					"D:\\JavaWorkSpace\\result.json"));

			while ((currLine = br.readLine()) != null) {
				Object obj;
				try {
					obj = parser.parse(currLine);
					JSONObject jsonObject = (JSONObject) obj;
					String name = (String) jsonObject.get("RoomName");
					long capacity = (long) jsonObject.get("Capacity");
					// add the imported data to the roomList
					Room room = new Room(name, (int) capacity);
					roomList.add(room);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Data imported successfully");
		} catch (IOException e) {
			logger.log(Level.WARNING, "Exception Message", e);
		} finally {
			try {
				if (br != null)
					br.close(); // close the buffered reader
			} catch (IOException e) {
				logger.log(Level.INFO,"Exception Message", e);
			}
		}
		return "";
	}	
}
