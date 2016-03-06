package exceptions;

/* Exception for too many jobs in the week being added to the database.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
*/
public class JobsInWeekException extends Exception{

	private static final String ERR_MSG = "Too many jobs in week.";
	private static final long serialVersionUID = -4367247674216031658L;
	
	
	public JobsInWeekException(){
		super(ERR_MSG);
	}

}
