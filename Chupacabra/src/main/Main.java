package main;

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
		//CONSOLE SHIT HERE.... backupDB() at termination of program.
		
	}

	private static void initDB() {
		JobDB jobs = DataService.loadJobs();
		ParkDB parks = DataService.loadParks();
		UserDB users = DataService.loadUsers();
		
	}
	
	private static void backupDB() {
		DataService.backup(jobs, parks, users);
	}

}
