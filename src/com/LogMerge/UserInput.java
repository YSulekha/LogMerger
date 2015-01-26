package com.LogMerge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserInput {
	Date startDate;
	Date endDate;
	String sStartDate;
	String sEndDate;
	String parentDirectory;
	int timeDiff;
	
	public void user_Input(){
		
		int correct = 0;
		
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Start Date in the format 'yyyy-MM-DD HH:mm:ss:SS'");
		sStartDate = s.nextLine();
	//	System.out.println(sStartDate);
		startDate = dateValidity(sStartDate);
		/*while((correct = dateValidity(sStartDate)) != 0){
			if(correct == 1)
				System.out.println("Invalid date:Enter the correct date");
			else if(correct == 2)
				System.out.println("Invalid format:Enter the date in format 'yyyy-MM-DD HH:mm:ss:SS'");
			sStartDate = s.next();
		}*/
		System.out.println("Enter End Date in the format 'yyyy-MM-DD HH:mm:ss:SS'");
		sEndDate = s.nextLine();
		endDate = dateValidity(sEndDate);
	/*	while((correct = dateValidity(sEndDate)) != 0){
			if(correct == 1)
				System.out.println("Invalid date:Enter the correct date");
			else if(correct == 2)
				System.out.println("Invalid format:Enter the date in format 'yyyy-MM-DD HH:mm:ss:SS'");
			sEndDate = s.next();
		}
		//check if enddate is greater than start date
		if(endDate.before(startDate))
			System.out.println("End Date should be greater than startdate");
			sEndDate = s.next();*/
	}
	
	public Date dateValidity(String date){
		//int validDate = 0;
		Date d = null;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
		try {
			 
			//if not valid, it will throw ParseException
			d = sd.parse(date);
		/*	System.out.println(date);
			if(!sd.format(d).equals(date)){
				System.out.println("Invalid date");
				validDate = 1;
			}*/
 
		} catch (ParseException e) {
			System.out.println("Invalid format:Enter the date in format 'yyyy-MM-DD HH:mm:ss:SS'");
			e.printStackTrace();
		}
		return d;
	}
	public static void main(String args[]){
		UserInput ui = new UserInput();
		int i = 0;
		String arg = "";
		boolean t1 = false;
		boolean t2 = false;
		
		
		while (i < args.length && args[i].startsWith("-")) {
      arg = args[i++];

// use this type of check for "wordy" arguments
      if (arg.equals("-f")) {
          System.out.println("verbose mode on");
         // vflag = true;
      }

// use this type of check for arguments that require arguments
      else if (arg.equals("-d")) {
          if (i < args.length)
              ui.parentDirectory = args[i++];
          else
              System.err.println("-f requires a filename");
      }
      
      else if(arg.equals("-t")){
      	if (i < args.length)
          ui.timeDiff = Integer.parseInt(args[i++]);
      	 else
           System.err.println("-t requires a timeDiff");
      }
      
      else if(arg.equals("-t1")){
      	if (i < args.length){
      		t1 = true;
          ui.sStartDate = args[i++];
      	}
      	 else
           System.err.println("-t1 requires a date");
      }
      else if(arg.equals("-t2")){
      	if (i < args.length){
      		t2 = false;
          ui.sEndDate = args[i++];
      	}
      	 else
           System.err.println("-t2 requires a date");
      }
	}
		if(!t1 || !t2){
			System.err.println("Enter the date");
		}
	//	ui.user_Input();
	}
}
