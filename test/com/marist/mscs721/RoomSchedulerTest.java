package com.marist.mscs721;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

/**
 * 
 * @author ssreddy23 This class will demonstrate the room scheduler test
 *
 */

public class RoomSchedulerTest {

	ArrayList<Room> roomList = new ArrayList<Room>();

	/**
	 * This method will be executed before each test
	 */
	@Before
	public void setUp() throws Exception {
		Room newRoom = new Room("delRoom", 50, "LowellThomas", "MaristCampus");
		roomList.add(newRoom);
		String addedRoom = newRoom.getName();
		Assert.assertEquals(addedRoom, "delRoom");
	}

	/**
	 * This method is used to add a new room
	 */
	@Test
	public void testAddRoom() {
		Room newRoom = new Room("Donnelly", 50, "Hancock", "MaristCampus");
		roomList.add(newRoom);
		String addedRoom = newRoom.getName();
		Assert.assertEquals(addedRoom, "Donnelly");
	}

	/**
	 * This test method is used to remove room from the list
	 */
	@Test
	public void testRemoveRoom() {
		roomList.remove(RoomScheduler.findRoomIndex(roomList, "delRoom"));
		Assert.assertEquals(0, roomList.size());
	}

	/**
	 * This method is used to display the room and capacity from array list
	 */
	@Test
	public void testListRooms() {
		testAddRoom();
		RoomScheduler.listRooms(roomList);
		Assert.assertEquals(2, roomList.size());
	}

	/**
	 * this method is used to check whether the date is in a specific format or
	 * not
	 */
	@Test
	public void testDateValidation() {
		Boolean dateFormat = RoomScheduler.dateValidation("2016-09-09");
		Assert.assertEquals(true, dateFormat);
	}

	/**
	 * this method is used to schedule the existing room
	 */
	@Test
	public void testScheduleRoom() {
		Room curRoom = RoomScheduler.getRoomFromName(roomList, "delRoom");
		Timestamp startDate = Timestamp.valueOf("2016-09-09 09:09:00.000");
		Timestamp endDate = Timestamp.valueOf("2018-03-05 09:09:00.00");

		Assert.assertNotEquals(startDate, "2016-09-09 10:09:00.000");
		Meeting meeting = new Meeting(startDate, endDate, "Software Testing");
		curRoom.addMeeting(meeting);
	}

	/**
	 * this method will return the room name from name
	 */
	@Test
	public void testGetRoomFromName() {
		Room newRoom = new Room("testROOM", 100, "LowellThomas", "MaristCampus");
		roomList.add(newRoom);
		String addedRoom = newRoom.getName();
		Assert.assertEquals(addedRoom, "testROOM");
		String getRoom = RoomScheduler.getRoomFromName(roomList, "testROOM")
				.getName();
		Assert.assertEquals(getRoom, "testROOM");
	}

	/**
	 * this method is used to get the room Index
	 */
	@Test
	public void testFindRoomIndex() {
		int getIndex = RoomScheduler.findRoomIndex(roomList, "Donnelly");
		Assert.assertEquals(getIndex, 1);
	}

	/**
	 * this method is used to accept the user input
	 */
	@Test
	public void testGetRoomName() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner("LowellThomas");
		String data = "Lowell Thomas";
		boolean str = sc.equals(data);
		Assert.assertEquals(false, str);
	}

	/**
	 * this method is used to export the data to a result.json file
	 */
	@Test
	public void testExportData() {
		testAddRoom();
		RoomScheduler.exportData(roomList);
		Assert.assertEquals(roomList.size(), 2);
	}

	/**
	 * this method is used to import the data from a result.json file
	 */
	@Ignore
	@Test
	public void testImportData() {
		RoomScheduler.importData(roomList);
		assertTrue(roomList.size() <= 1);
	}
}
