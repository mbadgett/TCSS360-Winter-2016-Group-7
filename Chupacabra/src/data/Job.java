package data;

import java.io.Serializable;
import java.util.Date;

import users.VolUser;

public class Job implements Serializable{

	private static final long serialVersionUID = 8142246115096145662L;
	private String myDescription;
	private Date myDate;
	private Park myPark;
	//some collection to handle volunteers for this job
	
	public Job(String theDesc, Date theDate, Park thePark){
		myDescription = theDesc;
		myDate = theDate;
		myPark = thePark;
	}
	
	public void addVolunteer(VolUser theVol){
		
	}
}
