package users;

/** Specifies the Volunteer User, a specific kind of Urban Parks client.
 * Has several features catered to them specifically, but has the lowest level of information access.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
 *
 */
public class VolUser extends User {

	private static final long serialVersionUID = -2092614664954521778L;

	public VolUser(String theLName, String theFName, String theEmail){
		super(theLName,  theFName, theEmail);
		
	}
	
	

}
