package data;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import exceptions.JobFutureException;
import exceptions.JobLengthException;
import exceptions.JobMaxException;
import exceptions.JobPastException;
import exceptions.JobsInWeekException;
import users.PMUser;
import users.VolUser;

/* Tests the JobDB class.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
 */
public class JobDBTest {
	JobDB testDB, DB30Jobs;
	PMUser testPMUser;
	Park testPark;
	VolUser testVol;
	Job testJob, jobNextYear, jobInPast, jobToday, job91DaysAway, job90DaysAway, jobNextWeek;

	/*
	 * Initializes basic objects that will be used in all tests.
	 */
	@Before
	public void setUp() throws Exception {
		testDB = new JobDB();
		testPMUser = new PMUser("last", "first", "email@email.com");
		testPark = new Park("parkname", "address", testPMUser);
		testVol = new VolUser("name", "othername", "m@m");
		generateTestJobs();
		generateTestDBs();
	}


	
	



	//Within90 tests.
	@Test public void testWithin90Job90DaysAway() {
		assertTrue(testDB.within90(job90DaysAway));		
	}

	@Test public void testWithin90Job91DaysAway() {
		assertTrue(!testDB.within90(job91DaysAway));		
	}

	@Test public void testWithin90JobToday() {
		assertTrue(testDB.within90(jobToday));		
	}

	//checkRecency tests.


	@Test public void testcheckRecencyJob90DaysAway() throws JobFutureException {
		testDB.checkRecency(job90DaysAway);		
	}

	@Test (expected = JobFutureException.class)public void testcheckRecencyJob91DaysAway() throws JobFutureException {
		testDB.checkRecency(job91DaysAway);		
	}

	@Test public void testcheckRecencyJobToday() throws JobFutureException {
		testDB.checkRecency(jobToday);		
	}


	//checkInFture tests.

	@Test (expected = JobPastException.class) public void testCheckInFutreYesterdayJob() throws JobPastException {
		jobToday.getStartDate().add(Calendar.DATE, -1);
		jobToday.getEndDate().add(Calendar.DATE, -1);
		testDB.checkInFuture(jobToday);//modified to yesterday
	}

	@Test (expected = JobPastException.class) public void testCheckInFutreJobLongPast() throws JobPastException {
		testDB.checkInFuture(jobInPast);		
	}	


	//checkScheduleVacancy tests.
	@Test(expected = JobMaxException.class) public void testScheduleVacancyFull() throws JobMaxException {		
		DB30Jobs.checkScheduleVacancy();		
	}

	@Test public void testScheduleVacancySingleVacancy() throws JobMaxException {		
		DB30Jobs.deleteJob(DB30Jobs.getPendingJobs().get(0));
		DB30Jobs.checkScheduleVacancy();
	}


	//chekJobLength tests.

	@Test public void testCheckJobLength1DayLen() throws JobLengthException {
		jobToday.getStartDate().set(2016, 4, 4);
		jobToday.getEndDate().set(2016, 4, 4);
		testDB.checkJobLength(jobToday);
	}



	@Test public void testCheckJobLength2DayLen() throws JobLengthException {
		jobToday.getStartDate().set(2016, 4, 4);
		jobToday.getEndDate().set(2016, 4, 5);
		testDB.checkJobLength(jobToday);
	}


	@Test(expected = JobLengthException.class) public void testCheckJobLength3DayLen() throws JobLengthException {
		jobToday.getStartDate().set(2016, 4, 4);
		jobToday.getEndDate().set(2016, 4, 6);
		testDB.checkJobLength(jobToday);
	}


	@Test(expected = JobLengthException.class) public void testCheckJobLengthNegLen() throws JobLengthException {
		jobToday.getStartDate().set(2016, 4, 4);
		jobToday.getEndDate().set(2016, 4, 3);
		testDB.checkJobLength(jobToday);
	}



	//checkWeekCapacity tests.

	


	@Test public void testCheckWeekCapacityEmptyWeek() throws JobsInWeekException {
		populateNJobsInTestDBSingleWeek(0, jobNextWeek);
		testDB.checkWeekCapacity(jobNextWeek);		
	}




	@Test public void testCheckWeekCapacity4Jobs() throws JobsInWeekException {
		populateNJobsInTestDBSingleWeek(4, jobNextWeek);

		testDB.checkWeekCapacity(jobNextWeek);		
	}

	@Test(expected = JobsInWeekException.class) public void testCheckWeekCapacity5Jobs() throws JobsInWeekException {
		populateNJobsInTestDBSingleWeek(5, jobNextWeek);
		testDB.checkWeekCapacity(jobNextWeek);		
	}


