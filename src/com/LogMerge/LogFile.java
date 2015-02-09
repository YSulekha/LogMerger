package com.LogMerge;
//This class contains the information about the LogFiles which are merged
public class LogFile {
	String logLocation;
	String fileName;
	String aliasName;
	String timeFormat;
	String modificationTime;
	
	LogFile(String fname){
		fileName = fname;
	}
}
