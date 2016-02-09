/**
 * 
 */
package data;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import users.VolUser;

/**
 * @author Gehennom
 *
 */
public class JobTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link data.Job#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link data.Job#Job(java.lang.String, java.util.Date, data.Park)}.
	 */
	@Test
	public void testJob() {
		//need getters in order to test this properly
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link data.Job#addVolunteer(users.VolUser)}.
	 */
	@Test
	public void testAddVolunteer() {
		VolUser testUser = new VolUser("last", "first", "email@email.com");
		Job testJob = new Job("Description", null, null);
		
		testJob.addVolunteer(testUser);
		
		ArrayList<VolUser> testArray = new ArrayList<VolUser>();
		testArray = (ArrayList<VolUser>) testJob.getVolunteers();
		//should only have one element in it
		assertTrue(testArray.size() == 1);
		
		VolUser compareUser = testArray.get(0);
		//should be the same user that just got put in.
		assertTrue(testUser.equals(compareUser));
	}

	/**
	 * Test method for {@link data.Job#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link data.Job#getVolunteers()}.
	 */
	@Test
	public void testGetVolunteers() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link data.Job#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
