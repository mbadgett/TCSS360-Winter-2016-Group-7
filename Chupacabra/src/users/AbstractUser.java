package users;

import java.io.Serializable;

/**Abstract useer houses common functionality.
 * @author yattha
 *
 */
public abstract class AbstractUser implements Serializable{
	private static final long serialVersionUID = 2369082327791998209L;
	private String name, email;
	
	
	public AbstractUser(String theName, String theEmail){
		setName(theName);
		setEmail(theEmail);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		//REWRITE ME DAMN IT
		return "";
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
