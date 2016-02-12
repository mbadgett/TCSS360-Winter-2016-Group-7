/**
 * 
 */
package data.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import data.Job;
import users.VolUser;

/**
 * @author Gehennom
 *
 */
public class JobTest {
	VolUser testUser;
	Job testJob;
	
	/*
	 * Initializes objects used multiple times.
	 */
	@Before
	public void setUp() throws Exception {
		testUser = new VolUser("last", "first", "email@email.com");
		testJob = new Job("Description", null, null, null, 0, 0, 0);
		
	}

	/*
	 * Tests to see if a volunteer is added, and the information is preserved.
	 */
	@Test
	public void testAddVolunteer() {

		testJob.addVolunteer(testUser, 0);
		
		ArrayList<VolUser> testArray = new ArrayList<VolUser>();
		testArray = (ArrayList<VolUser>) testJob.getVolunteers();
		//should only have one element in it
		assertTrue(testArray.size() == 1);
		
		VolUser compareUser = testArray.get(0);
		//should be the same user that just got put in.
		assertTrue(testUser.equals(compareUser));
	}
	
	/*
	 * Checks reflexivity, symmetry and inequality.
	 */
	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}
	
	/*
	 * Tests max available openings, some openings and no openings.
	 */
	@Test
	public void testDisplayOpenings() {
		
	}

	/*
	 * Tests on inputs of intensity 0, 1, 2, 3, 4. 0 and 4 should fail.
	 */
	@Test
	public void testHasAvailableSpot() {
		
	}
}
