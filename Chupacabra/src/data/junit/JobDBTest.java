package data.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.Job;
import data.JobDB;
import data.Park;
import users.PMUser;

public class JobDBTest {
	JobDB testDB;
	PMUser testUser;
	Park testPark;
	Job testJob;

	/*
	 * Initializes basic objects that will be used in all tests.
	 */
	@Before
	public void setUp() throws Exception {
		testDB = new JobDB();
		testUser = new PMUser("last", "first", "email@email.com");
		testPark = new Park("parkname", "address", testUser);
		testJob = new Job("a", null, null, testPark, 0, 0, 0);
	}

	/*
	 * Ensures that jobs are added correctly.
	 * Currently only tests to see if a job can be added.
	 * Doesn't test to see if the details of an added job are kept properly.
	 * Tests to see if adding more jobs than 5 in a week is possible.
	 * Tests to see if a job can be added in the past.
	 * Tests to see if a a job can be scheduled more than 90 days in the future.
	 * Tests to see if more than 30 jobs can be added.
	 */
	@Test
	public void testAddJob() {
		testDB.addJob(testJob);
		assertTrue(testDB.getAllJobs().size() == 1);
	}
	
	/*
	 * Test on a job within 90 days, and one outside of 90 days.
	 */
	@Test
	public void testWithin90() {
		
	}
	
	/*
	 * Tests no jobs in week, tests 5 jobs in 1 week.
	 */
	@Test
	public void testJobsInWeek() {
		
	}
	
	/*
	 * Tests to see if the job gets added appropriately.
	 * Uses the get method to see if it's there.
	 */
	@Test
	public void testDeleteJob() {
		fail("Not yet implemented");
	}

	/*
	 * Tests to see if no jobs are returned if none are scheduled in the future.
	 * Tests to see if jobs are returned if there are ones within 90 days.
	 */
	@Test
	public void testGetAllJobs() {
		//can only test if it returns the list field right now,
		//can't test job list integrity yet
		testDB = new JobDB();
		assertTrue(testDB.getAllJobs().size() == 0);
	}

	/*
	 * Tests on no jobs, a job that's a different PM,
	 * a job that's the right PM, and multiple jobs that are the right PM.
	 */
	@Test
	public void testGetParkManagerJobs() {
		fail("Not yet implemented");
	}

	/*
	 * Tests on no jobs, a job that's a different volunteer,
	 * a job that's the right volunteer, and multiple jobs that are the right volunteer.
	 */
	@Test
	public void testGetVolunteerJobs() {
		fail("Not yet implemented");
	}
	
	/*
	 * Tests on a park that doesn't exist.
	 * Tests on a park that does exist with no jobs.
	 * Tests on a park that does exist with jobs.
	 */
	@Test
	public void testGetParkJobs() {
		
	}
	
	/*
	 * Tests if the same job can be registered for twice.
	 * Tests if dates intersect -
	 * 	Start date of registered job = end date of new job
	 * 	End date of registered job = start date of new job
	 *  End date of registered job = end date of new job
	 *  Start date of registered job = start date of new job
	 * 	No further testing required - jobs are 2 days max (if i remember correctly)
	 */
	@Test
	public void testCanVolunteer() {
		
	}

}
