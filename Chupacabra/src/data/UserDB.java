package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import users.AbstractUser;
import users.VolUser;

/**Houses all the users and the needed functionality.
 * @author yattha
 *
 */
public class UserDB implements Serializable{
	/**For serialization.*/
	private static final long serialVersionUID = 3463062287214682923L;
	/**The actual collection of the users. MAKE SURE THE COLLECTION SUPPORTS SERIAALIZATION*/	
	private ArrayList<AbstractUser> myUsers;
	
	/***/
	public UserDB(){
		myUsers = new ArrayList<AbstractUser>();
	}
	
	public AbstractUser getUser(String theEmail){
		AbstractUser result = null;
		for(AbstractUser u: myUsers) if(u.getEmail().toLowerCase().equals(theEmail.toLowerCase()))result = u;
		return result;
	}
	
	
	public void addUser(AbstractUser theUser){
		myUsers.add(theUser);
	}
	
	
	/** Return all users, may be useful. could be superfluous.
	 * @return
	 */
	public ArrayList<VolUser> getVolunteers(){
		ArrayList<VolUser> rtn = new ArrayList<VolUser>();
		for (AbstractUser u: myUsers) {
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
