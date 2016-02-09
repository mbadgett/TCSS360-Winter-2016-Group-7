package data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import users.PMUser;

public class JobDBTest {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Ensures that jobs are added correctly.
	 * Currently only tests to see if a job can be added.
	 * Doesn't test to see if the details of an added job are kept properly.
	 */
	@Test
	public void testAddJob() {
		JobDB testDB = new JobDB();
		PMUser testUser = new PMUser("last", "first", "email@email.com");
		Park testPark = new Park("parkname", "address", testUser);
		Job testJob = new Job("a", null, testPark);
		testDB.addJob(testJob);
		assertTrue(testDB.getAllJobs().size() == 1);
		//todo after getJob is done
	}

	@Test
	public void testDeleteJob() {
		fail("Not yet implemented");
	}

	/**
	 * Ensures the entire job list is returned and contains added jobs.
	 */
	@Test
	public void testGetAllJobs() {
		//can only test if it returns the list field right now,
		//can't test job list integrity yet
		JobDB testDB = new JobDB();
		assertTrue(testDB.getAllJobs().size() == 0);
	}

	/**
	 * Ensures jobs are retrieved properly.
	 */
	@Test
	public void testGetJob() {
		//getJob needs to accept a parameter before I can do anything here.
		fail("Not yet implemented");
	}

	@Test
	public void testGetParkManagerJobs() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVolunteerJobs() {
		fail("Not yet implemented");
	}

}
