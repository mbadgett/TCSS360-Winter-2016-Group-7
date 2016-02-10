package users;

public class PMUser extends AbstractUser {

	private static final long serialVersionUID = 564005197077434081L;

	public PMUser(String theLName, String theFName, String theEmail){
		super(theLName,  theFName, theEmail);
		
	}
	
	public boolean equals(Object theObject){
		boolean rtn = false;
		if (super.equals(theObject)) {
			rtn = true;
		} else if (theObject.getClass() == PMUser.class) {
			PMUser pm = (PMUser) theObject;
			if (pm.getEmail().equals(this.getEmail())) rtn = true;
		}
		return rtn;
	}

}
