package main;

import java.util.ArrayList;

import data.DataService;
import data.JobDB;
import data.ParkDB;
import data.UserDB;

public class Main {
	static JobDB jobs;
	static ParkDB parks;
	static UserDB users;
	
	public static void main(String[] args) {
		initDB();		
		
		//backupDB();
	}

	private static void initDB() {
		ArrayList<Object> DBs = DataService.loadDBs();
		jobs = (JobDB) DBs.get(0);
		parks = (ParkDB) DBs.get(1);
		users = (UserDB) DBs.get(2);				
	}
	
	private static void backupDB() {
		DataService.backup(jobs, parks, users);
	}
	
	
	

}
