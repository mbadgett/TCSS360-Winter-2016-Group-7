package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import users.AbstractUser;
import users.VolUser;

public class UserDB implements Serializable{

	
	private Collection<AbstractUser> myUsers;
	private static final long serialVersionUID = 3463062287214682923L;
	//collection of users
	
	public void addUser(AbstractUser theUser){
		
	}
	
	public Collection<VolUser> getVolunteers(){
		return new ArrayList<VolUser>();
	}
}
