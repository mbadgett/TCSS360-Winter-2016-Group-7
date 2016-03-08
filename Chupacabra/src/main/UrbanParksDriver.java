package main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

import data.DataService;
import data.Job;
import data.JobDB;
import data.Park;
import data.ParkDB;
import data.UserDB;
import exceptions.JobFutureException;
import exceptions.JobLengthException;
import exceptions.JobMaxException;
import exceptions.JobPastException;
import exceptions.JobsInWeekException;
import users.User;
import users.PMUser;
import users.UPSUser;
import users.VolUser;

/**Basic console display class that all positions draw from.
 * @author Derek Moore
 * @author Ian Cresse
 * @author Son Vu
 * @author Michael Badgett
*/
public class UrbanParksDriver {
	JobDB jobs;
	ParkDB parks;
	UserDB users;
	User myCurrentUser;
	
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

	public void run() {
		boolean programContinue = true;
		initDB();		
		Scanner scan = new Scanner(System.in);
		
		while(programContinue){
			String email ="";
			System.out.println("Welcome to Urban Parks!");
			
			while(Objects.isNull(users.getUser(email))){
				
				System.out.print("Please enter your email:");
				email = scan.nextLine();
				//nextScreen();
				if(Objects.isNull(users.getUser(email)))System.out.println("User not found.");
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
		System.out.println("Good bye.");
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
	
//	public void nextScreen() {
//		System.out.println();
//		System.out.println();
//		System.out.println("===============================================================================");
//		System.out.println();
//		System.out.println();
//		
//	}
	
	public void quit() {
		System.out.println("\n================================\nGood Bye.....");
		backupDB();
		System.exit(0);
		
	}
	
	public void ViewSummaryAllUpComingJobs() {
		int i = 1;
		System.out.println("Jobs...");
		for(Job curJob : jobs.getPendingJobs()){
			System.out.println(i++ +". "+ listingToString(curJob));
		}	
	}
	
	public String listingToString(Job curJob) {
		return curJob.getMyPark().getName() + " (" + (curJob.getMyStartDate().get(Calendar.MONTH)+1) + "/" 
					+ curJob.getMyStartDate().get(Calendar.DATE) + "-" + curJob.getMyEndDate().get(Calendar.DATE)
					+ "): " + curJob.getMyDescription().substring(0, Math.max(0, Math.min(20, curJob.getMyDescription().length()-4))) + "...";
	}
	
	public String displayOpenings(Job curJob){
		StringBuilder sb = new StringBuilder();
		if(curJob.getlMax()>curJob.getlCount())sb.append("1. Light: "+(curJob.getlMax()-curJob.getlCount())+" Spots remaining\n");
		if(curJob.getmMax()>curJob.getmCount())sb.append("2. Medium: "+(curJob.getmMax()-curJob.getmCount())+" Spots remaining\n");
		if(curJob.gethMax()>curJob.gethCount())sb.append("3. Heavy: "+(curJob.gethMax()-curJob.gethCount())+" Spots remaining\n");		
		return sb.toString();
	}	

	public String displayVolunteerInfo(Job curJob) {
		StringBuilder sb = new StringBuilder();
		sb.append("Light: "+curJob.getlCount()+"/"+curJob.getlMax()+" spots filled.\n");
		sb.append("Medium: "+curJob.getmCount()+"/"+curJob.getmMax()+" spots filled.\n");
		sb.append("Heavy: "+curJob.gethCount()+"/"+curJob.gethMax()+" spots filled.\n");		
		return sb.toString();
	}
	
	//Pass the abstract user and the menu name to this method to print the header
	public void displayHeader(User theUser, String theMenu){
		System.out.println("\n\n++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("            Urban Parks                   ");
		System.out.println("            "+theUser.getName()+"          ");
		if(theUser instanceof PMUser)
			System.out.println("            Park Manager                   ");
		else if( theUser instanceof UPSUser)
			System.out.println("            Urban Park Staff                   ");
		else if( theUser instanceof VolUser)
			System.out.println("            Volunteer                   ");
		
		System.out.println("----------- "+theMenu+" ----------------\n");
		
	}
	
	//pass the list of menu option as an arrayList to this method to print the menu
	//Must be put in the array in order. the number for user to choose should be included in the array.
	public void displayMenu(ArrayList<String> theMenuList){
		for(int i = 0; i<theMenuList.size();i++) {
			System.out.println(theMenuList.get(i));
		}
		System.out.print("Please enter your choice:");
	}
	
	//change Date object to Calendar object to be consistent with addJob method.
	public Calendar dateToCalendar(Date theDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		return cal;
	}
	
	//Error handler from front end
	public void errorHandle(Exception ex){
		if( ex instanceof JobFutureException)
			System.out.println(new JobFutureException().getMessage());
		else if(ex instanceof JobLengthException)
			System.out.println(new JobLengthException().getMessage());
		else if(ex instanceof JobMaxException)
			System.out.println(new JobMaxException().getMessage());
		else if(ex instanceof JobPastException)
			System.out.println(new JobPastException().getMessage());
		else if(ex instanceof JobsInWeekException)
			System.out.println(new JobsInWeekException().getMessage());
	}
	
	public User getAbstractUser(){
		return myCurrentUser;
	}
	
	/**
	 * this method gets an integer value from the console. method will not close until an integer value
	 * between the given min and max (both inclusive) is entered, or the optional escape sequence is entered.
	 * 
	 * @param scan A Scanner used to get console input
	 * @param min minimum int value option inclusive
	 * @param max maximum int value option inclusive
	 * @param message optional error message to display when wrong input received.
	 * @param returnC optional return character to break the input cycle.
	 *        optional return has unexpected behavior if min is less than zero.
	 * @return returns an positive int for any option that the user selects. negative one returned
	 * 		   when optional character is read in from the console.
	 */
	public int userMenuSelection(Scanner scan, int min, int max, String message, String returnC) {
		int userSelection;
		String checkRtn;
		do {
			if (scan.hasNextInt()) {
				userSelection = scan.nextInt();
			} else {
				checkRtn = scan.next();
				userSelection = -1;
				if (checkRtn.equals(returnC)) break;
				if (message != null && !message.isEmpty())System.out.println(message);
			}
		} while (userSelection < min || userSelection > max);
		return userSelection;
	}
}
