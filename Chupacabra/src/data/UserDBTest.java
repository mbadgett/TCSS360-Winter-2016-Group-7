package data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import users.VolUser;

public class UserDBTest {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Ensures that adding users is successful.
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

	@Test
	public void testGetVolunteers() {
		//instantiate
		//call getVolunteers, expect none
		//add one volunteer, expect one
		//add a few, expect a few
		//just checking to see if the number of users is right
		//addUser handles user adding integrity
		fail("Not yet implemented");
	}

	@Test
	public void testSearchByLName() {
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
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
