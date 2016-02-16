package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import users.VolUser;

/** Job will contain references to it's park and the volunteers for the job as well as its info.
 * @author yattha
 *
 */
public class Job implements Serializable{
	/**For serialization.*/
	private static final long serialVersionUID = 8142246115096145662L;
	/*list within a list, categories, maximums will be applied index
	 * 0 light, 1 medium, 2 heavy
	 */
	private ArrayList<VolUser> myVolunteers;
	/**The park at which this job is located.*/
	private Park myPark;
	/**The details of a job.*/
	private String myDescription;
	/**The date of the job.*/
	private Date myStartDate, myEndDate;
	private int lMax, mMax, hMax, lCount, mCount, hCount;

	
	/**Constructor.
	 * @param theDesc
	 * @param theDate
	 * @param thePark
	 */
	public Job(String theDesc, Date theStartDate, Date theEndDate, Park thePark, int light, int med, int heavy){
		myDescription = theDesc;
		myStartDate = theStartDate;
		myEndDate = theEndDate;
		myPark = thePark;
		myVolunteers = new ArrayList<VolUser>();
		lMax = light;
		lCount = 0;
		mMax = med;
		mCount = 0;
		hMax = heavy;
		hCount = 0;
	}
	//returns days of job length
	public long getJobLength(){
		return (myEndDate.getTime() - myStartDate.getTime())/TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);
	}
	
	public Date getStartDate(){
		return myStartDate;
	}
	public Date getEndDate(){
		return myEndDate;
	}
	
	public Park getPark(){
		return myPark;
	}
	
	/** USER STORY 6
	 * @param theVol
	 */
	public String addVolunteer(VolUser theVol, int theIntensity){
		String result = "Could not sign up for job.";
		if (!myVolunteers.contains(theVol)) {
			if (theIntensity == 1 && lCount < lMax) {
				myVolunteers.add(theVol);
				lCount++;
				result = "Signed up for Job, light intensity";
			} else if (theIntensity == 2 && mCount < mMax) {
				myVolunteers.add(theVol);
				mCount++;
				result = "Signed up for Job, medium intensity";
			} else if (theIntensity == 3 && hCount < hMax) {
				myVolunteers.add(theVol);
				hCount++;
				result = "Signed up for Job, heavy intensity";
			} 
		}
		return result;
		
	}
	
	/* (non-Javadoc) must be written to enable searching
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object theObject){
		boolean rtn = false;
		if (super.equals(theObject)) {
			rtn = true;
		} else if (theObject.getClass() == Job.class) {
			Job j = (Job) theObject;
			if (j.myStartDate.equals(myStartDate) && j.myDescription.equals(myDescription) &&
					j.myPark.equals(myPark)){
				rtn = true;
			}
		}
		return rtn;
	}	
	
	/**USER STORY 9 in combination with 
	 * @return
	 */
	public ArrayList<VolUser> getVolunteers() {
		return myVolunteers;
	}
	
	/* (non-Javadoc) written since we're rewriting equals
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode(){
		return this.toString().hashCode();
	}
	
	//potentially replace this getDescription method
	/**The purpose is for console display USER STORY 5 and 12
	 * */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(myPark);
		sb.append('(');
		sb.append(myStartDate);
		sb.append('-');
		sb.append(myEndDate);
		sb.append("): ");
		sb.append(myDescription);
		return sb.toString();
	}
	
	public String displayOpenings(){
		StringBuilder sb = new StringBuilder();
		if(lMax>lCount)sb.append("1. Light: "+(lMax-lCount)+" Spots remaining\n");
		if(mMax>mCount)sb.append("2. Medium: "+(mMax-mCount)+" Spots remaining\n");
		if(hMax>hCount)sb.append("3. Heavy: "+(hMax-hCount)+" Spots remaining\n");		
		return sb.toString();
	}
	
	public String displayVolunteerInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("Light: "+lCount+"/"+lMax+" Spots filled.\n");
		sb.append("Medium: "+mCount+"/"+mMax+" Spots filled.\n");
		sb.append("Heavy: "+hCount+"/"+hMax+" Spots filled.\n");		
		return sb.toString();
	}

	public boolean hasAvailableSpot(int theIntensity) {
		boolean result = false;
		if(theIntensity==1 && lCount < lMax){			
			result=true;
		} else if(theIntensity==2 && mCount < mMax){			
			result=true;
		}else if(theIntensity==3 && hCount < hMax){			
			result=true;
		} 
		return result;
	}
	@SuppressWarnings("deprecation")
	public String listingToString() {
		return myPark.getName() + " (" + (myStartDate.getMonth()+1) + "/" + myStartDate.getDate() + "-" + myEndDate.getDate()
		+ "): " + myDescription.substring(0, Math.max(0, Math.min(20, myDescription.length()-4))) + "...";
	}
}
