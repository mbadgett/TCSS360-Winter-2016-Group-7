package data;

import java.io.Serializable;
import java.util.ArrayList;

import users.User;
import users.VolUser;

/**Houses all the users and the needed functionality.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
 */
public class UserDB implements Serializable{
	/**For serialization.*/
	private static final long serialVersionUID = 3463062287214682923L;
	/**The actual collection of the users. MAKE SURE THE COLLECTION SUPPORTS SERIAALIZATION*/	
	private ArrayList<User> myUsers;
	
	/***/
	public UserDB(){
		myUsers = new ArrayList<User>();
	}
	
	public User getUser(String theEmail){
		User result = null;
		for(User u: myUsers) if(u.getEmail().toLowerCase().equals(theEmail.toLowerCase()))result = u;
		return result;
	}
	
	
	public void addUser(User theUser){
		myUsers.add(theUser);
	}
	
	
	/** Return all users, may be useful. could be superfluous.
	 * @return
	 */
	public ArrayList<VolUser> getVolunteers(){
		ArrayList<VolUser> rtn = new ArrayList<VolUser>();
		for (User u: myUsers) {
			if (u.getClass() == VolUser.class) rtn.add((VolUser) u);
		}
		return rtn;
	}
	
	/**User story 10
	 * @param theLName
	 * @return
	 */
	public ArrayList<VolUser> searchByLName(String theLName){
		ArrayList<VolUser> rtn = new ArrayList<VolUser>();
		for (VolUser v : getVolunteers())  {
			if (v.getName().toLowerCase().equals(theLName.toLowerCase())) {
				rtn.add(v);
			}
		}
		
		return rtn;		
	}
	
	
	/**The purpose is for console display
	 * */
	public String toString(){
		//REWRITE ME DAMN IT
		return "";
	}
}
