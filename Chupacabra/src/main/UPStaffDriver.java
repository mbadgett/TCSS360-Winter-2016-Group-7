package main;

import java.util.ArrayList;
import java.util.Scanner;

import users.VolUser;

public class UPStaffDriver {
	
	private UrbanParksDriver UPDriver;

	public UPStaffDriver(UrbanParksDriver urbanParksDriver) {
		UPDriver = urbanParksDriver;
	}

	public void displayVolInterface(Scanner scan) {
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
			UPDriver.nextScreen();
			userSelection = Integer.parseInt(input);
			
			
			if(userSelection == 1){
				System.out.print("Enter last name:");
				input = scan.next();
				ArrayList<VolUser> foundUsers = UPDriver.users.searchByLName(input);
				System.out.println(foundUsers.size() +" user(s) found...");
				for(VolUser u :UPDriver.users.searchByLName(input)){
					System.out.println(u);
				}
			} else if(userSelection == 2){
				UPDriver.ViewSummaryAllUpComingJobs();
				do{
					System.out.print("Enter job number to view details or b to go back:");
					input = scan.next();
				}while(!"b".equals(input) && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < UPDriver.jobs.getAllJobs().size()+1));
				UPDriver.nextScreen();
				if(!"b".equals(input))System.out.println(UPDriver.jobs.getAllJobs().get(Integer.parseInt(input)-1));
			} else if(userSelection == 3){
				volunteerContinue = false;
				UPDriver.quit();
			}
			UPDriver.nextScreen();
		}		
	}
}