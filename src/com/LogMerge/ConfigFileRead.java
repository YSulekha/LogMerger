package com.LogMerge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigFileRead {
	private String path;
	ArrayList<String> LogFileList = new ArrayList<String>();
	ArrayList<String> TimeFormatList = new ArrayList<String>();
	ArrayList<String> AliasNameList = new ArrayList<String>();
	ArrayList<String> dNames = new ArrayList<String>();
	
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
	public void fileSearch(){
		UserInput ui = new UserInput();
		
		if(ui.parentDirectory != null){
			
		}
	}
	public void listFilesAndFilesSubDirectories(String directoryName){
		
		File directory = new File(directoryName);
		//get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList){
		//if (file.isFile()){
	//	System.out.println(file.getAbsolutePath());
		if (file.isDirectory()){
			dNames.add(file.getAbsolutePath());
		listFilesAndFilesSubDirectories(file.getAbsolutePath());
		}
		}
		for(String name:dNames){
			System.out.println(name);
		}
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
class FileFilter implements FilenameFilter {
    private String fileExtension;
    public FileFilter(String fileExtension) {
        this.fileExtension = fileExtension;
    }
    @Override
    public boolean accept(File directory, String fileName) {
        return (fileName.endsWith(this.fileExtension));
    }
}

