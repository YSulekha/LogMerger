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
/*	public static void main(String args[]){
		UserInput ui = new UserInput();
		ui.user_Input();
	}*/
}
