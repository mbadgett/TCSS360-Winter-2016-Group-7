package main;

import java.io.InputStream;
import java.util.ArrayList;
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
				Staff();
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
			System.out.println("1. View information of all upcoming jobs");
			System.out.println("2. View jobs you signed up for.");
			System.out.println("3. Sign up for a job.");
			System.out.println("4. View detail of a job.");
			System.out.println("5. Quit");
			System.out.println("Please enter your choice:");
			int userSelection = scan.nextInt();
			if(userSelection == 1){
				ViewAllJobs();
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
	
	//View info of all jobs, same for all kind of users
	private static void ViewAllJobs(){
		//Add code to show all jobs here
		System.out.println("All jobs showed");
	}
	
	//Could put username or volunteer info as parameter(s)
	private static void ViewJobsSignedUp(){
		//Add code to show signed jobs
		System.out.println("All signed jobs showed");
	}
	
	//Could put job info as parameter(s)
	private static void SignUpForJob(Scanner scan){
		System.out.println("Please select from one of the following jobs:");
		
		ViewAllJobs();
		
		System.out.println("Please enter which job you want to sign up for:");
		int jobSelection = scan.nextInt();
		
		//Add code to sign up for job with jobSelection parameter.
		System.out.println("job " + jobSelection + " added");
	}
	
	//Could put job info as parameter(s)
	private static void ViewJobDetail(Scanner scan){
		System.out.println("Please select from one of the following jobs:");
		
		ViewAllJobs();
		
		System.out.println("Please enter which job you want to view:");
		int jobSelection = scan.nextInt();
		
		//Add code to sign up for job with jobSelection parameter.
		System.out.println("job " + jobSelection + " viewed");
	}
	
	
	
	//Park manager functions
	private static void ParkManager(Scanner scan){
		boolean managerContinue = true;
		while(managerContinue){
			System.out.println("Welcome Park Manager. What would you like to do?");
			System.out.println("1. Submit a new job.");
			System.out.println("2. Delete a job.");
			System.out.println("3. Edit detail of a job.");
			System.out.println("4. View summary of all upcoming jobs.");
			System.out.println("5. View Volunteers for a job.");
			System.out.println("Please enter your choice:");
			int userSelection = scan.nextInt();
			if(userSelection == 1){
				SubmitJob();
			} else if(userSelection == 2){
				DeleteJob();
			} else if(userSelection == 3){
				EditJob(scan);
			} else if(userSelection == 4){
				ViewSummary();
			} else if(userSelection == 5){
				ViewVolunntersAJob(scan);
			}
			System.out.println("------------");
		}
	}
	
	private static void ViewVolunntersAJob(Scanner scan) {
		// TODO Auto-generated method stub
		
	}

	private static void ViewSummary() {
		// TODO Auto-generated method stub
		
	}

	private static void EditJob(Scanner scan) {
		// TODO Auto-generated method stub
		
	}

	private static void DeleteJob() {
		// TODO Auto-generated method stub
		
	}

	private static void SubmitJob(){
		
	}
	
	
	
	
	//Staff functions
	private static void Staff(){
		
	}

}
