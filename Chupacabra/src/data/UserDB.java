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
	private Collection<AbstractUser> myUsers;
	
	/***/
	public void addUser(AbstractUser theUser){
		myU
	}
	
	
	/** Retrun all users, may be useful. could be superfluous.
	 * @return
	 */
	public Collection<VolUser> getVolunteers(){
		return new ArrayList<VolUser>();
	}
	
	/**User story 10
	 * @param theLName
	 * @return
	 */
	public Collection<VolUser> searchByLName(String theLName){
		//must also isolate VolUsers...
		return null;		
	}
	
	
	/**The purpose is for console display
	 * */
	public String toString(){
		//REWRITE ME DAMN IT
		return "";
	}
}
