package data.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.UserDB;
import users.VolUser;

public class UserDBTest {

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * Tests users with the same fields
	 * Tests users with the same fields except email
	 * 
	 */
	@Test
	public void testGetUser() {
		
	}
	
	/*
	 * Tests to verify if any users exist at initialization (none expected)
	 * Tests to see if a user can be added, and checks to see if it got added with getUsers
	 * Tests to see if a bad user can be added (improper fields or null parameters)
	 * 
	 */
	@Test
	public void testAddUser() {
		UserDB testDB = new UserDB();
		//check to ensure there's no users right now
		VolUser testUser = new VolUser("last", "first", "email@email.com");
		testDB.addUser(testUser);
		//fetch user list
		//check to see if it's just this user in the collection
		//check details of user to ensure it's the right one
		//see if adding in null info in the user and adding it to the testDB breaks it
	}

	//instantiate
	//call getVolunteers, expect none
	//add one volunteer, expect one
	//add a few, expect a few
	//just checking to see if the number of users is right
	//addUser handles user adding integrity
	@Test
	public void testGetVolunteers() {

		fail("Not yet implemented");
	}

	//instantiate
	//test on a name (expect nothing or an exception)
	//add a user with a specific last name
	//search for a different name than the one put in
	//should say no name found
	//search for the first name
	//should say no name found
	//search for the email
	//should say no name found
	//search for the right last name
	//should say name found
	@Test
	public void testSearchByLName() {

		fail("Not yet implemented");
	}

}
