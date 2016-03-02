package main;

import java.util.ArrayList;
import java.util.Scanner;

import data.Job;
import users.VolUser;

public class UPStaffDriver {
	
	private UrbanParksDriver UPDriver;

	public UPStaffDriver(UrbanParksDriver urbanParksDriver) {
		UPDriver = urbanParksDriver;
	}

	public void displayVolInterface(Scanner scan) {
		boolean volunteerContinue = true;
		ArrayList<String> menu = new ArrayList<String>();
		menu.add("Welcome Urban Parks staff. What would you like to do?");
		menu.add("1. Search volunteers by last name.");
		menu.add("2. View summary of all upcoming jobs.");			
		menu.add("3. Quit");
		String errMessage = "Oops that is not one of the current menu options.";
		while(volunteerContinue) {
			UPDriver.displayHeader(UPDriver.myCurrentUser, "Main Menu");
			UPDriver.displayMenu(menu);
			
			int userSelection = UPDriver.userMenuSelection(scan, 1, 3, errMessage, null);
			
			String input = "";
			//UPDriver.nextScreen();
			if (userSelection == 1) {
				UPDriver.displayHeader(UPDriver.myCurrentUser, "Search Volunteer by Last Name");
				System.out.print("Enter last name: ");
				input = scan.next();
				ArrayList<VolUser> foundUsers = UPDriver.users.searchByLName(input);
				System.out.println(foundUsers.size() +" user(s) found...");
				for(VolUser u :UPDriver.users.searchByLName(input)){
					System.out.println(u);
				}
			} else if(userSelection == 2) {
				UPDriver.displayHeader(UPDriver.myCurrentUser, "View Summary of all upcoming jobs");
				UPDriver.ViewSummaryAllUpComingJobs();
				ArrayList<Job> allJobs = UPDriver.jobs.getPendingJobs();
				String selJob = "Please select a job from the list or enter b to go back: ";
				System.out.print(selJob);
				int selection = UPDriver.userMenuSelection(scan, 1, allJobs.size(), selJob, "b");
				//UPDriver.nextScreen();
				if(selection > -1) {
					UPDriver.displayHeader(UPDriver.myCurrentUser, "Detail of a job");
					Job selectedJob = allJobs.get(selection - 1);
					System.out.println(selectedJob);
					System.out.println("\n\nVolunteer info...");
					System.out.print(UPDriver.displayVolunteerInfo(selectedJob)+"\n\n");
				}
			} else if(userSelection == 3){
				volunteerContinue = false;
				UPDriver.quit();
			}
			//UPDriver.nextScreen();
		}		
	}
	

}