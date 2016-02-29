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
	
	private static String errMessage = "Oops that is not one of the current menu options.";

	public ParkManagerDriver(UrbanParksDriver urbanParksDriver) {
		UPDriver = urbanParksDriver;
	}

	public void displayVolInterface(Scanner scan)  {
		boolean managerContinue = true;
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
				parkOptions(scan, managedParks.get(parkSelection - 1));
			}
			UPDriver.nextScreen();
		} while (parkSelection != -1);
		
	}
	
	private void parkOptions(Scanner scan, Park thePark) {
		int userSelection;
		ArrayList<String> parksMenu = new ArrayList<String>();
		do {
			parksMenu.add(thePark + " options...");
			parksMenu.add("1. Add new job.");
			parksMenu.add("2. View/Edit current jobs.");
			parksMenu.add("3. Go back...");	
		
			UPDriver.displayHeader(UPDriver.myCurrentUser, "Park Management Menu");
			UPDriver.displayMenu(parksMenu);
			parksMenu.clear();
			userSelection = UPStaffDriver.userMenuSelection(scan, 1, 3, errMessage, null);
			UPDriver.nextScreen();			
	
			if(userSelection == 1){				
				SubmitJob(scan, thePark);
			} else if(userSelection == 2){
				viewParkJobs(scan, thePark);
			}
		} while(userSelection != 3);
	}


	private void viewParkJobs(Scanner theScanner, Park thePark) {
		ArrayList<Job> parkJobs;	
		int i;
		int jobSel;
		
		do {
			UPDriver.displayHeader(UPDriver.myCurrentUser, "Jobs Menu");
			parkJobs = UPDriver.jobs.getParkJobs(thePark);
			i = 1;
			System.out.println("Jobs...");
			for(Job  j: parkJobs){
				System.out.println(i++ +". "+j);
			}		
			String jobSelectMsg = "Select job for more options or b to go back:";
			System.out.println(jobSelectMsg);
			jobSel = UPStaffDriver.userMenuSelection(theScanner, 1, parkJobs.size(), jobSelectMsg, "b");
			UPDriver.nextScreen();
			if (jobSel != -1) {
				jobOptions(theScanner, parkJobs.get(jobSel - 1), thePark);
			}
		} while(jobSel != -1);
	}
	
	
	private void jobOptions(Scanner theScanner, Job currentJob, Park thePark) {
		int userSelection;
		ArrayList<String> menu = new ArrayList<String>();
		do {
			menu.add("Options for " + currentJob);
			menu.add("\nVolunteer info...");
			menu.add(UPDriver.displayVolunteerInfo(currentJob)+"\n");			
			menu.add("1. Delete.");
			menu.add("2. Modify.");
			menu.add("3. View volunters.");
			menu.add("4. Go back...");
			UPDriver.displayMenu(menu);
			menu.clear();
			userSelection = UPStaffDriver.userMenuSelection(theScanner, 1, 4, errMessage, null);			
			UPDriver.nextScreen();
			if(userSelection == 1){				
				UPDriver.jobs.deleteJob(currentJob);
				System.out.println("Job deleted.");
				break;
			} else if(userSelection == 2){
				EditJob(theScanner, currentJob, thePark);
			} else if (userSelection == 3){
				for(VolUser v: currentJob.getVolunteers()){
					System.out.println(v);
				}
				do {
					System.out.println("Enter b to go back");
				} while (!theScanner.next().equals("b"));
			}
			UPDriver.nextScreen();
		} while (userSelection != 4);
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
