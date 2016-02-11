package main;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import data.DataService;
import data.Job;
import data.JobDB;
import data.ParkDB;
import data.UserDB;
import users.AbstractUser;
import users.PMUser;
import users.UPSUser;
import users.VolUser;

public class UrbanParksDriver {
	JobDB jobs;
	ParkDB parks;
	UserDB users;
	AbstractUser myCurrentUser;
	
	UrbanParksDriver() {		
		jobs = new JobDB();
		parks = new ParkDB();
		users = new UserDB();
		initDB();
	}
	
	public void run(){
		boolean programContinue = true;
		initDB();		
		Scanner scan = new Scanner(System.in);
		String email="";
		while(programContinue){
			System.out.println("Welcome to Urban Park.");
			
			while(Objects.isNull(users.getUser(email))){
				
				System.out.print("Please enter your email:");
				email = scan.nextLine();
				nextScreen();
				if(Objects.isNull(users.getUser(email)))System.out.println("User not found....");
			}
			myCurrentUser = users.getUser(email);
			
			
//			//The following code should be replace by code to determine the user type.
//			if(email.equals("q")){
//				programContinue = false;
//			} else 
			if(myCurrentUser instanceof VolUser) {
				new VolunteerDriver(this).displayVolInterface(scan);
				
				//Volunteer(scan);
				//Clear buffer - I don't know the reason why but this code make it works :)
				scan.nextLine();
			} else if (myCurrentUser instanceof PMUser) {
				new ParkManagerDriver(this).displayVolInterface(scan);
				//ParkManager(scan);
				scan.nextLine();
			} else if (myCurrentUser instanceof UPSUser) {
				new UPStaffDriver(this).displayVolInterface(scan);
				
				//Staff(scan);
				scan.nextLine();
			} 
			 
		}
		scan.close();
		System.out.println("Thank you for using the program! Good bye.");
		System.exit(0);
	}
	
	
	private void initDB() {
		ArrayList<Object> DBs = DataService.loadDBs();
		jobs = (JobDB) DBs.get(0);
		parks = (ParkDB) DBs.get(1);
		users = (UserDB) DBs.get(2);				
	}
	
	
	private void backupDB() {
		DataService.backup(jobs, parks, users);
	}
	
	public void nextScreen() {
		System.out.println();
		System.out.println();
		System.out.println("===============================================================================");
		System.out.println();
		System.out.println();
		
	}
	
	public void quit() {
		System.out.println("Good Bye.....");
		backupDB();
		System.exit(0);
		
	}
	
	public void ViewSummaryAllUpComingJobs() {
		int i = 1;
		System.out.println("Jobs...");
		for(Job curJob : jobs.getAllJobs()){
			System.out.println(i++ +". "+ curJob);
		}
		
		
	}
}
