package com.LogMerge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * The LogAnalysis class analysis all the log files
 *  and merges into one file
 */
public class LogAnalysis {
	
	File logFile;
	BufferedWriter pw;
	TreeMap<Date, String> FinalLog = new TreeMap<Date, String>();
	UserInput ui; 
	//To-do
	public void LogFileOpen() {
		logFile = new File("MergeLog");
		try{
		pw = new BufferedWriter(new FileWriter(logFile));
		for(Date key: FinalLog.keySet()){
			pw.write(FinalLog.get(key)+"\n");
		}
		pw.flush();
		}
		catch(IOException ex) {
			System.out.println("Log File could not open");
		}
	}
	//Opens the config file
/*	public void lAnalysis() {
	//	ui.user_Input();
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the config file path");
		String path = s.next();
		ConfigFileRead cfr = new ConfigFileRead(path);
		try{
		cfr.file_Open();
		}
		catch(IOException ex) {
			System.out.println("Could not open config file");
			ex.printStackTrace();
		}
		int i = cfr.LogFileList.size();
		int j = 0;
		FileReader freader; 
		BufferedReader reader = null;
		while(j < i) {
			try{
				freader = new FileReader(new File(cfr.LogFileList.get(j)));
				reader = new BufferedReader(freader);
				fileExtract(reader,cfr.TimeFormatList.get(j),cfr.AliasNameList.get(j));
				j++;
			}
			catch(Exception ex) {
				System.out.println("File not found "+cfr.LogFileList.get(j));
				ex.printStackTrace();
				j++;
			}		
		}
		LogFileOpen();
	}*/
	public void lAnalysis(UserInput uin) {
		ui = uin;
		LogFile l = null;
		int i = ui.LogFileList.size();
		int j = 0;
		FileReader freader; 
		BufferedReader reader = null;
		while(j < i) {
			try {
				l = ui.LogFileList.get(j);
				freader = new FileReader(new File(l.logLocation));
				reader = new BufferedReader(freader);
				fileExtract(reader,l.timeFormat,l.aliasName);
				j++;
			}
			catch(IOException ex) {
				System.out.println("File not found "+l.fileName);
				ex.printStackTrace();
				j++;
			}		
		}
		LogFileOpen();
	}
	
	public void fileExtract(BufferedReader bf,String tf,String alias) throws IOException {
		String line = null;
		String pattern = "(^.\\d+\\-\\d+\\-\\d+.\\d+\\:\\d+\\:\\d+.\\d+)";
		String date = "";
		Date uniqueName = null;
		Pattern pattern1;
		Matcher matcher;
		 while((line=bf.readLine()) != null) {
			 pattern1 = Pattern.compile(pattern);
			 matcher = pattern1.matcher(line);
			 if(matcher.find()) {  	 
          date = matcher.group(1);
          if(date.charAt(0) == '[') {
          	date = date.substring(1);
          }    
          uniqueName = timeComparision(date,tf);
          if(uniqueName != null) {				 
          	if(FinalLog.containsKey(uniqueName)) {
          		String str = FinalLog.get(uniqueName)+"\n"+alias+ " -> " + line;
          		FinalLog.put(uniqueName, str);
	 				 	}
	   				else
	   					FinalLog.put(uniqueName, alias+ " -> " + line);
	   			}
			 }
			 else{
				 if(uniqueName != null) {
					 if(FinalLog.containsKey(uniqueName)) {
						 String str = FinalLog.get(uniqueName)+"\n"+alias+ " -> " + line;
						 FinalLog.put(uniqueName, str);
					 }
					 else
						 FinalLog.put(uniqueName, alias+ " -> " + line);
				 }
			 }
		 }	 
	}
	
	public Date timeComparision(String t,String timeFormat) {
		Date d=null;
	//	String unique = "";
		SimpleDateFormat sd = new SimpleDateFormat(timeFormat);
		try{
		d = sd.parse(t);
		}
		catch(ParseException e) {
			System.out.println("Invalid format");
			e.printStackTrace();
		}
		if(d.compareTo(ui.startDate) >= 0) {
			if(d.compareTo(ui.endDate)<=0) {
				return d;
			}
		}
		return null;
	}
	//testing purpose
	/*public static void main(String [] args) {
		LogAnalysis la = new LogAnalysis();
		la.lAnalysis();
	}*/
}