	//jobsInWeek tests

	@Test public void testJobsInWeek0Jobs() {
		populateNJobsInTestDBSingleWeek(0, jobNextWeek);
		System.out.println(testDB.getPendingJobs().size());
		assertTrue(testDB.jobsInWeek(jobNextWeek)== 0);
	}

	@Test public void testJobsInWeek1Job() {
		populateNJobsInTestDBSingleWeek(1, jobNextWeek);
		System.out.println(testDB.getPendingJobs().size());
		assertTrue(testDB.jobsInWeek(jobNextWeek) == 1);
	}
	@Test public void testJobsInWeek4Job() {
		populateNJobsInTestDBSingleWeek(4, jobNextWeek);
		assertTrue(testDB.jobsInWeek(jobNextWeek) == 4);
	}

	@Test public void testJobsInWeek5Jobs() {
		populateNJobsInTestDBSingleWeek(5, jobNextWeek);
		assertTrue(testDB.jobsInWeek(jobNextWeek) == 5);
	}


	//can volunteer tests.

	@Test public void testCanVolunteerVolunteer0jobs() {		
		assertTrue(DB30Jobs.canVolunteer(DB30Jobs.getPendingJobs().get(0), testVol));
	}

	@Test public void testCanVolunteerVolunteerWithjobsNotOnSameDay() {
		DB30Jobs.getPendingJobs().get(6).addVolunteer(testVol, 1);
		DB30Jobs.getPendingJobs().get(9).addVolunteer(testVol, 1);
		DB30Jobs.getPendingJobs().get(11).addVolunteer(testVol, 1);

		assertTrue(DB30Jobs.canVolunteer(DB30Jobs.getPendingJobs().get(0), testVol));
	}

	@Test public void testCanVolunteerVolunteerAlreadyVolunteered() {
		populateNJobsInTestDBSingleDay(1, jobToday);
		testDB.getPendingJobs().get(0).addVolunteer(testVol, 1);
		System.out.println(testDB.getPendingJobs().get(0).getVolunteers().size());
		assertTrue(!testDB.canVolunteer(testDB.getPendingJobs().get(0), testVol));
	}

	@Test public void testCanVolunteerVolunteerWithJobOnSameDay() {
		populateNJobsInTestDBSingleDay(2, jobToday);
		testDB.getPendingJobs().get(0).addVolunteer(testVol, 1);


		System.out.println(testDB.canVolunteer(testDB.getPendingJobs().get(1), testVol));
		assertTrue(!testDB.canVolunteer(testDB.getPendingJobs().get(1), testVol));
	}


	



	//JobsOnSameDay tests.

	@Test public void testJobsOnSameDayJobsStartSame() {
		populateNJobsInTestDBSingleDay(2, jobToday);
		assertTrue(testDB.jobsOnSameDay(testDB.getPendingJobs().get(0), testDB.getPendingJobs().get(1)));
	}

	@Test public void testJobsOnSameDayJobsEndSame() {
		populateNJobsInTestDBSingleDay(2, jobToday);
		testDB.getPendingJobs().get(0).getStartDate().add(Calendar.DATE, 1);
		assertTrue(testDB.jobsOnSameDay(testDB.getPendingJobs().get(0), testDB.getPendingJobs().get(1)));
	}
	@Test public void testJobsOnSameDayStartEndSameDay() {
		populateNJobsInTestDBSingleDay(2, jobToday);
		testDB.getPendingJobs().get(1).getStartDate().add(Calendar.DATE, 1);
		testDB.getPendingJobs().get(1).getEndDate().add(Calendar.DATE, 1);
		assertTrue(testDB.jobsOnSameDay(testDB.getPendingJobs().get(0), testDB.getPendingJobs().get(1)));
	}



	@Test public void testJobsOnSameDayDifferentDays() {
		populateNJobsInTestDBSingleDay(2, jobToday);		
		testDB.getPendingJobs().get(1).getStartDate().add(Calendar.DATE, 2);
		testDB.getPendingJobs().get(1).getEndDate().add(Calendar.DATE, 2);		
		assertTrue(!testDB.jobsOnSameDay(testDB.getPendingJobs().get(0), testDB.getPendingJobs().get(1)));
	}



	//addJob methods, all of the failures are associated with the above methods, they will not be re-tested here.
	//That would be redundant, though valid jobs being added will be tested here.

	@Test public void testAddJobEmptyDB() throws JobMaxException, JobFutureException, JobPastException, JobsInWeekException, JobLengthException {
		testDB.addJob(jobToday);
		assertTrue(jobToday.equals(testDB.getPendingJobs().get(0)));
	}

