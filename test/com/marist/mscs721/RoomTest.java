package com.marist.mscs721;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author ssreddy23
 * This class is used to demonstrate Room test
 */

public class RoomTest {

	private Room room;
	/**
	 * This method will add room and it is executed before each test run
	 */
	@Before
	public void addRoom() {
		room = new Room("Hancock", 45, "LowellThomas" , "MaristCampus");
	}
	/**
	 * This method will get the name of the room
	 */
	@Test
	public void getRoom() {
		String getRoomName = room.getName();		
		Assert.assertEquals(getRoomName, "Hancock");
	}	
	
}
