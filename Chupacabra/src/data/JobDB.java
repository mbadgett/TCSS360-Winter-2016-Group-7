package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import exceptions.JobFutureException;
import exceptions.JobLengthException;
import exceptions.JobMaxException;
import exceptions.JobPastException;
import exceptions.JobsInWeekException;
import users.PMUser;
import users.VolUser;

/**Houses a collection of all the jobs and needed functionality.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
 */
public class JobDB implements Serializable {
	/**For serialization.*/
	private static final long serialVersionUID = -2757438807790008719L;
	/**The actual collection of jobs. MAKE SURE THE COLLECTION SUPPORTS SERIAALIZATION*/
	private ArrayList<Job> myJobs;


	/***/
	public JobDB(){
		myJobs = new ArrayList<Job>();
	}





	/**Delete job from the db USER STORY 2
	 * @param theJob
	 * @return
	 */
	public boolean deleteJob(Job theJob){
		return myJobs.remove(theJob);
	}

	/**will return all jobs, potentially for user story 4 and 11
	 * @return
	 */
	public ArrayList<Job> getPendingJobs(){
		ArrayList<Job> pendingJobs = new ArrayList<Job>();
		for(Job j:myJobs){
			if(j.getEndDate().after(Calendar.getInstance().getTime()) && within90(j))pendingJobs.add(j);
		}		
		return myJobs;
	}


	//	/**For the purpose of editing the details of a job USER STORY 3
	//	 * @return
	//	 */
	//	public Job getJob(){
	//		return null;
	//	}

	/**USER STORY 8
	 * @param theParkManager
	 * @return
	 */
	public ArrayList<Job> getParkManagerJobs(PMUser theParkManager){
		ArrayList<Job> result = new ArrayList<Job>();
		for(Job j : myJobs){
			if(j.getPark().getManager().equals(theParkManager)){
				result.add(j);
			}
		}
		//iteratively cycle through the jobs check if Job myManager.equals(theParkManager)
		return result;
	}

	/**USER STORY 7
	 * @param theParkManager
	 * @return
	 */
	public ArrayList<Job> getVolunteerJobs(VolUser theVolunteer){
		ArrayList<Job> result = new ArrayList<Job>();
		for(Job j : myJobs){
			for(VolUser vol : j.getVolunteers()){
				if(vol.equals(theVolunteer))result.add(j);
			}
		}
		//iteratively cycle through the jobs check if Job myManager.equals(theParkManager)
		return result;
	}


	public ArrayList<Job> getParkJobs(Park thePark) {
		ArrayList<Job> result = new ArrayList<Job>();
		for(Job j: myJobs){
			if(j.getPark().equals(thePark))result.add(j);
		}
		return result;
	}

	public boolean canVolunteer(Job theJob, VolUser theVolunteer){
		boolean result = true;
		for(Job j : myJobs){
			if(!j.equals(theJob) && j.getVolunteers().contains(theVolunteer) && (theJob.getStartDate().equals(j.getStartDate()) 
					|| theJob.getStartDate().equals(j.getEndDate())
					|| theJob.getEndDate().equals(j.getStartDate())
					|| theJob.getEndDate().equals(j.getEndDate()))
					|| theJob.getVolunteers().contains(theVolunteer))result = false;
		}//Jobs may only last two days 4 possible violations of same day rule, no time so dates = on given days.		
		return result;
	}

	/**Add job to the DB USER STORY 1
	 * @param theJob
	 * @throws JobMaxException 
	 * @throws DuplicateJobException 
	 * @throws JobFutureException 
	 * @throws JobPastException 
	 * @throws JobsInWeekException 
	 * @throws JobLengthException 
	 */
	public void addJob(Job theJob) throws JobMaxException, JobFutureException, JobPastException, JobsInWeekException, JobLengthException{			
		checkJobLength(theJob);
		checkWeekCapacity(theJob);
		checkInFuture(theJob);
		checkRecency(theJob);
		checkScheduleVacancy();		
		myJobs.add(theJob);
	}

	protected void checkScheduleVacancy() throws JobMaxException {
		if(getPendingJobs().size() >= 30) throw new JobMaxException();

	}


	protected void checkRecency(Job theJob) throws JobFutureException {
		if(!within90(theJob)) throw new JobFutureException();

	}


	protected void checkInFuture(Job theJob) throws JobPastException {
		if(theJob.getStartDate().before(Calendar.getInstance())) throw new JobPastException();

	}


	protected void checkWeekCapacity(Job theJob) throws JobsInWeekException {
		if(jobsInWeek(theJob)>4) throw new JobsInWeekException();

	}


	protected void checkJobLength(Job theJob) throws JobLengthException {
		if(theJob.getJobLength()>1 || theJob.getJobLength() < 0) throw new JobLengthException();

	}


	protected boolean within90(Job theJob) {
		boolean result = false;
		long today = Calendar.getInstance().getTimeInMillis();		
		long jobsTime = theJob.getStartDate().getTimeInMillis();
		result = ((jobsTime-today )- 90*TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)) < 0;		
		return result;
	}


	protected int jobsInWeek(Job theJob) {
		int i = 0;
		for(Job j : getPendingJobs()){
			if(Math.abs(j.getStartDate().getTimeInMillis()-theJob.getStartDate().getTimeInMillis()) <= 3*(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)))i++;
		}
		return i;
	}

	protected void addTestJob(Job theJob) {
		myJobs.add(theJob);
	}

}
