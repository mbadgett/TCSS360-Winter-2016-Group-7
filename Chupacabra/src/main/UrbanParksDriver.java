package main;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import data.DataService;
import data.Job;
import data.JobDB;
import data.Park;
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
		
		//populateData();//comment in to repopulate data
		
		initDB();//comment out for repopulate
	}
	
	//for initial population of data
	private void populateData() {		
		users.addUser(new UPSUser("Moore", "Jessica", "j.moore@urbanparks.org"));
		users.addUser(new UPSUser("user","ups","ups@mail.com"));//Generic		
		//some duplicate last names for searching.
		users.addUser(new VolUser("Parsons","Gustavo","g.parsons@mail.com"));
		users.addUser(new VolUser("Parsons","Jessie","j.parson@mail.com"));
		users.addUser(new VolUser("Welch","Clayton","c.welch@mail.com"));
		users.addUser(new VolUser("Hill","Jean","j.hill@mail.com"));
		users.addUser(new VolUser("Hill","Randal","r.hill@mail.com"));
		users.addUser(new VolUser("Hill","Cleetus","c.hill@mail.com"));
		users.addUser(new VolUser("Hill","Ian","i.hill@mail.com"));
		users.addUser(new VolUser("Flemming","Norman","n.flemming@mail.com"));
		users.addUser(new VolUser("Tran","Dawn","d.tran@mail.com"));
		users.addUser(new VolUser("Smith","Hubert","h.smith@mail.com"));
		users.addUser(new VolUser("user","volunteer","volunteer@mail.com"));//Generic
		
		
		PMUser manager1 = new PMUser("Schultz", "Terence", "t.schultz@tacomaparks.gov");
		users.addUser(manager1);
		PMUser manager2 = new PMUser("Wilkerson", "Alberto", "w.alberto@seattleparks.gov");
		users.addUser(manager2);
		PMUser manager3 = new PMUser("Aguilar", "Lucia", "l.aguilar@olympiaparks.gov");
		users.addUser(manager3);
		
		parks.addPark(new Park("Wright Park", "Tacoma", manager1));
		parks.addPark(new Park("AlderWood Park", "Tacoma", manager1));
		parks.addPark(new Park("Ballard Commons Park", "Seattle", manager2));
		parks.addPark(new Park("Greenwood Park", "Seattle", manager2));
		
		parks.addPark(new Park("Friendly Grove Park", "Olympia", manager3));
		backupDB();		
	}

	public void run(){
		boolean programContinue = true;
		initDB();		
		Scanner scan = new Scanner(System.in);
		
		while(programContinue){
			String email ="";
			System.out.println("Welcome to Urban Park.");
			
			while(Objects.isNull(users.getUser(email))){
				
				System.out.print("Please enter your email:");
				email = scan.nextLine();
				nextScreen();
				if(Objects.isNull(users.getUser(email)))System.out.println("User not found....");
			}
			myCurrentUser = users.getUser(email);			

			if(myCurrentUser instanceof VolUser) {
				new VolunteerDriver(this).displayVolInterface(scan);				
				scan.nextLine();
			} else if (myCurrentUser instanceof PMUser) {
				new ParkManagerDriver(this).displayVolInterface(scan);				
				scan.nextLine();
			} else if (myCurrentUser instanceof UPSUser) {
				new UPStaffDriver(this).displayVolInterface(scan);				
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
		for(Job curJob : jobs.getPendingJobs()){
			System.out.println(i++ +". "+ curJob.listingToString());
		}	
	}
}
