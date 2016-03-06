package exceptions;

/* Exception for a job that is too long being added to the database.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
*/
public class JobLengthException extends Exception{

	private static final String ERR_MSG = "Job length is invalid.";
	private static final long serialVersionUID = -4367247674216031658L;
	
	
	public JobLengthException(){
		super(ERR_MSG);
	}

}