	@Test public void testAddJobNearlyFullDB() throws JobMaxException, JobFutureException, JobPastException, JobsInWeekException, JobLengthException {
		DB30Jobs.deleteJob(DB30Jobs.getPendingJobs().get(0));//jobtoday removed
		DB30Jobs.addJob(jobToday);
		assertTrue(jobToday.equals(DB30Jobs.getPendingJobs().get(29)));//jobToday added to end
		
	}


	//getPendingJobs tests.

	@Test public void testGetPendingJobs1JobInPast4InFuture() {
		
		populateNJobsInTestDBSingleWeek(5, jobToday);		
		assertTrue(testDB.getPendingJobs().size() == 4 && allPending(testDB.getPendingJobs()));
	}
	
	



	@Test public void testGetPendingJobs30JobsInFuture() {			
		assertTrue(DB30Jobs.getPendingJobs().size() == 30 && allPending(testDB.getPendingJobs()));
	}
	
	@Test public void testGetPendingJobsOnlyPastJobs() {
		assertTrue(testDB.getPendingJobs().size() == 0);
	}
	
	
	private void generateTestDBs() {		
		populate30JobsDB();
		}


	private void populate30JobsDB() {
		DB30Jobs = new JobDB ();		
		for(int i = 0; i < 90; i+=3) {
			Job temp = new Job("Job #" + i, Calendar.getInstance(), Calendar.getInstance(), testPark, 1,1,1);
			temp.getStartDate().add(Calendar.DATE, i);
			temp.getEndDate().add(Calendar.DATE, i+1);
			DB30Jobs.addTestJob(temp);
		}

	}


	private void generateTestJobs() {		
		jobNextYear = new Job("Job too far into future", Calendar.getInstance(), Calendar.getInstance(), testPark, 1,1,1);
		jobNextYear.getStartDate().set(2017,  12,  12);
		jobNextYear.getEndDate().set(2017,  12,  13);

		jobInPast = new Job("Job in the past", Calendar.getInstance(), Calendar.getInstance(), testPark, 1,1,1);
		jobInPast.getStartDate().set(2016, 2, 1);
		jobInPast.getEndDate().set(2016, 2, 2);

		jobToday = new Job("Job today", Calendar.getInstance(), Calendar.getInstance(), testPark, 1,1,1);
		jobToday.getEndDate().add(Calendar.DATE, 1);//make job two days long
		
		
		jobNextWeek = new Job("Job today", Calendar.getInstance(), Calendar.getInstance(), testPark, 1,1,1);
		jobNextWeek.getStartDate().add(Calendar.DATE, 7);//make job two days long
		jobNextWeek.getEndDate().add(Calendar.DATE, 8);//make job two days long

		job91DaysAway  = new Job("Job in the past", Calendar.getInstance(), Calendar.getInstance(), testPark, 1,1,1);
		job91DaysAway.getStartDate().add(Calendar.DATE, 91);
		job91DaysAway.getEndDate().add(Calendar.DATE, 92);

		job90DaysAway  = new Job("Job in the past", Calendar.getInstance(), Calendar.getInstance(), testPark, 1,1,1);
		job90DaysAway.getStartDate().add(Calendar.DATE, 90);
		job90DaysAway.getEndDate().add(Calendar.DATE, 91);

	}
	
	
	private boolean allPending(ArrayList<Job> pendingJobs) {
		boolean result = true;
		Calendar today = Calendar.getInstance();
		today.add(Calendar.MINUTE, -1);//adjustment for jobs created instantly
		for(Job j : pendingJobs) {
			if(!j.getEndDate().after(today)) result = false;
		}
		return result;
	}
	
	
	private void populateNJobsInTestDBSingleWeek(int n, Job theJob) {
		for(int i = -2; i < n-2; i ++){
			Job temp = new Job("test job" + (i+2), (Calendar)theJob.getMyStartDate().clone(), (Calendar)theJob.getMyEndDate().clone(), testPark, 5 ,5 ,5);
			temp.getStartDate().add(Calendar.DATE, i);
			temp.getEndDate().add(Calendar.DATE, i);			
			testDB.addTestJob(temp);
		}

	}
	private void populateNJobsInTestDBSingleDay(int n, Job theJob) {
		for(int i = 0; i < n; i++){
			Job temp = new Job("test job" + i, (Calendar)theJob.getMyStartDate().clone(), (Calendar)theJob.getMyEndDate().clone(), testPark, 5 , 5 , 5);			
			testDB.addTestJob(temp);
		}

	}

}
