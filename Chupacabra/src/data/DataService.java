package data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public final class DataService {
	private static final String OUTPUT_FILENAME = "./backup.ups";
	
	
	
	private DataService(){
		
	}
	
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
