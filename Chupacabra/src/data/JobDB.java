package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import users.PMUser;
import users.VolUser;

/**Houses a collection of all the jobs and needed functionality.
 * @author yattha
 *
 */
public class JobDB implements Serializable {
	/**For serialization.*/
	private static final long serialVersionUID = -2757438807790008719L;
	/**The actual collection of jobs. MAKE SURE THE COLLECTION SUPPORTS SERIAALIZATION*/
	public ArrayList<Job> myJobs;
	
	
	/***/
	public JobDB(){
		myJobs = new ArrayList<Job>();
	}


	/**Add job to the DB USER STORY 1
	 * @param theJob
	 */
	public String addJob(Job theJob){
		String result= "";
		if(theJob.getJobLength()>1 || theJob.getJobLength() < 0) result = "Job length is invalid.";//
		else if(jobsInWeek(theJob)>4)result = "Too many jobs in week.";
		else if(theJob.getStartDate().before(Calendar.getInstance().getTime())) result = "Job must be in the future.";//
		else if(!within90(theJob)) result = "Job must be within 90 days.";
		else if(getPendingJobs().contains(theJob)) result = "Job already exists."; //
		else if(getPendingJobs().size() >= 30){
			result="Maximum number of jobs reached.";
		} else {
			myJobs.add(theJob);
			result="Job added.";//
		}
		
		return result;
	}

	public boolean within90(Job theJob) {
		boolean result = false;
		long today = Calendar.getInstance().getTime().getTime();
		
		long jobsTime = theJob.getStartDate().getTime();
		result = ((jobsTime-today )- 90*TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)) < 0;		
		return result;
	}


	public int jobsInWeek(Job theJob) {
		int i = 0;
		for(Job j : getPendingJobs()){
			if(Math.abs(j.getStartDate().getTime()-theJob.getStartDate().getTime()) <= 3*(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)))i++;
		}
		return i;
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
			if(j.getStartDate().after(Calendar.getInstance().getTime()))pendingJobs.add(j);
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

}
