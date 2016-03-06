package exceptions;

/* Exception for a Job that's in the future.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
*/
public class JobFutureException extends Exception{
	
	private static final long serialVersionUID = 5155498575822417380L;
	private static final String ERR_MSG = "Job must be within 90 days.";
	
	public JobFutureException(){
		super(ERR_MSG);
	}
}
