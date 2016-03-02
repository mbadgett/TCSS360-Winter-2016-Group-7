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
			
			int userSelection = userMenuSelection(scan, 1, 3, errMessage, null);
			
			String input = "";
			UPDriver.nextScreen();
			if (userSelection == 1) {
				System.out.print("Enter last name:");
				input = scan.next();
				ArrayList<VolUser> foundUsers = UPDriver.users.searchByLName(input);
				System.out.println(foundUsers.size() +" user(s) found...");
				for(VolUser u :UPDriver.users.searchByLName(input)){
					System.out.println(u);
				}
			} else if(userSelection == 2) {
				UPDriver.ViewSummaryAllUpComingJobs();
				ArrayList<Job> allJobs = UPDriver.jobs.getPendingJobs();
				String selJob = "Please select a job from the list or enter b to go back";
				System.out.println(selJob);
				int selection = userMenuSelection(scan, 1, allJobs.size(), selJob, "b");
				UPDriver.nextScreen();
				if(selection > -1) {
					Job selectedJob = allJobs.get(selection - 1);
					System.out.println(selectedJob);
					System.out.println("\n\nVolunteer info...");
					System.out.print(UPDriver.displayVolunteerInfo(selectedJob)+"\n\n");
				}
			} else if(userSelection == 3){
				volunteerContinue = false;
				UPDriver.quit();
			}
			UPDriver.nextScreen();
		}		
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
	public static int userMenuSelection(Scanner scan, int min, int max, String message, String returnC) {
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