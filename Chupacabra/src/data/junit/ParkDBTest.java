//Is there really anything to test here yet? Need some getters/setters at least.
package data.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ParkDBTest {

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * PERHAPS a test to see if a bad park can be added (null parameters)
	 */
	@Test
	public void testAddPark() {
		fail("Not yet implemented");
	}

	/*
	 * Tests on a non-existing PMUser
	 * Tests on an existing PMUser
	 */
	@Test
	public void testGetParksManagedByPMUser() {
		
	}
}
