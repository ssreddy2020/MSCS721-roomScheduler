package com.marist.mscs721;
/**
 * Sandeep Salla License
 *
 * Copyrights: Sandeep, 2016
 */
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;
import java.util.Spliterator;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class demonstrates RoomScheduler Process
 */
public class RoomScheduler {

	protected static Scanner keyboard = new Scanner(System.in);
	private static final Logger logger = Logger
			.getLogger("RoomScheduler.class");

	/**
	 * Entry point of the RoomScheduler program
	 */

	public static void main(String[] args) {
		initializeLog();
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

	private static void initializeLog() {
		Properties prop = new Properties();
		try {
			FileInputStream file = new FileInputStream(
					"configuration/log4j.properties");
			prop.load(file);
			PropertyConfigurator.configure(prop);
		} catch (FileNotFoundException ex) {
			System.out.println("Not able to locate property file");
		} catch (IOException ex) {
			System.out.println("Error while reading the property file");
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
				// for (Meeting m : roomList) {
				logger.info(m.toString());
			}
		} catch (Exception e) {
			logger.error("Exception in List Schedule:");
			logger.error(e);
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
			String name = getRoomName().toLowerCase();
			if (Pattern.matches("[a-zA-Z]+", name)) {
				int count = 0;
				if (count == 0) {
					if (roomList.size() > 0) {
						for (Room room : roomList) {
							if (room.getName().contains(name)) {
								System.out.println("Room already exists");
								break;
							} else {
								count = 1;
							}
						}
					}
				}
				if (count == 1 || roomList.size() == 0) {
					// to check whether the room name is empty or not
					if (name != null && !name.isEmpty()) {
						System.out.println("Room capacity?");
						int capacity = keyboard.nextInt();
						System.out.println("Building?");
						String building = keyboard.next();
						if (Pattern.matches("[a-zA-Z\\s]+", building)) {
							if (building != null && !building.isEmpty()) {
								System.out.println("Location?");
								String location = keyboard.next();
								if (Pattern.matches("[a-zA-Z]+", location)) {
									Room newRoom = new Room(name, capacity,
											building, location);
									roomList.add(newRoom); // add room to the
															// list
									return "Room '" + newRoom.getName()
											+ "' added successfully!";
								} else {
									System.out
											.println("Please enter only alphabets");
								}
							}

						} else {
							System.out.println("Please enter only alphabets");
						}
					} else {
						System.out.println("Name should not be empty");
					}
				}
			} else {
				System.out.println("Please enter only alphabets");
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
			logger.error("Please enter a valid room name:", e);
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

	@SuppressWarnings("resource")
	protected static String scheduleRoom(ArrayList<Room> roomList) {
		try {

			System.out.println("Schedule a room:");

			// check whether room exists or not
			for (Room room : roomList) {

				System.out.println("Start Date? (yyyy-mm-dd):");
				String startDate = keyboard.next();
				// check whether start date and end date format is correct
				boolean vStartDate = dateValidation(startDate);
				if (vStartDate == true) {
					System.out.println("Start Time? (hh:mm)");
					String startTime = keyboard.next();
					startTime = startTime + ":00";

					String avSDate = startDate + " " + startTime + ".0";
					System.out.println(avSDate);
					// functionality to check the available rooms based on
					// start date
					availableRooms(roomList, avSDate);

					String name = getRoomName();
					if (room.getName().contains(name)) {
						System.out.println("End Date? (yyyy-mm-dd):");
						String endDate = keyboard.next();

						boolean vEndDate = dateValidation(endDate);
						if (vEndDate == true) {
							System.out.println("End Time? (hh:mm)");
							String endTime = keyboard.next();
							endTime = endTime + ":00";

							Timestamp startTimestamp = Timestamp
									.valueOf(startDate + " " + startTime);

							Timestamp endTimestamp = Timestamp.valueOf(endDate
									+ " " + endTime);

							System.out.println("Subject?");
							String subject = "";
							Scanner scanner = new Scanner(System.in);
							subject = scanner.nextLine();

							Room curRoom = getRoomFromName(roomList, name);
							Meeting meeting = new Meeting(startTimestamp,
									endTimestamp, subject);
							curRoom.addMeeting(meeting);

							return "Successfully scheduled meeting!\n\n";
						} else {
							System.out.println("Please enter valid end date");
						}
					} else {
						System.out.println("Please enter valid start date");
					}

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
	 * this method is used to check date validation
	 * 
	 * @param in_Date
	 *            : contains the value of start date and end date
	 * @return : it will return boolean value
	 */
	protected static boolean dateValidation(String in_Date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(in_Date.trim());
		} catch (Exception e) {
			return false;
		}
		return true;
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
			File fileName = new File("src/File/result.json");
			try (PrintWriter file = new PrintWriter(new BufferedWriter(
					new FileWriter(fileName, true)))) {
				file.write(json.toJSONString());
				file.write("\n");
				file.flush();
				file.close();
			} catch (IOException e) {
				logger.error("Exception in export method:", e);
			}
			logger.info("Data exported successfully");
		}
		return "";
	}

	/**
	 * this method is used to check the availability of rooms based on start
	 * date and time
	 * 
	 * @param roomList
	 *            : Contains the details of room
	 * @param avDate
	 *            : contains the start date
	 * @return
	 */
	protected static String availableRooms(ArrayList<Room> roomList,
			String avDate) {
		System.out.println("--Available Rooms--");
		for (Room room : roomList) {
			ArrayList<Meeting> sDate = room.getMeetings();
			String listString = "";

			for (Meeting s : sDate) {
				listString += s + "\t";
			}
			String[] spDate = listString.split(";");

			if (spDate[0] == "" || spDate[0] == avDate) {
				System.out.println(room.getName());
			}
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
			File fileName = new File("src/File/result.json");
			// read data from the file
			br = new BufferedReader(new FileReader(fileName));

			while ((currLine = br.readLine()) != null) {
				Object obj;
				try {
					obj = parser.parse(currLine);
					JSONObject jsonObject = (JSONObject) obj;
					String name = (String) jsonObject.get("RoomName");
					long capacity = (long) jsonObject.get("Capacity");
					String building = (String) jsonObject.get("Building");
					String location = (String) jsonObject.get("Location");
					// add the imported data to the roomList
					Room room = new Room(name, (int) capacity, building,
							location);
					roomList.add(room);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			logger.info("Data imported successfully");
		} catch (IOException e) {
			logger.error("Exception in import data:", e);

		} finally {
			try {
				if (br != null)
					br.close(); // close the buffered reader
			} catch (IOException e) {
				logger.error("Exception method in finally:", e);
			}
		}
		return "";
	}
}
