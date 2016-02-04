package data;

import java.io.Serializable;
import java.util.Collection;

import users.PMUser;

/**Park will only contain manager and info.
 * @author yattha
 *
 */
class Park implements Serializable{
	/**For serialization.*/
	private static final long serialVersionUID = -4004677218782934433L;
	/**The info*/
	private String myName, myAddress;
	/**Manager*/
	private PMUser myManager;	
	
	public Park(String theName, String theAddress, PMUser theManager){
		myName = theName;
		myAddress = theAddress;
		myManager = theManager;
	}
	
	
	/* (non-Javadoc) must be writted to enable searching
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object theObject){
		return false;
	}
	
	/* (non-Javadoc) written since we're rewriting equals
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode(){
		return 1;
	}
	
	
	/* (non-Javadoc) FOR CONSOLE DISPLAY>
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		//REWRITE ME DAMN IT
		return "";
	}
	
	
	
}