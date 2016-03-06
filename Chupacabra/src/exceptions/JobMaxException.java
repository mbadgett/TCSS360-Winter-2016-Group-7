package exceptions;

/* Exception for the maximum number of jobs being reached in the database.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
*/
public class JobMaxException extends Exception{

	
	private static final long serialVersionUID = 3200566517927520133L;
	private static final String ERR_MSG = "Maximum number of jobs reached.";
	
	public JobMaxException(){
		super(ERR_MSG);
	}

}
