package data;

import java.io.Serializable;

public class Job implements Serializable{

	private static final long serialVersionUID = 8142246115096145662L;
	public String description;
	
	public Job(String theDesc){
		description = theDesc;
	}
}
