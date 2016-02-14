package data.junit;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import data.Job;
import data.JobDB;
import data.Park;
import users.PMUser;
import users.VolUser;

@SuppressWarnings("deprecation")
public class JobDBTest {
	JobDB testDB;
	PMUser testPMUser;
	Park testPark;
	Job testJob;

	/*
	 * Initializes basic objects that will be used in all tests.
	 */
	@Before
	public void setUp() throws Exception {
		testDB = new JobDB();
		testPMUser = new PMUser("last", "first", "email@email.com");
		testPark = new Park("parkname", "address", testPMUser);

		//A job in one day
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
	public void testAddOneJob() {
		//test to see if a job gets added to the list
		assertEquals(testDB.addJob(testJob), "Job added.");
	}
	
	@Test
	public void testAddSameJob() {
		testDB.addJob(testJob);
		assertEquals(testDB.addJob(testJob), "Job already exists.");
	}
	
	@Test
	public void testAddBadJob() {
		//job that's 5 days long
		Job testJob2 = new Job("a", new Date(), new Date(), testPark, 5, 3, 3);
		testJob2.getEndDate().setDate(testJob2.getEndDate().getDate() + 5);
		assertEquals(testDB.addJob(testJob2), "Job length is invalid.");
		
		//job that ends before it begins
		Job testJob3 = new Job("a", new Date(), new Date(), testPark, 5, 3, 3);
		testJob3.getEndDate().setDate(testJob3.getEndDate().getDate() - 2);
		assertEquals(testDB.addJob(testJob3), "Job length is invalid.");
		
		//job that ended already
		Job testJob4 = new Job("a", new Date(), new Date(), testPark, 5, 3, 3);
		testJob4.getStartDate().setDate(testJob4.getStartDate().getDate() - 2);
		testJob4.getEndDate().setDate(testJob4.getEndDate().getDate() - 2);
		
		//can't add a job too far into the future
		Job testJob5 = new Job("a", new Date(), new Date(), testPark, 5, 3, 3);
		testJob5.getStartDate().setDate(testJob5.getStartDate().getDate() + 100);
		testJob5.getEndDate().setDate(testJob5.getEndDate().getDate() + 100);
		assertEquals(testDB.addJob(testJob5), "Job must be within 90 days.");
	}
	
	
	@Test
	public void testAddTooManyJobs() {
		Job testJob2 = new Job("2", new Date(), new Date(), testPark, 5, 3, 3);
		testJob2.getStartDate().setDate(testJob2.getStartDate().getDate() + 2);
		testJob2.getEndDate().setDate(testJob2.getEndDate().getDate() + 2);
		
		Job testJob3 = new Job("3", new Date(), new Date(), testPark, 5, 3, 3);
		testJob3.getStartDate().setDate(testJob3.getStartDate().getDate() + 2);
		testJob3.getEndDate().setDate(testJob3.getEndDate().getDate() + 2);
		
		Job testJob4 = new Job("4", new Date(), new Date(), testPark, 5, 3, 3);
		testJob4.getStartDate().setDate(testJob4.getStartDate().getDate() + 2);
		testJob4.getEndDate().setDate(testJob4.getEndDate().getDate() + 2);
		
		Job testJob5 = new Job("5", new Date(), new Date(), testPark, 5, 3, 3);
		testJob5.getStartDate().setDate(testJob5.getStartDate().getDate() + 2);
		testJob5.getEndDate().setDate(testJob5.getEndDate().getDate() + 2);
		
		Job testJob6 = new Job("6", new Date(), new Date(), testPark, 5, 3, 3);
		testJob6.getStartDate().setDate(testJob6.getStartDate().getDate() + 2);
		testJob6.getEndDate().setDate(testJob6.getEndDate().getDate() + 2);
		
		//can't have 6 jobs in the same week.
		testDB.addJob(testJob);
		testDB.addJob(testJob2);
		testDB.addJob(testJob3);
		testDB.addJob(testJob4);
		assertEquals(testDB.addJob(testJob5), "Job added.");
		assertEquals(testDB.addJob(testJob6), "Too many jobs in week.");
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
		Job testJob2 = new Job("2", new Date(), new Date(), testPark, 5, 3, 3);
		testJob2.getStartDate().setDate(testJob2.getStartDate().getDate() + 2);
		testJob2.getEndDate().setDate(testJob2.getEndDate().getDate() + 2);
		
		Job testJob3 = new Job("3", new Date(), new Date(), testPark, 5, 3, 3);
		testJob3.getStartDate().setDate(testJob3.getStartDate().getDate() + 2);
		testJob3.getEndDate().setDate(testJob3.getEndDate().getDate() + 2);
		
		Job testJob4 = new Job("4", new Date(), new Date(), testPark, 5, 3, 3);
		testJob4.getStartDate().setDate(testJob4.getStartDate().getDate() + 2);
		testJob4.getEndDate().setDate(testJob4.getEndDate().getDate() + 2);
		
		Job testJob5 = new Job("5", new Date(), new Date(), testPark, 5, 3, 3);
		testJob5.getStartDate().setDate(testJob5.getStartDate().getDate() + 2);
		testJob5.getEndDate().setDate(testJob5.getEndDate().getDate() + 2);
		
		Job testJob6 = new Job("6", new Date(), new Date(), testPark, 5, 3, 3);
		testJob6.getStartDate().setDate(testJob6.getStartDate().getDate() + 2);
		testJob6.getEndDate().setDate(testJob6.getEndDate().getDate() + 2);
		
		assertEquals(testDB.jobsInWeek(testJob), 0);
		testDB.addJob(testJob);
		assertEquals(testDB.jobsInWeek(testJob), 1);
		testDB.addJob(testJob2);
		testDB.addJob(testJob3);
		testDB.addJob(testJob4);
		testDB.addJob(testJob5); //5 jobs currently in week
		assertEquals(testDB.jobsInWeek(testJob), 5);
	}
	
	/*
	 * Tests to see if the job gets deleted appropriately.
	 * Uses the get method to see if it's there.
	 */
	@Test
	public void testDeleteJob() {
		testDB.addJob(testJob);
		assertEquals(testDB.getPendingJobs().size(), 1);
		testDB.deleteJob(testJob);
		assertEquals(testDB.getPendingJobs().size(), 0);
	}

	/*
	 * Tests to see if no jobs are returned if none are scheduled in the future.
	 * Tests to see if jobs are returned if there are ones within 90 days.
	 */
	@Test
	public void testGetPendingJobs() {
		assertTrue(testDB.getPendingJobs().size() == 0);
		testDB.addJob(testJob);
		assertTrue(testDB.getPendingJobs().size() == 1);
		
	}

	/*
	 * Tests on no jobs, a job that's a different PM,
	 * a job that's the right PM, and multiple jobs that are the right PM.
	 */
	@Test
	public void testGetParkManagerJobs() {
		//expected none, nonexistent park manager
		PMUser diffPM = new PMUser("last2", "first2", "notreal@reallyfake.com");
		assertEquals(testDB.getParkManagerJobs(diffPM).size(), 0);
		//expected none
		assertEquals(testDB.getParkManagerJobs(testPMUser).size(), 0);
		testDB.addJob(testJob);
		
		//expected none, nonexistent park manager despite adding a job to DB
		assertEquals(testDB.getParkManagerJobs(diffPM).size(), 0);
		//expected one
		assertEquals(testDB.getParkManagerJobs(testPMUser).size(), 1);
		
	}

	/*
	 * Tests a volunteer with no jobs and a volunteer with a job.
	 */
	@Test
	public void testGetVolunteerJobs() {
		VolUser testVolunteer = new VolUser("test", "test", "test@email.com");
		assertEquals(testDB.getVolunteerJobs(testVolunteer).size(), 0);
		
		testJob.addVolunteer(testVolunteer, 1);
		testDB.addJob(testJob);
		assertEquals(testDB.getVolunteerJobs(testVolunteer).size(), 1);
		
	}
	
	@Test
	public void testGetParkJobs() {
		//a park with no jobs added should have none in the collection
		assertEquals(testDB.getParkJobs(testPark).size(), 0);
		
		PMUser diffPMUser = new PMUser("last2", "first2", "email2@email.com");
		Park diffPark = new Park("parkname2", "addresse2", diffPMUser);
		Job diffJob = new Job("b", new Date(), new Date(), diffPark, 5, 3, 3);
		testDB.addJob(testJob);
		testDB.addJob(diffJob);
		
		//should only get one of these jobs, and it should be the first job.
		assertEquals(testDB.getParkJobs(testPark).get(0), testJob);
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
		VolUser testVolunteer = new VolUser("test", "test", "test@email.com");
		testDB.addJob(testJob);
		
		//can volunteer for a basic job
		assertTrue(testDB.canVolunteer(testJob, testVolunteer));

		
		//can't volunteer for a job already signed up for
		testJob.addVolunteer(testVolunteer, 1);
		assertFalse(testDB.canVolunteer(testJob, testVolunteer));
		
		
		//can volunteer for a job with the right intensity
		//cant volunteer for a job with no slots
		
		//cant volunteer for a job with the wrong intensity
	}

}
