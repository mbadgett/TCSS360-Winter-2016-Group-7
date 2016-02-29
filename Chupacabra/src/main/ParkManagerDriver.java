package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import data.Job;
import data.Park;
import exceptions.DuplicateJobException;
import exceptions.JobFutureException;
import exceptions.JobLengthException;
import exceptions.JobMaxException;
import exceptions.JobPastException;
import exceptions.JobsInWeekException;
import users.PMUser;
import users.VolUser;

public class ParkManagerDriver {

	private UrbanParksDriver UPDriver;

	public ParkManagerDriver(UrbanParksDriver urbanParksDriver) {
		UPDriver = urbanParksDriver;
	}

	public void displayVolInterface(Scanner scan)  {
		boolean managerContinue = true;
		String errMessage = "Oops that is not one of the current menu options.";
		ArrayList<String> menu = new ArrayList<String>();
		menu.add("Welcome Park Manager. What would you like to do?");
		menu.add("1. Manage jobs in one of your parks");
		menu.add("2. View summary of all upcoming jobs at parks that I manage.");
		menu.add("3. Quit.");			
		while(managerContinue){
			UPDriver.displayHeader(UPDriver.myCurrentUser, "Main Menu");
			UPDriver.displayMenu(menu);
			
			int userSelection = UPStaffDriver.userMenuSelection(scan, 1, 3, errMessage, null);
			UPDriver.nextScreen();
			if(userSelection == 1){
				viewManagedParks(scan);
			} else if(userSelection == 2){
				for(Job j:UPDriver.jobs.getParkManagerJobs((PMUser)UPDriver.myCurrentUser)) {
					System.out.println(UPDriver.listingToString(j));
				}
				System.out.println("Enter b to go back");
				UPStaffDriver.userMenuSelection(scan, 1, 0, "Enter b to go back", "b"); //This call waits for the user to press 'b'
				UPDriver.nextScreen();
			} else if(userSelection == 3){
				managerContinue = false;
				UPDriver.quit();
			}			
		}		
	}


	private void viewManagedParks(Scanner scan)  {
		ArrayList<Park> managedParks = UPDriver.parks.getParksManagedBy((PMUser)UPDriver.myCurrentUser);
		ArrayList<String> parksMenu = new ArrayList<String>();
		String errMessage = "Oops that is not one of the current menu options.";
		int i;
		int parkSelection;
		
		do {
			System.out.println("Please which park you would like to manage jobs for");
			i = 1;
			for(Park  p: managedParks){
				System.out.println(i++ + ". " + p);
			}
	
			String parkMessage = "Select park for more options or b to go back:";
			System.out.print(parkMessage);
			
			parkSelection = UPStaffDriver.userMenuSelection(scan, 1, managedParks.size(), parkMessage, "b");
			UPDriver.nextScreen();
			if (parkSelection != -1) {
				parksMenu.add(managedParks.get(parkSelection - 1) + " options...");
				parksMenu.add("1. Add new job.");
				parksMenu.add("2. View/Edit current jobs.");
				parksMenu.add("3. Go back...");	
			
				UPDriver.displayHeader(UPDriver.myCurrentUser, "Park Management Menu");
				UPDriver.displayMenu(parksMenu);
				parksMenu.clear();
				int userSelection = UPStaffDriver.userMenuSelection(scan, 1, 3, errMessage, null);
				UPDriver.nextScreen();			
	
				if(userSelection == 1){				
					SubmitJob(scan, managedParks.get(parkSelection - 1));
				} else if(userSelection == 2){
					viewParkJobs(scan, managedParks.get(parkSelection - 1));
				}
			}
			UPDriver.nextScreen();
		} while (parkSelection != -1);
		
	}


	private void viewParkJobs(Scanner theScanner, Park thePark) {
		ArrayList<Job> parkJobs = UPDriver.jobs.getParkJobs(thePark);		
		int i = 1;
		System.out.println("Jobs...");
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
			System.out.println("\n\nVolunteer info...");
			System.out.print(UPDriver.displayVolunteerInfo(parkJobs.get(jobSel))+"\n\n");			
			System.out.println("1. Delete.");
			System.out.println("2. Modify.");
			System.out.println("3. View volunters.");
			System.out.println("4. Go back...");			

			int userSelection = -1;			
			input = "";
			while(userSelection < 0 && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < 5)){
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
			} else if (userSelection == 3){
				for(VolUser v: parkJobs.get(jobSel).getVolunteers()){
					System.out.println(v);
				}
			}else if (userSelection == 4);			
			UPDriver.nextScreen();			
		}		
	}	

	private void EditJob(Scanner theScanner, Job theJob, Park thePark)  {
		boolean successfulAdd = SubmitJob(theScanner, thePark);
		if (successfulAdd) {
			UPDriver.jobs.getPendingJobs().remove(theJob);//remove the old entry iff the new one succeeds.
		}
	}


	private boolean SubmitJob(Scanner scan, Park thePark) {
		boolean successfulAdd = true;
		scan.nextLine();
		System.out.print("Please enter job description:");
		String description = scan.nextLine();
		System.out.print("Please enter the date the job will Start (mm/dd/yyyy):");
		@SuppressWarnings("deprecation")
		Date jobDateStart = new Date(scan.nextLine());
		System.out.print("Please enter the date the job will End (mm/dd/yyyy):");
		@SuppressWarnings("deprecation")
		Date jobDateEnd = new Date(scan.nextLine());
		System.out.print("Please enter the max number of light duty volunteer slots:");
		int light = scan.nextInt();
		System.out.print("Please enter the max number of medium duty volunteer slots:");
		int medium = scan.nextInt();
		System.out.print("Please enter the max number of heavy duty volunteer slots:");
		int heavy = scan.nextInt();
		try {
			UPDriver.jobs.addJob(new Job(description, UPDriver.dateToCalendar(jobDateStart), UPDriver.dateToCalendar(jobDateEnd), thePark, light, medium,heavy));
		} catch (JobMaxException | DuplicateJobException | JobFutureException
				| JobPastException | JobsInWeekException | JobLengthException e) {
			successfulAdd = false;
			System.out.println("There was an error with the job you tried to submit.\nThe following error occurred:\n" + e.getMessage());
		}
		if (successfulAdd) {
			System.out.println("Job added!");
		}
		return successfulAdd;
	} 
}
