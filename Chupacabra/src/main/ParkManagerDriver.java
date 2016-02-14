package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import data.Job;
import data.Park;
import users.PMUser;

public class ParkManagerDriver {
	
	private UrbanParksDriver UPDriver;
	
	public ParkManagerDriver(UrbanParksDriver urbanParksDriver) {
		UPDriver = urbanParksDriver;
	}

	public void displayVolInterface(Scanner scan) {
		boolean managerContinue = true;
		while(managerContinue){
			System.out.println("Welcome Park Manager. What would you like to do?");
			System.out.println("1. View managed parks.");
			System.out.println("2. View summary of all upcoming jobs at Parks that I manage.");
			System.out.println("3. Quit.");			
			
			int userSelection = -1;			
			String input = "";
			while(userSelection < 0 && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < 4)){
				System.out.print("Please enter your choice:");
				input = scan.next();
			}
			UPDriver.nextScreen();
			userSelection = Integer.parseInt(input);
			
			
			
			if(userSelection == 1){
				
				viewManagedParks(scan);
			} else if(userSelection == 2){
				for(Job j:UPDriver.jobs.getParkManagerJobs((PMUser)UPDriver.myCurrentUser))System.out.println(j);
				input = "";
				do {
					System.out.print("Enter b to go back:");
					input = scan.next();
				} while (!"b".equals(input));
				UPDriver.nextScreen();
			} else if(userSelection == 3){
				managerContinue = false;
				UPDriver.quit();
			}			
		}		
	}
	
	
	private void viewManagedParks(Scanner theScanner) {
		ArrayList<Park> managedParks = UPDriver.parks.getParksManagedBy((PMUser)UPDriver.myCurrentUser);
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
			UPDriver.nextScreen();
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
			UPDriver.nextScreen();
			userSelection = Integer.parseInt(input);			
			
			if(userSelection == 1){				
				SubmitJob(theScanner, managedParks.get(parkSelection));
			} else if(userSelection == 2){
				viewParkJobs(theScanner, managedParks.get(parkSelection));
			}else if (userSelection == 3);
		}
		UPDriver.nextScreen();		
	}
	

	private void viewParkJobs(Scanner theScanner, Park thePark) {
		ArrayList<Job> parkJobs = UPDriver.jobs.getParkJobs(thePark);		
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
		UPDriver.nextScreen();
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
			UPDriver.nextScreen();
			userSelection = Integer.parseInt(input);
			
			if(userSelection == 1){				
				UPDriver.jobs.deleteJob(parkJobs.get(jobSel));
				System.out.println("Job deleted.");
			} else if(userSelection == 2){
				EditJob(theScanner, parkJobs.get(jobSel), thePark);
			}else if (userSelection == 3);			
			UPDriver.nextScreen();			
		}		
	}	
	
	private void EditJob(Scanner theScanner, Job theJob, Park thePark) {
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
		Job newJob = new Job(description, jobDateStart, jobDateEnd, thePark, light, medium,heavy);
		String output = UPDriver.jobs.addJob(newJob);
		if(output.equals("Job added.")){
			UPDriver.jobs.getPendingJobs().remove(theJob);
			System.out.println("Job edited.");
		}else System.out.println("\n\n\n"+output);//why could not edit		
	}
	
	
	private void SubmitJob(Scanner scan, Park thePark){
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
		System.out.println(UPDriver.jobs.addJob(new Job(description, jobDateStart, jobDateEnd, thePark, light, medium,heavy)));
		
	}
}
