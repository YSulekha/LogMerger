# LogMerger
LogMerger is a Java based CLI tool to parse and merge logs for different components based on different criteria like time range, time difference.

Say you have a product which has multiple components and all those components have their own log file. Any usecase that is run by the user might leave traces in one or more components logs. Troubleshooting by going through all those logs might become tedious. LogMerger comes to the rescue here by using multiple easy to use options you can merge the section of different logfiles into single file.

Merges all the log files present inside the specified directory(use -d option) which are modified within the specified time values(-t1 to -t2) or time range(-t). User can even list the files to be merged(-f) or files to be ignored(-i). Tool can also list(-l) the name of the files modified within the specified time.

## NAME
     LogMerger -- Tool to merge different logs

## SYNOPSIS
     java -jar LogMergerFinal.jar [-dfiltt1t2] [directory ...]

## DESCRIPTION
     Merges all the log files present inside the specified directory(use -d option) which are modified within the specified time values(-t1 to -t2) or time range(-t). User can even list the files to be merged(-f) or files to be ignored(-i). Tool can also list(-l) the name of the files modified within the specified time.

     The following options are available:

     -d      Name of the parent directory inside which all the log files are present.

     -f      List of filenames present in the specified directory which are considered for merging(Instead of all log files).
     
     -i      Files to be ignored.
     
     -l      List the files modified within the specified time.
     
     -t1     Start time.
     
     -t2     End time.
     
     -t      Time range in minutes where startTime = CurrentTime - t.

## USAGE
    Download the jar file and invoke it using 'java -jar'. This creates the newly merged file "LogMerge" in the current directory.
    
    java -jar LogMerger.jar -d <DirectoryName> -t1 <StartTime:yyyy-MM-dd HH:mm:ss:SS> -t2<EndTime:yyyy-MM-dd HH:mm:ss:SS>
    
    java -jar LogMerger.jar -d <DirectoryName> -t <timeDiff in minutes>

    java -jar LogMerger.jar -d <DirectoryName> -f <CommaSeparatedfilenames> -t <timeDiff in minutes>

    java -jar LogMerger.jar -d <DirectoryName> -f <CommaSeparatedfilenames> -t1 <StartTime in yyyy-MM-dd HH:mm:ss:SS> -t2 <EndTime in yyyy-MM-dd HH:mm:ss:SS>
    
    java -jar LogMerger.jar -d <DirectoryName> -i <CommaSeparatedfilenames> -t1 <StartTime in yyyy-MM-dd HH:mm:ss:SS>  -t2 <EndTime in yyyy-MM-dd HH:mm:ss:SS>

    java -jar LogMerger.jar -d <DirectoryName> -l -t1 <StartTime in yyyy-MM-dd HH:mm:ss:SS>  -t2 <EndTime in yyyy-MM-dd HH:mm:ss:SS>
     
