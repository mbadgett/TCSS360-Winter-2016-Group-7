package main;

import java.util.ArrayList;
import java.util.Scanner;

import data.Job;
import users.AbstractUser;
import users.VolUser;

public class VolunteerDriver {
	private UrbanParksDriver UPDriver;

	VolunteerDriver(UrbanParksDriver theUPDriver){
		this.UPDriver = theUPDriver;
	}

	public void displayVolInterface(Scanner scan) {
		boolean volunteerContinue = true;
		
		while(volunteerContinue){
			ArrayList<String> menuString = new ArrayList<String>();
			menuString.add("Welcome Volunteer. What would you like to do?");
			menuString.add("1. View summary of all upcoming jobs");
			menuString.add("2. View details of a job.");
			menuString.add("3. View jobs you signed up for.");
			menuString.add("4. Quit");
			UPDriver.displayHeader(UPDriver.getAbstractUser(), "Volunteer - Main menu");
			UPDriver.displayMenu(menuString);

			int userSelection = -1;
			String input = "";
			while(userSelection < 0 && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < 4)){
				input = scan.next();
			}
	
			userSelection = Integer.parseInt(input);

			if(userSelection == 1){			
				viewSummaryofAllJobs(input, scan);
			} else if(userSelection ==2){
				viewDetailofAJob(input, scan);
			} else if(userSelection == 3){
				UPDriver.displayHeader(UPDriver.getAbstractUser(), "Volunteer - View Signed up jobs");
				ViewJobsSignedUp(scan);
			} else if(userSelection == 4){
				volunteerContinue = false;
				UPDriver.quit();
			}
		}
	}
	
	private void viewSummaryofAllJobs(String input, Scanner scan){
		UPDriver.displayHeader(UPDriver.getAbstractUser(), "Volunteer - View Summary of upcoming jobs");
		UPDriver.ViewSummaryAllUpComingJobs();				
		do{
			System.out.print("Enter job number to sign up or b to go back:");
			input = scan.next();
		}while(!"b".equals(input) && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < UPDriver.jobs.getPendingJobs().size()+1));


		if(!"b".equals(input)){
			UPDriver.displayHeader(UPDriver.getAbstractUser(), "Volunteer - Sign up for job");
			int jobSel = Integer.parseInt(input)-1;
			System.out.println(UPDriver.jobs.getPendingJobs().get(jobSel).toString());
			if(UPDriver.jobs.canVolunteer(UPDriver.jobs.getPendingJobs().get(jobSel), (VolUser) UPDriver.myCurrentUser))
				SignUpForJob(jobSel, scan);
			else System.out.println("Cannot volunteer for multiple jobs on the sameday.\n\n");
		}
	}
	
	private void viewDetailofAJob(String input, Scanner scan){
		UPDriver.displayHeader(UPDriver.getAbstractUser(), "Volunteer - View Summary of Upcoming jobs");
		UPDriver.ViewSummaryAllUpComingJobs();				
		do{
			System.out.print("Enter job number to view details or b to go back:");
			input = scan.next();
		}while(!"b".equals(input) && !(input.matches(".*\\d+.*") && Integer.parseInt(input) < UPDriver.jobs.getPendingJobs().size()+1));
		UPDriver.nextScreen();
		if(!"b".equals(input)){
			UPDriver.displayHeader(UPDriver.getAbstractUser(), "Volunteer - View detail of selected upcoming job");
			int jobSel = Integer.parseInt(input)-1;
			System.out.println(UPDriver.jobs.getPendingJobs().get(jobSel).toString());
			//UPDriver.nextScreen();
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
		//UPDriver.nextScreen();
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
				UPDriver.displayHeader(UPDriver.getAbstractUser(), "Volunteer - View detail of selected upcoming job");
				//UPDriver.nextScreen();
				System.out.println(volunteeredJobs.get(Integer.parseInt(input)-1));
				System.out.print("Enter b to go back:");
				input = theScanner.next();
			} while (!"b".equals(input));
		}		
		//UPDriver.nextScreen();
	}
}
