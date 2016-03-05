package data.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.UserDB;
import users.VolUser;


/* Tests the UserDB class.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
 */
public class UserDBTest {
	UserDB testDB;
	VolUser testUser;

	@Before
	public void setUp() throws Exception {
		testDB = new UserDB();
		testUser = new VolUser("last", "first", "email");
	}

	@Test
	public void testGetUser() {
		testDB.addUser(testUser);
		assertEquals(testDB.getUser("not the email"), null);
		assertEquals(testDB.getUser("email"), testUser);
	}

	//instantiate
	//call getVolunteers, expect none
	//add one volunteer, expect one
	//add a few, expect a few
	//just checking to see if the number of users is right
	//addUser handles user adding integrity
	@Test
	public void testGetVolunteers() {
		assertEquals(testDB.getVolunteers().size(), 0);
		
		testDB.addUser(testUser);
		assertEquals(testDB.getVolunteers().size(), 1);
	}

	@Test
	public void testSearchByLName() {
		//test empty DB and non matches
		assertEquals(testDB.searchByLName("not a name").size(), 0);
		
		//test last name searching
		testDB.addUser(testUser);
		assertEquals(testDB.searchByLName("last").get(0), testUser);
		
		//test searching for the multiple instances of the same last name
		VolUser testUser2 = new VolUser("last", "diff first", "diff email");
		testDB.addUser(testUser2);
		assertEquals(testDB.searchByLName("last").size(), 2);
	}

}
