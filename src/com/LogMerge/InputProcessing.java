package com.LogMerge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputProcessing {
	private static final String FILE_TEXT_EXT = "*.log";
	private static final String[] formats = { 
		"yyyy-MM-dd'T'HH:mm:ss'Z'",   "yyyy-MM-dd'T'HH:mm:ssZ",
		"yyyy-MM-dd'T'HH:mm:ss",      "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss,SS", 
		"MM/dd/yyyy HH:mm:ss",        "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'", 
		"MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS", 
		"MM/dd/yyyy'T'HH:mm:ssZ",     "MM/dd/yyyy'T'HH:mm:ss", 
		"yyyy:MM:dd HH:mm:ss,SS","yyyy-MM-dd HH:mm:ss" };
	private ArrayList<String> dNames = new ArrayList<String>();
	UserInput ui;
	
	public void fileSearch(UserInput uin) {
		ui = uin;
		if(ui.fileString == null) {
			fileLocation(ui.parentDirectory,FILE_TEXT_EXT);
		}
		if(ui.fileString != null) {
			fileLocation(ui.parentDirectory,ui.fileString);
		}
	}
	public void fileLocation(String directoryName,String fileNameString) {
		String line = null;
		String datepattern = "(^.\\d+\\-\\d+\\-\\d+.\\d+\\:\\d+\\:\\d+.\\d+)";
		String date = "";
		Pattern pattern;
		Matcher matcher;
		int count;
		String timeFormat;
		String format;
		String filename[] = fileNameString.split(",");
		boolean iFile = false;
		List<String> items = null;
		if(ui.ignoreFile != null) {
			items = Arrays.asList(ui.ignoreFile.split(","));
			iFile = true;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		dNames.add(directoryName);
		listFilesAndFilesSubDirectories(directoryName);
		ui.logger.info("Inside Directory Search");
		for(String d:dNames) {
			File dir = new File(d);
			for(String fname:filename) {
				WildCardFileFilter filter = new WildCardFileFilter(fname);
				File[] files = dir.listFiles(filter);
				for(File f:files) {
					try {
						if(iFile == true && items.contains(f.getName()))
							continue;
						String sDate = sdf.format(f.lastModified());
						ui.logger.info("Modified Time of File"+f.getName()+"is"+sDate);
						if(sdf.parse(sDate).compareTo(ui.startDate)>=0) {
							if(ui.recent == true) {
								ui.FileList.add(f.getAbsolutePath());
								ui.TimeFormatList.add(sDate);
								continue;
							}
							FileReader fr = new FileReader(f);
							BufferedReader bf = new BufferedReader(fr);
							line = "";
							date = "";
							count = 0;
							timeFormat = "";
							format = "";
							while((line=bf.readLine()) != null) {
								pattern = Pattern.compile(datepattern);
								matcher = pattern.matcher(line);
								if(matcher.find()) {
									date = matcher.group(1);
					        if(date.charAt(0) == '[') {
					        	date = date.substring(1);
					        }
					        count++;
					      }
								if(date != null) {
									format = parse(date);
									if(format != null)
										timeFormat = timeFormat+">"+format;
								}
								if(count==5)
									break;
							}
							String tfs[] = timeFormat.split(">");
							int c = 0;
							String tfFinal = " ";
							for(int j = 0;j < tfs.length-1;j++) {
								if(tfs[j].equals(tfs[j+1])) {
									c++;
									tfFinal = tfs[j];
								}
							}
							if(c > 1 && tfFinal != null) {
								LogFile l = new LogFile(f.getName());
								l.aliasName = f.getName();
								l.logLocation = f.getAbsolutePath();
								l.timeFormat = tfFinal;
								l.modificationTime = sDate;
								ui.LogFileList.add(l);
							}
							bf.close();
						}
					}
					catch(ParseException ex) {
						System.out.println("Unable to parse the date");
						ex.printStackTrace();
					}
					catch(FileNotFoundException ex) {
						System.out.println("File Not found"+f);
						ex.printStackTrace();
					}
					catch(IOException ex) {
						System.out.println("Unable to open the file"+f);
						ex.printStackTrace();
					}
				}
			}
		}
		if(ui.recent == true) {
			for(int i = 0;i < ui.FileList.size();i++) {
				System.out.println(ui.FileList.get(i)+"\t"+ui.TimeFormatList.get(i));
			}
		}
		else {
			ui.logger.info("Final list of logs");
			for(int i = 0;i < ui.LogFileList.size();i++){
				ui.logger.info("FileName"+ui.LogFileList.get(i).fileName+ui.LogFileList.get(i).timeFormat);
			}
		}
	}
	
	public void listFilesAndFilesSubDirectories(String directoryName) {
		try {
			File directory = new File(directoryName);
			//get all the files from a directory
			File[] fList = directory.listFiles();
			for(File file : fList) {
				if(file.isDirectory()) {
					dNames.add(file.getAbsolutePath());
					listFilesAndFilesSubDirectories(file.getAbsolutePath());
				}
			}
		}
		catch(Exception ex) {
			System.out.println("Directory Name not proper"+directoryName);
		}
	}
	
	public static String parse(String date) {
		Date da;
		if (date != null) {
			for (String parse : formats) {
				SimpleDateFormat sdf = new SimpleDateFormat(parse);
				try {
					da = sdf.parse(date);
					if(sdf.format(da).equals(date))
						return parse;
        } 
        catch (ParseException e) {
 
        }
      }
    }
		return null;
	}
	
	public class WildCardFileFilter implements FileFilter {
		private String _pattern;
 
    public WildCardFileFilter(String pattern) {
    	_pattern = pattern.replace("*", ".*").replace("?", ".");
    }
    
    public boolean accept(File file) {
    	return(Pattern.compile(_pattern).matcher(file.getName()).find());
    }
	}	
}
