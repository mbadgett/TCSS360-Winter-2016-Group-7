package data;

import java.io.Serializable;
import java.util.ArrayList;

public class JobDB implements Serializable {

	private static final long serialVersionUID = -2757438807790008719L;
	public ArrayList<Job> jobList;
	//collection of jobs
	public JobDB(){
		jobList = new ArrayList<Job>();
	}
	
	
	public void addJob(Job theJob){
		jobList.add(theJob);
	}
	
	public Job getJob(){
		return jobList.get(0);
	}
	
	
		
	
}
