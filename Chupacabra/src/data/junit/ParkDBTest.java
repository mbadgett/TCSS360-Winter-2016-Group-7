//Is there really anything to test here yet? Need some getters/setters at least.
package data.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import data.Park;
import data.ParkDB;
import users.PMUser;

/* Tests the ParkDB class.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
 *
 */
public class ParkDBTest {

	/*
	 * Tests on a non-existing PMUser
	 * Tests on an existing PMUser
	 */
	@Test
	public void testGetParksManagedByPMUser() {
		PMUser testUser = new PMUser("last", "first", "email");
		Park testPark = new Park("name", "address", testUser);
		ParkDB testDB = new ParkDB();
		
		//no managers yet
		assertEquals(testDB.getParksManagedBy(testUser).size(), 0);
		
		//test finding the right manager
		testDB.addPark(testPark);
		assertEquals(testDB.getParksManagedBy(testUser).size(), 1);
	}
}
