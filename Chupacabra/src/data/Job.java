package data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import users.VolUser;

/** Job will contain references to it's park and the volunteers for the job as well as its info.
 * @author yattha
 *
 */
public class Job implements Serializable{
	/**For serialization.*/
	private static final long serialVersionUID = 8142246115096145662L;
	/**The volunteers that are signed up for this job.*/
	private Collection<VolUser> myVolunteers;
	/**The park at which this job is located.*/
	private Park myPark;
	/**The details of a job.*/
	private String myDescription;
	/**The date of the job.*/
	private Date myDate;

	
	/**Constructor.
	 * @param theDesc
	 * @param theDate
	 * @param thePark
	 */
	public Job(String theDesc, Date theDate, Park thePark){
		myDescription = theDesc;
		myDate = theDate;
	}
	
	/** USER STORY 6
	 * @param theVol
	 */
	public void addVolunteer(VolUser theVol){
		myVolunteers.add(theVol);
	}
	
	/* (non-Javadoc) must be writted to enable searching
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object theObject){
		return false;
	}	
	
	/**USER STORY 9 in combination with 
	 * @return
	 */
	public Collection<VolUser> getVolunteers() {
		return myVolunteers;
	}
	
	/* (non-Javadoc) written since we're rewriting equals
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode(){
		return 1;
	}
	
	//potentially replace this getDescription method
	/**The purpose is for console display USER STORY 5 and 12
	 * */
	public String toString(){
		//REWRITE ME DAMN IT
		return "";
	}
}
