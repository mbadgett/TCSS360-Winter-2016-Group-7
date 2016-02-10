package main;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

import javax.print.DocFlavor.INPUT_STREAM;

import users.AbstractUser;
import users.PMUser;
import users.UPSUser;
import users.VolUser;
import data.DataService;
import data.Job;
import data.JobDB;
import data.Park;
import data.ParkDB;
import data.UserDB;

public class Main {
	static JobDB jobs;
	static ParkDB parks;
	static UserDB users;
	static AbstractUser myCurrentUser;
	
	public static void main(String[] args) {
		
//		populateData();
//		backupDB();
		
		
		
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
				Volunteer(scan);
				//Clear buffer - I don't know the reason why but this code make it works :)
				scan.nextLine();
			} else if (myCurrentUser instanceof PMUser) {
				ParkManager(scan);
				scan.nextLine();
			} else if (myCurrentUser instanceof UPSUser) {
				Staff(scan);
				scan.nextLine();
			} 
			 
		}
		scan.close();
		System.out.println("Thank you for using the program! Good bye.");
		System.exit(0);
		//backupDB();
	}

	private static void nextScreen() {
		System.out.println();
		System.out.println();
		System.out.println("===============================================================================");
		System.out.println();
		System.out.println();
		
	}

	private static void populateData() {
		users = new UserDB();
		users.addUser(new VolUser("Vol", "User", "volunteer@mail.com"));
		users.addUser(new PMUser("PM", "User", "PM@mail.com"));
		users.addUser(new UPSUser("UPS", "User", "UPS@mail.com"));
		
		jobs = new JobDB();
		
		parks = new ParkDB();
		parks.addPark(new Park("This park", "123 st", (PMUser)users.getUser("PM@mail.com")));
		
		jobs.addJob(new Job("job desc", new Date("02/01/2014"), new Date("02/02/2014"), parks.getParksManagedBy((PMUser)users.getUser("PM@mail.com")).get(0), 2,3,4));
		
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
	
	
	
	
	
	//Volunteers functions
	private static void Volunteer(Scanner scan){
		boolean volunteerContinue = true;
		while(volunteerContinue){
			System.out.println("Welcome Volunteer. What would you like to do?");
			System.out.println("1. View summary of all upcoming jobs");
			System.out.println("2. View jobs you signed up for.");
			//System.out.println("3. Sign up for a job.");
			//System.out.println("3. View detail of a job.");
			System.out.println("3. Quit");
			
			int userSelection = -1;
			String input = "";
			while(userSelection < 0 && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < 4)){
				System.out.print("Please enter your choice:");
				input = scan.next();
			}
			nextScreen();
			userSelection = Integer.parseInt(input);
			
			
			
			
			
			if(userSelection == 1){				
				ViewSummaryAllUpComingJobs();				
				do{
					System.out.print("Enter job number to sign up or b to go back:");
					input = promptUser(scan);
				}while(!"b".equals(input) && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < jobs.getAllJobs().size()+1));
				nextScreen();
				if(!"b".equals(input))SignUpForJob(Integer.parseInt(input)-1, scan);
				
			} 
			
			
			
			
			else if(userSelection == 2){
				ViewJobsSignedUp(scan);
				
			}
			
			
			
			
			
			else if(userSelection == 3){
				volunteerContinue = false;
				quit();
			}
			//System.out.println("------------");
		}
	}

	private static String promptUser(Scanner theScanner) {
		
		return theScanner.next();
	}

	private static void quit() {
		System.out.println("Good Bye.....");
		backupDB();
		System.exit(0);
		
	}

	//Could put username or volunteer info as parameter(s)
	private static void ViewJobsSignedUp(Scanner theScanner){
		String input = "";
		ArrayList<Job> volunteeredJobs = jobs.getVolunteerJobs((VolUser)myCurrentUser);
		for(int i = 0; i<volunteeredJobs.size();i++) {
			System.out.println((i+1) + ". "+volunteeredJobs.get(i));
		}
		do {
			System.out.print("Select job for further details or enter b to go back:");
			input = theScanner.next();
		} while (!"b".equals(input) && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < volunteeredJobs.size()+1));
		if(input.matches(".*\\d+.*") && Integer.parseInt(input) < volunteeredJobs.size()+1){
			do {
				nextScreen();
				System.out.println(volunteeredJobs.get(Integer.parseInt(input)-1));
				System.out.print("Enter b to go back:");
				input = theScanner.next();
			} while (!"b".equals(input));
		}		
		nextScreen();
	}
	
	//Could put job info as parameter(s)
	private static void SignUpForJob(int theJobIndex, Scanner theScanner){
		System.out.println("Spots remaining...");
		System.out.print(jobs.getAllJobs().get(theJobIndex).displayOpenings());
		String input = "";
		do {
			System.out.print("Select intensity to sign up for or b to go back.");
			input = theScanner.next();
		} while (!"b".equals(input) && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < 4 && jobs.getAllJobs().get(theJobIndex).hasAvailableSpot(Integer.parseInt(input))));
		if(input.matches(".*\\d+.*") && Integer.parseInt(input) < 4) {
			int intensity = Integer.parseInt(input);			
			System.out.println("\n\n"+jobs.getAllJobs().get(theJobIndex).addVolunteer((VolUser)myCurrentUser, intensity));
			
		}
		nextScreen();
		
		
		//Add code to sign up for job with jobSelection parameter.
		//System.out.println("You've signed up for job " + jobSelection);
	}
	
	
	
	

	
	
	//Park manager functions
	private static void ParkManager(Scanner scan){
		boolean managerContinue = true;
		while(managerContinue){
			System.out.println("Welcome Park Manager. What would you like to do?");
			System.out.println("1. View managed parks.");
//			System.out.println("2. Delete a job.");
//			System.out.println("3. Edit detail of a job.");
			System.out.println("2. View summary of all upcoming jobs at Parks that I manage.");
			//System.out.println("5. View Volunteers for a job.");
			System.out.println("3. Quit.");
			
			
			
			int userSelection = -1;			
			String input = "";
			while(userSelection < 0 && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < 4)){
				System.out.print("Please enter your choice:");
				input = scan.next();
			}
			nextScreen();
			userSelection = Integer.parseInt(input);
			
			
			
			if(userSelection == 1){
				
				viewManagedParks(scan);
			} else if(userSelection == 2){
				for(Job j:jobs.getParkManagerJobs((PMUser)myCurrentUser))System.out.println(j);
				input = "";
				do {
					System.out.print("Enter b to go back:");
					input = scan.next();
				} while (!"b".equals(input));
				nextScreen();
			} else if(userSelection == 3){
				managerContinue = false;
				quit();
			}
			
		}
	}
	
	private static void viewManagedParks(Scanner theScanner) {
		ArrayList<Park> managedParks = parks.getParksManagedBy((PMUser)myCurrentUser);
		int i = 1;
		System.out.println("Parks...");
		for(Park  p: managedParks){
			System.out.println(i++ +". "+p);
		}
		
		String input = "";
		do {
			System.out.print("Select park for more options or b to go back:");
			input = theScanner.next();
		} while (!"b".equals(input) && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < managedParks.size()+1));
		if(input.matches(".*\\d+.*") && Integer.parseInt(input) < managedParks.size()+1) {
			int parkSelection = Integer.parseInt(input)-1;
			nextScreen();
			System.out.println(managedParks.get(parkSelection) + " options...");
			System.out.println("1. Add job.");
			System.out.println("2. View jobs.");
			System.out.println("3. Go back...");
			
			
			
			
			
			int userSelection = -1;			
			input = "";
			while(userSelection < 0 && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < 4)){
				System.out.print("Please enter your choice:");
				input = theScanner.next();
			}
			nextScreen();
			userSelection = Integer.parseInt(input);
			
			
			
			if(userSelection == 1){				
				SubmitJob(theScanner, managedParks.get(parkSelection));
			} else if(userSelection == 2){
				viewParkJobs(theScanner, managedParks.get(parkSelection));
			}else if (userSelection == 3);
		}
		nextScreen();
		
	}

	private static void viewParkJobs(Scanner theScanner, Park thePark) {
		ArrayList<Job> parkJobs = jobs.getParkJobs(thePark);
		
		int i = 1;
		System.out.println("Parks...");
		for(Job  j: parkJobs){
			System.out.println(i++ +". "+j);
		}
		
		String input = "";
		do {
			System.out.print("Select job for more options or b to go back:");
			input = theScanner.next();
		} while (!"b".equals(input) && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < parkJobs.size()+1));
		nextScreen();
		if(input.matches(".*\\d+.*") && Integer.parseInt(input) < parkJobs.size()+1) {
			int jobSel = Integer.parseInt(input)-1;
			System.out.println("Options for " + parkJobs.get(jobSel));
			System.out.println("1. Delete.");
			System.out.println("2. Modify.");
			System.out.println("3. Go back...");
			

			int userSelection = -1;			
			input = "";
			while(userSelection < 0 && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < 4)){
				System.out.print("Please enter your choice:");
				input = theScanner.next();
			}
			nextScreen();
			userSelection = Integer.parseInt(input);
			
			if(userSelection == 1){				
				jobs.deleteJob(parkJobs.get(jobSel));
				System.out.println("Job deleted.");
			} else if(userSelection == 2){
				EditJob(theScanner, parkJobs.get(jobSel), thePark);
			}else if (userSelection == 3);
			
			nextScreen();
			
			
			
			
		}
		
	}

	

	private static void EditJob(Scanner theScanner, Job theJob, Park thePark) {
		theScanner.nextLine();
		System.out.print("Please enter new job description:");
		String description = theScanner.nextLine();
		System.out.print("Please enter the new  date the job will Start (mm/dd/yyyy):");
		@SuppressWarnings("deprecation")
		Date jobDateStart = new Date(theScanner.nextLine());
		System.out.print("Please enter the new date the job will End (mm/dd/yyyy):");
		@SuppressWarnings("deprecation")
		Date jobDateEnd = new Date(theScanner.nextLine());
		System.out.print("Please enter the new max for light workers:");
		int light = theScanner.nextInt();
		System.out.print("Please enter the new max for medium workers:");
		int medium = theScanner.nextInt();
		System.out.print("Please enter the new max for heavy workers:");
		int heavy = theScanner.nextInt();
		theJob = new Job(description, jobDateStart, jobDateEnd, thePark, light, medium,heavy);
		
		System.out.println("Job edited.");
	}

	
	private static void SubmitJob(Scanner scan, Park thePark){
		scan.nextLine();
		System.out.print("Please enter job description:");
		String description = scan.nextLine();
		System.out.print("Please enter the date the job will Start (mm/dd/yyyy):");
		@SuppressWarnings("deprecation")
		Date jobDateStart = new Date(scan.nextLine());
		System.out.print("Please enter the date the job will End (mm/dd/yyyy):");
		@SuppressWarnings("deprecation")
		Date jobDateEnd = new Date(scan.nextLine());
		System.out.print("Please enter the max for light workers:");
		int light = scan.nextInt();
		System.out.print("Please enter the max for medium workers:");
		int medium = scan.nextInt();
		System.out.print("Please enter the max for heavy workers:");
		int heavy = scan.nextInt();
		System.out.println(jobs.addJob(new Job(description, jobDateStart, jobDateEnd, thePark, light, medium,heavy)));
		
	}
	
	
	
	
	
	//Staff functions
	private static void Staff(Scanner scan){
		boolean volunteerContinue = true;
		while(volunteerContinue){
			System.out.println("Welcome Urban Parks staff. What would you like to do?");
			System.out.println("1. Search volunteers by last name.");
			System.out.println("2. View summary of all upcoming jobs.");			
			System.out.println("3. Quit");
			
			
			
			int userSelection = -1;			
			String input = "";
			while(userSelection < 0 && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < 4)){
				System.out.print("Please enter your choice:");
				input = scan.next();
			}
			nextScreen();
			userSelection = Integer.parseInt(input);
			
			
			if(userSelection == 1){
				System.out.print("Enter last name:");
				input = scan.next();
				ArrayList<VolUser> foundUsers = users.searchByLName(input);
				System.out.println(foundUsers.size() +" user(s) found...");
				for(VolUser u :users.searchByLName(input)){
					System.out.println(u);
				}
			} else if(userSelection == 2){
				ViewSummaryAllUpComingJobs();
				do{
					System.out.print("Enter job number to view details or b to go back:");
					input = promptUser(scan);
				}while(!"b".equals(input) && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < jobs.getAllJobs().size()+1));
				nextScreen();
				if(!"b".equals(input))System.out.println(jobs.getAllJobs().get(Integer.parseInt(input)-1));
			} else if(userSelection == 3){
				ViewDetailUpComingJob(scan);
			} else if(userSelection == 4){
				volunteerContinue = false;
			}
			nextScreen();
		}
	}
	
	private static void ViewSummaryAllUpComingJobs() {
		int i = 1;
		System.out.println("Jobs...");
		for(Job curJob : jobs.getAllJobs()){
			System.out.println(i++ +". "+ curJob);
		}
		
		
	}

	//This function display detail of an up coming job.
	private static void ViewDetailUpComingJob(Scanner scan) {
		// TODO Auto-generated method stub
		ViewSummaryAllUpComingJobs();
		int userSelection = scan.nextInt();
		
		//add code to display detail of all upcoming jobs here
		
		System.out.println("Detail of an upcoming job "+userSelection+" is displayed.");
	}

	private static void SearchVolunteer(Scanner scan){
		System.out.println("Please enter volunteer's last name:");
		String lastName = scan.nextLine();
		
		//Add code to search and display the result here
		
		System.out.println("Volunteer: "+ lastName+" displayed.");
	}
	
	

}
