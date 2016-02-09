package main;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.print.DocFlavor.INPUT_STREAM;

import users.AbstractUser;
import data.DataService;
import data.JobDB;
import data.ParkDB;
import data.UserDB;

public class Main {
	static JobDB jobs;
	static ParkDB parks;
	static UserDB users;
	static AbstractUser myCurrentUser;
	
	public static void main(String[] args) {
		boolean programContinue = true;
		initDB();		
		Scanner scan = new Scanner(System.in);
		String email;
		while(programContinue){
			System.out.println("Welcome to Urban Park.");
			System.out.println("Please enter your email (enter letter q to quit program): ");
			email = scan.nextLine();
			
			//The following code should be replace by code to determine the user type.
			if(email.equals("q")){
				programContinue = false;
			} else if(email.equals("1")) {
				Volunteer(scan);
				//Clear buffer - I don't know the reason why but this code make it works :)
				scan.nextLine();
			} else if (email.equals("2")) {
				ParkManager(scan);
				scan.nextLine();
			} else if (email.equals("3")) {
				Staff(scan);
				scan.nextLine();
			} 
			 
		}
		scan.close();
		System.out.println("Thank you for using the program! Good bye.");
		System.exit(0);
		//backupDB();
	}

	private static void initDB() {
//		ArrayList<Object> DBs = DataService.loadDBs();
//		jobs = (JobDB) DBs.get(0);
//		parks = (ParkDB) DBs.get(1);
//		users = (UserDB) DBs.get(2);				
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
			System.out.println("3. Sign up for a job.");
			System.out.println("4. View detail of a job.");
			System.out.println("5. Quit");
			System.out.println("Please enter your choice:");
			int userSelection = scan.nextInt();
			if(userSelection == 1){
				ViewSummaryAllUpComingJobs();
			} else if(userSelection == 2){
				ViewJobsSignedUp();
			} else if(userSelection == 3){
				SignUpForJob(scan);
			}else if(userSelection == 4) {
				ViewJobDetail(scan);
			} else if(userSelection == 5){
				volunteerContinue = false;
			}
			System.out.println("------------");
		}
	}

	//Could put username or volunteer info as parameter(s)
	private static void ViewJobsSignedUp(){
		//Add code to show signed jobs
		System.out.println("All signed jobs showed");
	}
	
	//Could put job info as parameter(s)
	private static void SignUpForJob(Scanner scan){
		System.out.println("Please select from one of the following jobs:");
		
		ViewSummaryAllUpComingJobs();
		
		System.out.println("Please enter which job you want to sign up for:");
		int jobSelection = scan.nextInt();
		
		//Add code to sign up for job with jobSelection parameter.
		System.out.println("You've signed up for job " + jobSelection);
	}
	
	//Could put job info as parameter(s)
	private static void ViewJobDetail(Scanner scan){
		System.out.println("Please select from one of the following jobs:");
		
		ViewSummaryAllUpComingJobs();
		
		System.out.println("Please enter which job you want to view:");
		int jobSelection = scan.nextInt();
		
		//Add code to sign up for job with jobSelection parameter.
		System.out.println("detail of job " + jobSelection + " displayed");
	}
	
	

	
	
	//Park manager functions
	private static void ParkManager(Scanner scan){
		boolean managerContinue = true;
		while(managerContinue){
			System.out.println("Welcome Park Manager. What would you like to do?");
			System.out.println("1. Submit a new job.");
			System.out.println("2. Delete a job.");
			System.out.println("3. Edit detail of a job.");
			System.out.println("4. View summary of all upcoming jobs at Parks that I manage.");
			System.out.println("5. View Volunteers for a job.");
			System.out.println("6. Quit.");
			System.out.println("Please enter your choice:");
			int userSelection = scan.nextInt();
			if(userSelection == 1){
				SubmitJob(scan);
			} else if(userSelection == 2){
				DeleteJob(scan);
			} else if(userSelection == 3){
				EditJob(scan);
			} else if(userSelection == 4){
				ViewSummary();
			} else if(userSelection == 5){
				ViewVolunntersAJob(scan);
			} else if(userSelection == 6){
				managerContinue = false;
			}
			System.out.println("------------");
		}
	}
	
	private static void ViewVolunntersAJob(Scanner scan) {
		// TODO Auto-generated method stub
		ViewAllJobOfParkManager();
		System.out.println("Please choose one job to view volunteers:");
		int userSelection = scan.nextInt();
		
		//Add code to display volunteers here
		
		System.out.println("Volunteers of job "+userSelection+" displayed");
	}

	//Could use current user as a parameter 
	private static void ViewSummary() {
		// TODO Auto-generated method stub
		//Add code to display summary here
		
		System.out.println("Summary displayed.");
	}

	private static void EditJob(Scanner scan) {
		// TODO Auto-generated method stub
		ViewAllJobOfParkManager();
		System.out.println("Please choose a job to edit:");
		int userSelection = scan.nextInt();
		
		//Add code to edit job here
		
		System.out.println("Job "+userSelection+" edited.");
	}

	private static void DeleteJob(Scanner scan) {
		// TODO Auto-generated method stub
		ViewAllJobOfParkManager();
		System.out.println("Please choose a job to delete:");
		int userSelection = scan.nextInt();
		
		//Add code to delete job here
		
		System.out.println("Job "+userSelection+" deleted.");
	}

	private static void SubmitJob(Scanner scan){
		scan.nextLine();
		System.out.println("Please enter job description.");
		String description = scan.nextLine();
		System.out.println("Please enter the date the job will occur (mm/dd/yyyy):");
		@SuppressWarnings("deprecation")
		Date jobDate = new Date(scan.nextLine());
		System.out.println("Please enter the Park that the job will occur: ");
		String park = scan.nextLine();
		
		//Add code to add job here
		
		System.out.println("Job created: "+description+", "+jobDate.toString()+", "+park);
	}
	
	//Display all jobs associate with current park manager
	//Could add current user as a parameter
	private static void ViewAllJobOfParkManager(){
		//Add code here
		
		System.out.println("All job of this manager displayed.");
	}
	
	
	
	
	//Staff functions
	private static void Staff(Scanner scan){
		boolean volunteerContinue = true;
		while(volunteerContinue){
			System.out.println("Welcome Urban Parks staff. What would you like to do?");
			System.out.println("1. Search volunteers by last name.");
			System.out.println("2. View summary of all upcoming jobs.");
			System.out.println("3. View detail of a job.");
			System.out.println("4. Quit");
			System.out.println("Please enter your choice:");
			int userSelection = scan.nextInt();
			if(userSelection == 1){
				SearchVolunteer(scan);
			} else if(userSelection == 2){
				ViewSummaryAllUpComingJobs();
			} else if(userSelection == 3){
				ViewDetailUpComingJob(scan);
			} else if(userSelection == 4){
				volunteerContinue = false;
			}
			System.out.println("------------");
		}
	}
	
	private static void ViewSummaryAllUpComingJobs() {
		// TODO Auto-generated method stub
		
		//add code to display all detail of all up coming jobs here
		
		System.out.println("All summary of all up coming jobs are displayed.");
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
