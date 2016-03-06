/**
 * 
 */
package data.junit;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import data.Job;
import data.Park;
import users.PMUser;
import users.VolUser;

/* Tests the Job class.
 * @author Ian Cresse
 * @author Derek Moore
 * @author Son Vu
 * @author Michael Badgett
 *
 */
public class JobTest {
	VolUser testUser;
	Park testPark;
	PMUser testPM;
	Job testJob;
	
	/*
	 * Initializes objects used multiple times.
	 */
	@Before
	public void setUp() throws Exception {
		testUser = new VolUser("last", "first", "email@email.com");
		testPM = new PMUser("last", "first", "email@email@email.com");
		testPark = new Park("name", "address", testPM);
		testJob = new Job("Description", Calendar.getInstance(), Calendar.getInstance(), testPark, 1, 1, 1);
		
	}

	/*
	 * Tests to see if a volunteer is added, and the information is preserved.
	 */
	@Test
	public void testAddVolunteer() {
		assertEquals(testJob.addVolunteer(testUser, 1), "Signed up for Job, light intensity");
		//should be the same user that just got put in.
		assertTrue(testUser.equals(new VolUser("last", "first", "email@email.com")));		
		
		//testing adding to other job intensities
		VolUser testUser2 = new VolUser("last2", "first2", "email2@email.com");
		VolUser testUser3 = new VolUser("last3", "first3", "email3@email.com");
		VolUser testUser4 = new VolUser("last4", "first4", "email4@email.com");
		
		//only one spot for heavy intensity jobs, expect failure
		assertEquals(testJob.addVolunteer(testUser2, 2), "Signed up for Job, medium intensity");
		assertEquals(testJob.addVolunteer(testUser3, 3), "Signed up for Job, heavy intensity");
		assertEquals(testJob.addVolunteer(testUser4, 3), "Could not sign up for job.");
	}
	
	/*
	 * Checks reflexivity, symmetry and inequality.
	 */
	@Test
	public void testEqualsObject() {
		//reflexivity
		assertTrue(testJob.equals(testJob));
		
		//inequality
		Job testJob2 = new Job("Inequality", Calendar.getInstance(), Calendar.getInstance(), testPark, 5, 3, 3);
		assertFalse(testJob.equals(testJob2));
		
		//symmetry
		Job testJob3 = new Job("Description", Calendar.getInstance(), Calendar.getInstance(), testPark, 5, 3, 1);
		testJob3.getStartDate().setTime(testJob.getStartDate().getTime());
		testJob3.getEndDate().setTime(testJob.getEndDate().getTime());
		assertTrue(testJob.equals(testJob3));
		assertTrue(testJob3.equals(testJob));
		
	}
	
	/*
	 * Tests on inputs of intensity 0, 1, 2, 3, 4. 0 and 4 should fail.
	 */
	@Test
	public void testHasAvailableSpot() {
		
		//test a job with intensity 1 available
		assertTrue(testJob.hasAvailableSpot(1));
		assertTrue(testJob.hasAvailableSpot(2));
		assertTrue(testJob.hasAvailableSpot(3));
		
		//test whether weird numbers are possible
		assertFalse(testJob.hasAvailableSpot(9999));
		
		//test a job with each intensity not available
		Job testJob2 = new Job("not the same", Calendar.getInstance(), Calendar.getInstance(), testPark, 0, 0, 0);
		assertFalse(testJob2.hasAvailableSpot(1));
		assertFalse(testJob2.hasAvailableSpot(2));
		assertFalse(testJob2.hasAvailableSpot(3));
		
	}
}
