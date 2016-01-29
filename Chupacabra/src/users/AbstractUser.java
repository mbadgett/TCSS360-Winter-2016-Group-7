package users;

import java.io.Serializable;

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
	

}
