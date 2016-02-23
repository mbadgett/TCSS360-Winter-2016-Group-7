package exceptions;

public class DuplicateJobException extends Exception{

	
	private static final long serialVersionUID = 6838302904542134448L;
	private static final String ERR_MSG = "That job already exists.";
	
	public DuplicateJobException() {
		super(ERR_MSG);
	}

}
