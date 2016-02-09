package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import users.AbstractUser;
import users.PMUser;


/**Houses a collection of all the parks and needed functionality.
* @author yattha
*
*/
public class ParkDB implements Serializable{
	/**For serialization.*/
	private static final long serialVersionUID = -6787742603286676244L;
	/**The actual collection of parks.. MAKE SURE THE COLLECTION SUPPORTS SERIAALIZATION*/
	private Collection<Park> myParks;		
	
	public ParkDB(){
		myParks = new ArrayList<Park>();
	}
	
	/***/
	public void addPark(Park thePark){
		myParks.add(thePark);
	}		
	
	//MAY NOT BE NECCESSARY (no need to list all parks as far as i can see).
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		//REWRITE ME DAMN IT
		return "";
	}
	

}
