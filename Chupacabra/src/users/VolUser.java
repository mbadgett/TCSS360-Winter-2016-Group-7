package users;

import java.util.ArrayList;
import java.util.Collection;

import data.Job;

public class VolUser extends AbstractUser {

	private static final long serialVersionUID = -2092614664954521778L;

	public VolUser(String theLName, String theFName, String theEmail){
		super(theLName,  theFName, theEmail);
		
	}
	
	public Collection<Job> getVolunteeredJobs(){
		return new ArrayList<Job>();
	}
	//if circular relationship addJobrequired.... preference iteratively determine jobs availfrom jobsDB

}
