package com.LogMerge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
//import java.io.FilenameFilter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;



public class ConfigFileRead {
	private String path;
	private static final String FILE_TEXT_EXT = "*.log";
	ArrayList<String> LogFileList = new ArrayList<String>();
	ArrayList<String> TimeFormatList = new ArrayList<String>();
	ArrayList<String> AliasNameList = new ArrayList<String>();
	private ArrayList<String> dNames = new ArrayList<String>();
	UserInput ui = new UserInput();
	
	ConfigFileRead(String File_Path) {
		path = File_Path;
	}
	
	public void file_Open() throws IOException {
		FileReader freader = new FileReader(path);
		BufferedReader reader = new BufferedReader(freader);
		String line = null;
		while((line=reader.readLine()) != null){
			addList(line);
		}
		reader.close();
	}
	
	public void addList(String lineToParse) {
		String [] Tokens = lineToParse.split("\\$");
	//	System.out.println(Tokens[0]);
		LogFileList.add(Tokens[0]);
		TimeFormatList.add(Tokens[1]);
		AliasNameList.add(Tokens[2]);
	}
	
	public void fileSearch() {
		
		
		if(ui.parentDirectory != null) {
			fileLocation(ui.parentDirectory,FILE_TEXT_EXT);
		}
		if(ui.fileString != null) {
			fileLocation(ui.parentDirectory,ui.fileString);
		}
	}
	
/*	public void listFile(String file_Dir,String ext) {
		listFilesAndFilesSubDirectories(file_Dir);
		dNames.add(file_Dir);
		GenericExtFilter filter = new GenericExtFilter(ext);
    for(String folder:dNames){
    	File dir = new File(folder);
 
    	if(dir.isDirectory()==false){
    		System.out.println("Directory does not exists : " + folder);
    		continue;
    	}
    	// list out all the file name and filter by the extension
    	String[] list = dir.list(filter);
    	if (list.length == 0) {
    		System.out.println("no files end with : " + ext);
    		continue;
    	}
    	for (String file : list) {
    		String temp = new StringBuffer(folder).append(File.separator)
					.append(file).toString();
    		LogFileList.add(temp);
    		AliasNameList.add(file);
			//System.out.println("file : " + temp);
    	}
    }
    for(String name:LogFileList){
			System.out.println(name);
		}
	}*/
	public void fileLocation(String directoryName,String pattern) {
		
		String filename[] = pattern.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	//	WildCardFileFilter filter = new WildCardFileFilter(".*Server.log");
	//	File[] files = dir.listFiles(filter);
		dNames.add(directoryName);
		listFilesAndFilesSubDirectories(directoryName);
		for(String d:dNames){
			File dir = new File(d);
			for(String fname:filename){
		
					WildCardFileFilter filter = new WildCardFileFilter(fname);
					File[] files = dir.listFiles(filter);
					for(File f:files){
						try{
						String sDate = sdf.format(f.lastModified());
						//	System.out.println(sdf.format(fff.lastModified()));
							if(sdf.parse(sDate).compareTo(ui.startDate)>=0) {
								LogFileList.add(f.getAbsolutePath());
								AliasNameList.add(f.getName());
					}
						}
						catch(ParseException ex) {
							System.out.println("Unable to parse the date");
							ex.printStackTrace();
						}
					
			}
		}
}
	}
	public void listFilesAndFilesSubDirectories(String directoryName) {
		
		File directory = new File(directoryName);
		//get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList){
			if (file.isDirectory()) {
				dNames.add(file.getAbsolutePath());
				listFilesAndFilesSubDirectories(file.getAbsolutePath());
			}
		}
		for(String name:dNames) {
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

//inner class, generic extension filter
/*public class GenericExtFilter implements FilenameFilter {

	private String ext;

	public GenericExtFilter(String ext) {
		this.ext = ext;
	}

	public boolean accept(File dir, String name) {
	// Pattern.compile(_pattern).matcher(file.getName()).find();
		return (name.endsWith(ext));
	}
}*/
public class WildCardFileFilter implements FileFilter{
	
    private String _pattern;
    private String p;
 
    public WildCardFileFilter(String pattern) {
    	_pattern = pattern.replace("*", ".*").replace("?", ".");
    }
 
    public boolean accept(File file) {
    		return(Pattern.compile(_pattern).matcher(file.getName()).find());
    }
}
}

