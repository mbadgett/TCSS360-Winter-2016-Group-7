package data.junit;

import static org.junit.Assert.*;

import java.util.Date;

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
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		testDB = new JobDB();
		testUser = new PMUser("last", "first", "email@email.com");
		testPark = new Park("parkname", "address", testUser);
		//test jobs wont work if the dates within are null
		//not super sure what int light med heavy are supposed to indicate
		testJob = new Job("a", new Date(), new Date(), testPark, 5, 3, 3);
		testJob.getStartDate().setDate(testJob.getStartDate().getDate() + 1);
		testJob.getEndDate().setDate(testJob.getEndDate().getDate() + 1);
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
		//test to see if a job gets added to the list
		testDB.addJob(testJob);
		int size = testDB.getAllJobs().size();
		assertEquals(size, 1);
		
		//can add the same job twice
//		System.out.println(testDB.addJob(testJob));
//		System.out.println(testDB.addJob(testJob));
//		System.out.println(testDB.addJob(testJob));
//		System.out.println(testDB.addJob(testJob));
//		System.out.println(testDB.addJob(testJob));
//		System.out.println(testDB.addJob(testJob));
//		System.out.println(testDB.addJob(testJob));
//		System.out.println(testDB.addJob(testJob));
//		System.out.println(testDB.jobsInWeek(testJob));
		
		//test to make sure a job with a start date in the future can't be added
//		Job compareJob1 = new Job("b", new Date(), new Date(), testPark, 1, 1, 1);
//		testJob.getStartDate().setDate(testJob.getStartDate().getDate() + 1);
//		System.out.println(testDB.addJob(testJob));
		
		
		//test to see if more than 5 jobs can be added in a week
//		Job compareJob1 = new Job("b", new Date(), new Date(), testPark, 1, 1, 1);
//		compareJob1.getStartDate().setDate(compareJob1.getStartDate().getDate() + 1);
//		System.out.println(testDB.addJob(compareJob1));
////		testDB.addJob(compareJob1);
//		compareJob1.getStartDate().setDate(compareJob1.getStartDate().getDate() + 1);
//		System.out.println(testDB.addJob(compareJob1));
////		testDB.addJob(compareJob1);
//		compareJob1.getStartDate().setDate(compareJob1.getStartDate().getDate() + 1);
//		System.out.println(testDB.addJob(compareJob1));
////		testDB.addJob(compareJob1);
//		compareJob1.getStartDate().setDate(compareJob1.getStartDate().getDate() + 1);
//		System.out.println(testDB.addJob(compareJob1));
////		testDB.addJob(compareJob1);
//		compareJob1.getStartDate().setDate(compareJob1.getStartDate().getDate() + 1);
//		System.out.println(testDB.addJob(compareJob1));
		
		
	}
	
	/*
	 * Test on a job within 90 days, and one outside of 90 days.
	 */
	@Test
	public void testWithin90() {
		assertTrue(testDB.within90(testJob));
		testJob.getStartDate().setDate(testJob.getStartDate().getDate() + 90);
		testJob.getEndDate().setDate(testJob.getEndDate().getDate() + 91);
		assertFalse(testDB.within90(testJob));
	}
	
	/*
	 * Tests no jobs in week, tests 5 jobs in 1 week.
	 */
	@Test
	public void testJobsInWeek() {
		assertEquals(testDB.jobsInWeek(testJob), 0);
		testDB.addJob(testJob);
		assertEquals(testDB.jobsInWeek(testJob), 1);
		testDB.addJob(testJob);
		testDB.addJob(testJob);
		testDB.addJob(testJob);
		testDB.addJob(testJob); //5 jobs currently in week
		assertEquals(testDB.jobsInWeek(testJob), 5);
	}
	
	/*
	 * Tests to see if the job gets deleted appropriately.
	 * Uses the get method to see if it's there.
	 */
	@Test
	public void testDeleteJob() {
		testDB.addJob(testJob);
		assertEquals(testDB.getAllJobs().size(), 1);
		testDB.deleteJob(testJob);
		assertEquals(testDB.getAllJobs().size(), 0);
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
		testDB.addJob(testJob);
		
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
