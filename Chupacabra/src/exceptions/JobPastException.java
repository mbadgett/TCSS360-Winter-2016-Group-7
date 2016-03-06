package exceptions;

/* Exception for an expired job being added to the database.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
*/
public class JobPastException extends Exception{

	
	private static final long serialVersionUID = -114864713456386274L;
	private static final String ERR_MSG = "Job must be in the Future.";
	
	public JobPastException(){
		super(ERR_MSG);
	}

}
