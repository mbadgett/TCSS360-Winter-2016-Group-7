package data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**Handles persistent data.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
 */
public final class DataService {
	/**Default file name for the backup file.	 */
	private static final String OUTPUT_FILENAME = "./backup.ups";
	
	
	
	/**Intended to be used statically.	 */
	private DataService(){
		
	}
	
	/**Loads the persistent data.
	 * @return A collection of the DB objects for users, parks, and jobs.
	 */
	public static ArrayList<Object> loadDBs(){
		ArrayList<Object> DBs = new ArrayList<Object>();
		try {
			ObjectInputStream inStream = new  ObjectInputStream(new FileInputStream(OUTPUT_FILENAME));
			DBs.add(inStream.readObject());
			DBs.add(inStream.readObject());
			DBs.add(inStream.readObject());
			inStream.close();			
			
		} catch (IOException | ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DBs;
	}	
	
	

	/** Writes the current state of the data out to the default file.
	 * @param jobs
	 * @param parks
	 * @param users
	 */
	public static void backup(JobDB jobs, ParkDB parks, UserDB users) {
		try {
			ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILENAME));
			outStream.writeObject(jobs);
			outStream.writeObject(parks);
			outStream.writeObject(users);
			outStream.flush();
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

}
