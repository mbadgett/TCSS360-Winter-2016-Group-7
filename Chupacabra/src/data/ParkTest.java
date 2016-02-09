package data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import users.PMUser;

public class ParkTest {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Checks equals method for viability.
	 * Tests the park against itself.
	 * Tests the park against an identical park that is a separate object.
	 * Tests the park against a different park.
	 */
	@Test
	public void testEquals() {
		PMUser user = new PMUser("Smith", "John", "jsmith@gmail.com");
		Park a = new Park("name", "address", user);
		Park b = new Park("name", "address", user);
		Park c = new Park("name", "different address", user);
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(a, c);
	}
	
	@Test
	public void testHashcode() {
		fail("n/a");
	}
	
	@Test
	public void testToString() {
		fail("n/a");
	}

}
