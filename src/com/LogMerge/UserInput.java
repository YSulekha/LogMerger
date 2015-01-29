package com.LogMerge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.Scanner;

public class UserInput {
	Date startDate;
	Date endDate;
	String sStartDate;
	String sEndDate;
	String parentDirectory;
	int timeDiff;
	String fileString;
	static final long ONE_MINUTE_IN_MILLIS=60000;
	
	public static void main(String args[]){
		UserInput ui = new UserInput();
		int i = 0;
		String arg = "";
		boolean t1 = false;
		boolean t2 = false;
		boolean dflag = false;
		String dir = "";
		while (i < args.length && args[i].startsWith("-")) {
			arg = args[i++];

			//check for "-f" arguments
      if (arg.equals("-f")) {
      	ui.fileString = args[i++];
      	dflag = true;
      }

      //check for arguments -d arguments
      else if (arg.equals("-d")) {
      	dir = args[i++];
      	if (i < args.length && !dir.startsWith("-")) {
      		ui.parentDirectory = dir;
          dflag = true;
         }
         else{
        	 System.err.println("-d requires a directory");
        	 break;
         }
      }
      //Check for time Arguments
      else if(arg.equals("-t")) {
      	dir = args[i++];
      	if (i < args.length && !dir.startsWith("-")) {
      		t1 = true;
      		t2 = true;
          ui.timeDiff = Integer.parseInt(args[i++]);
      	}
      	else {
      		System.err.println("-t requires a timeDiff");
      		break;
      	}
      }
      
      else if(arg.equals("-t1")) {
      	dir = args[i++];
      	if (i < args.length && !dir.startsWith("-")) {
      		t1 = true;
          ui.sStartDate = args[i++];
      	}
      	else{
      		System.err.println("-t1 requires a date");
      		break;
      	}
      }
      
      else if(arg.equals("-t2")) {
      	dir = args[i++];
      	if (i < args.length && !dir.startsWith("-")){
      		t2 = true;
          ui.sEndDate = args[i++];
      	}
      	else{
          System.err.println("-t2 requires a date");
          break;
      	}
      }
      else{  	
      	break;
      }
      System.out.println("End of if else");
		}
		if(i < args.length || !t1 || !t2 || !dflag) {
			System.err.println("Invalid input"+t1+t2+dflag);
    	System.err.println("Usage:/n LogMerge -d <PathToDirectory> -t <timeDiff>");
    	System.err.println("LogMerge -d <PathToDirectory> -t1 <StartTime> -t2 <endTime>");
    	System.out.println("LogMerge -d <PathToDirectory> -t <timeDiff> or -t1 <StartTime> -t2 <endTime> ");
		}
		else
			ui.date_calculation();
	//	ui.user_Input();
	}
	public void date_calculation() {
		if(timeDiff != 0){
			endDate = new Date();
			long t= endDate.getTime();
			startDate=new Date(t - (1000 * ONE_MINUTE_IN_MILLIS));
		}
		else {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
			try { 
				//if not valid, it will throw ParseException
				startDate = sd.parse(sStartDate);
				endDate = sd.parse(sEndDate);
			}
			catch(ParseException e) {
				System.out.println("Unable to parse the date");
				e.printStackTrace();
			}
		}
	}
/*public void user_Input(){
		
		int correct = 0;
		
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Start Date in the format 'yyyy-MM-DD HH:mm:ss:SS'");
		sStartDate = s.nextLine();
	//	System.out.println(sStartDate);
		startDate = dateValidity(sStartDate);*/
		/*while((correct = dateValidity(sStartDate)) != 0){
			if(correct == 1)
				System.out.println("Invalid date:Enter the correct date");
			else if(correct == 2)
				System.out.println("Invalid format:Enter the date in format 'yyyy-MM-DD HH:mm:ss:SS'");
			sStartDate = s.next();
		}*/
		/*System.out.println("Enter End Date in the format 'yyyy-MM-DD HH:mm:ss:SS'");
		sEndDate = s.nextLine();
		endDate = dateValidity(sEndDate);*/
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
/*	}
	
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
 /*
		} catch (ParseException e) {
			System.out.println("Invalid format:Enter the date in format 'yyyy-MM-DD HH:mm:ss:SS'");
			e.printStackTrace();
		}
		return d;
	}*/
}
