package com.LogMerge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigFileRead {
	private String path;
	ArrayList<String> LogFileList = new ArrayList<String>();
	ArrayList<String> TimeFormatList = new ArrayList<String>();
	ArrayList<String> AliasNameList = new ArrayList<String>();
	
	ConfigFileRead(String File_Path){
		path = File_Path;
	}
	
	public void file_Open() throws IOException{
		 FileReader freader = new FileReader(path);
		 BufferedReader reader = new BufferedReader(freader);
		 String line = null;
		 while((line=reader.readLine()) != null){
			 addList(line);
		 }
		 reader.close();
	}
	
	public void addList(String lineToParse){
		String [] Tokens = lineToParse.split("\\$");
	//	System.out.println(Tokens[0]);
		LogFileList.add(Tokens[0]);
		TimeFormatList.add(Tokens[1]);
		AliasNameList.add(Tokens[2]);
	}
	/*Testing Purpose
	public static void main(String [] args){
		ConfigFileRead cr = new ConfigFileRead("/Users/aharyadi/Documents/workspace/Filef.txt");
		try{
		cr.file_Open();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		System.out.println(cr.LogFileList);
		System.out.println(cr.TimeFormatList);
	}*/
}
