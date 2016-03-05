package users;

import java.io.Serializable;



/**Abstract user houses common functionality.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
 */
public abstract class AbstractUser implements Serializable{
	private static final long serialVersionUID = 2369082327791998209L;
	private String email, lName, fName;
	
	
	public AbstractUser(String theLName, String theFName, String theEmail){
		this.lName = theLName;
		this.fName = theFName;
		setEmail(theEmail);
	}

	public String getName() {
		return lName;
	}
	
	public void setLName(String name) {
		this.lName = name;
	}

	public void setFName(String name) {
		this.fName = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	/**The purpose is for console display
	 * */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(fName);
		sb.append(' ');
		sb.append(lName);
		sb.append(" - ");
		sb.append(email);
		return sb.toString();
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
	

}
