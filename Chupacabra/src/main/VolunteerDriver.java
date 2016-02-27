package main;

import java.util.ArrayList;
import java.util.Scanner;

import data.Job;
import users.VolUser;

public class VolunteerDriver {
	private UrbanParksDriver UPDriver;

	VolunteerDriver(UrbanParksDriver theUPDriver){
		this.UPDriver = theUPDriver;
	}

	public void displayVolInterface(Scanner scan) {
		boolean volunteerContinue = true;
		while(volunteerContinue){
			System.out.println("Welcome Volunteer. What would you like to do?");
			System.out.println("1. View summary of all upcoming jobs");
			System.out.println("2. View jobs you signed up for.");			
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
				UPDriver.ViewSummaryAllUpComingJobs();				
				do{
					System.out.print("Enter job number to sign up or b to go back:");
					input = scan.next();
				}while(!"b".equals(input) && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < UPDriver.jobs.getPendingJobs().size()+1));
				UPDriver.nextScreen();

				if(!"b".equals(input)){
					int jobSel = Integer.parseInt(input)-1;
					if(UPDriver.jobs.canVolunteer(UPDriver.jobs.getPendingJobs().get(jobSel), (VolUser) UPDriver.myCurrentUser))SignUpForJob(jobSel, scan);
					else System.out.println("Cannot volunteer for multiple jobs on the sameday.\n\n");
				}
			} else if(userSelection == 2){
				ViewJobsSignedUp(scan);
			} else if(userSelection == 3){
				volunteerContinue = false;
				UPDriver.quit();
			}
		}
	}

	private void SignUpForJob(int theJobIndex, Scanner theScanner){
		System.out.println("Spots remaining...");
		System.out.print(UPDriver.displayOpenings(UPDriver.jobs.getPendingJobs().get(theJobIndex)));
		String input = "";
		do {
			System.out.print("Select intensity to sign up for or b to go back.");
			input = theScanner.next();
		} while (!"b".equals(input) && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < 4 && UPDriver.jobs.getPendingJobs().get(theJobIndex).hasAvailableSpot(Integer.parseInt(input))));
		if(input.matches(".*\\d+.*") && Integer.parseInt(input) < 4) {
			int intensity = Integer.parseInt(input);			
			System.out.println("\n\n"+UPDriver.jobs.getPendingJobs().get(theJobIndex).addVolunteer((VolUser)UPDriver.myCurrentUser, intensity));

		}
		UPDriver.nextScreen();
	}	
	
	private void ViewJobsSignedUp(Scanner theScanner){
		String input = "";
		ArrayList<Job> volunteeredJobs = UPDriver.jobs.getVolunteerJobs((VolUser)UPDriver.myCurrentUser);
		for(int i = 0; i<volunteeredJobs.size();i++) {
			System.out.println((i+1) + ". "+ UPDriver.listingToString(volunteeredJobs.get(i)));
		}
		do {
			System.out.print("Select job for further details or enter b to go back:");
			input = theScanner.next();
		} while (!"b".equals(input) && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < volunteeredJobs.size()+1));
		if(input.matches(".*\\d+.*") && Integer.parseInt(input) < volunteeredJobs.size()+1){
			do {
				UPDriver.nextScreen();
				System.out.println(volunteeredJobs.get(Integer.parseInt(input)-1));
				System.out.print("Enter b to go back:");
				input = theScanner.next();
			} while (!"b".equals(input));
		}		
		UPDriver.nextScreen();
	}
}
