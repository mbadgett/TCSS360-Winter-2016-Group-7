package users;

import java.util.ArrayList;
import java.util.Collection;

import data.Job;

/** Specifies the Volunteer User, a specific kind of Urban Parks client.
 * Has several features catered to them specifically, but has the lowest level of information access.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
 *
 */
public class VolUser extends AbstractUser {

	private static final long serialVersionUID = -2092614664954521778L;

	public VolUser(String theLName, String theFName, String theEmail){
		super(theLName,  theFName, theEmail);
		
	}
	
	public boolean equals(Object theObject){
		boolean rtn = false;
		if (super.equals(theObject)) {
			rtn = true;
		} else if (theObject.getClass() == VolUser.class) {
			VolUser v = (VolUser) theObject;
			if (v.getEmail().equals(this.getEmail())) rtn = true;
		}
		return rtn;
	}

	
//	public Collection<Job> getVolunteeredJobs(){
//		return new ArrayList<Job>();
//	}
//	//if circular relationship addJobrequired.... preference iteratively determine jobs availfrom jobsDB

}
