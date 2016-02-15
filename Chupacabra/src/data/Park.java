package data;

import java.io.Serializable;
import users.PMUser;

/**Park will only contain manager and info.
 * @author yattha
 *
 */
public class Park implements Serializable{
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
	
	public PMUser getManager(){
		return myManager;
	}
	
	public String getName(){
		return myName;
	}
	
	
	/* (non-Javadoc) must be writted to enable searching
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object theObject){
		boolean rtn = false;
		if (super.equals(theObject)) {
			rtn = true;
		} else if (theObject.getClass() == Park.class) {
			Park j = (Park) theObject;
			if (j.myName.equals(myName) && j.myAddress.equals(myAddress) &&
					j.myManager.equals(myManager)){
				rtn = true;
			}
		}
		return rtn;
	}
	
	/* (non-Javadoc) written since we're rewriting equals
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode(){
		return toString().hashCode();
	}
	
	
	/* (non-Javadoc) FOR CONSOLE DISPLAY>
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(myName);
		sb.append(" (");
		sb.append(myAddress);		
		sb.append(") ");		
		return sb.toString();
	}
	
	
	
}