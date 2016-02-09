package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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
	public Collection<Job> jobList;
	
	
	/***/
	public JobDB(){
		jobList = new ArrayList<Job>();
	}


	/**Add job to the DB USER STORY 1
	 * @param theJob
	 */
	public void addJob(Job theJob){
		jobList.add(theJob);
	}

	/**Delete job from the db USER STORY 2
	 * @param theJob
	 * @return
	 */
	public boolean deleteJob(Job theJob){
		return jobList.remove(theJob);
	}

	/**will return all jobs, potentially for user story 4 and 11
	 * @return
	 */
	public Collection<Job> getAllJobs(){
		return jobList;
	}
	
	
	/**For the purpose of editing the details of a job USER STORY 3
	 * @return
	 */
	public Job getJob(){
		return null;
	}

	/**USER STORY 8
	 * @param theParkManager
	 * @return
	 */
	public Collection<Job> getParkManagerJobs(PMUser theParkManager){
		//iteratively cycle through the jobs check if Job myManager.equals(theParkManager)
		return null;
	}
	
	/**USER STORY 7
	 * @param theParkManager
	 * @return
	 */
	public Collection<Job> getVolunteerJobs(VolUser theVolunteer){
		//iteratively cycle through jobs searching for the user
		return null;
	}	

}
