//Liam Rodgers 1248912
//Corbyn Noble-May 1314639

package polyphaseSortMerge;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	
	static FibList fibList;
    static int runSize = 0;
    static int maxFileNum = 0;
    static String outputFilepath = "";
    static String inputFilepath = "";
    static int operations;
    
    /*
      /R -- Defines max memory size
      /F -- Defines the max file number
      /O -- Defines Output Filepath
      /I -- Defines Input Filepath
      Format "Polymerge /R 77 /O c:/users/test.txt"
     */
    public static void main(String[] args) throws IOException{

	//Deal with input arguments
	if (args.length == 0){
	    System.out.println("Please input arguments i.e. \"Polymerge /R 77 /O c:/users/test.txt\"");
	    return;
	}
	for(int i = 0; i < args.length; i++){
	    try{
	    if(args[i].equalsIgnoreCase("/R")){
	    	runSize = Integer.parseInt(args[i+1]);
	    }
	    if(args[i].equalsIgnoreCase("/F")){
	    	maxFileNum = Integer.parseInt(args[i+1]);
	    }
	    else if(args[i].equalsIgnoreCase("/O")){
	    	outputFilepath = args[i+1];
	    }
	    else if(args[i].equalsIgnoreCase("/I")){
	    	inputFilepath = args[i+1];
	    }
	    }catch(Exception e){
		System.err.println("Please input valid input arguments, " + runSize + ", " + maxFileNum + ", " + outputFilepath + ", " + inputFilepath +", " + e);
	    }
	}
	
	    int totalRuns = 0;
	    Heap heap = new Heap(runSize);
	    Run run = new Run();

	    //Read File or system.in
	    Scanner scanner;
	    if(inputFilepath.isEmpty()){
		scanner = new Scanner(System.in);
	    }
	    else{
		File file = new File(inputFilepath);
		scanner = new Scanner(file);
	    }	    
	    String line = "";

	    String[] overLap = new String[line.length()];
	    //Fill initial heap
	    while(scanner.hasNext()){
		line = scanner.nextLine();
		if(!line.equals("")) {
				String[] splitLine = line.split(" ");
				overLap = new String[splitLine.length];
				int oLi = 0;
				for(int i = 0; i < splitLine.length; i++) {
					if(!(splitLine[i] == null || splitLine[i].equals("") || splitLine[i].equals(" "))) {
						if(!heap.isFull()) {
						heap.push(splitLine[i]);
						}
						else {	//as we are splitting the line we need to keep track of the rest of the line that doesnt fit in the heap
							overLap[oLi] = splitLine[i];
							oLi++;
						}
					}
				}
				if(heap.isFull()) {break;}
			}
	    }
	    File outputFile = new File(outputFilepath);	//This is the file we will write the final output data to
	    PrintWriter writer = new PrintWriter(new FileWriter(outputFile.getParent() + File.separator + "temp.txt"));	//create new temp file in the same dirrectory as the output file

	    try {
	    //Generate runs
	    do {
	    	for(int i = 0; i < overLap.length; i++) {
				if(!(overLap[i] == null || overLap[i].equals("") || overLap[i].equals(" "))) {
					if(heap.isNewRun()){
				    	heap.reheap();
				    	run.writeToFile(writer);	//*
				    	totalRuns++;
				    	run = new Run();
					}
					if(!heap.isNewRun()) {
				    	run.append(heap.pop());
				    	if(overLap[i].compareTo(run.peekEnd()) <= 0){
				    		heap.place(overLap[i]);
						}
						else{
					    	heap.push(overLap[i]);
						}
					}
				}
				else { break; } 
			}
			line = scanner.nextLine();
	    	if(!line.equals("")) {
				String[] splitLine = line.split(" ");
				overLap = new String[splitLine.length];
				int overLapIndex = 0;
				for(int i = 0; i < splitLine.length; i++) {
					if(!(splitLine[i] == null || splitLine[i].equals("") || splitLine[i].equals(" "))) {
						if(heap.isNewRun()){
					    	heap.reheap();
					    	run.writeToFile(writer);	//*
					    	totalRuns++;
					    	run = new Run();
						}
						if(!heap.isNewRun()) {
					    	run.append(heap.pop());
					    	if(splitLine[i].compareTo(run.peekEnd()) <= 0){
						    	heap.place(splitLine[i]);
							}
							else{
						    	heap.push(splitLine[i]);
							}
						}
						else {
							overLap[overLapIndex] = splitLine[i];
							overLapIndex++;
						}
					}
				}	
			}		
	    } while(scanner.hasNext());
	    //empty the heap after file is fully read
		while(!heap.isEmpty()){
		    if(heap.isNewRun()){
			heap.reheap();
			run.writeToFile(writer);	//*
			totalRuns++;
			run = new Run();
		    }
		    if(run.isEmpty() || !(run.peekEnd().compareTo((heap.peek().stringValue)) > 0)){
			run.append(heap.pop());			
		    }
		}

		run.writeToFile(writer);	//*
		totalRuns++;

		scanner.close();
	    }
	    finally{
	    	writer.close();    
	    }

		fibList = new FibList(maxFileNum,totalRuns);

		//Distribute runs into files
		    File tempFile = new File(outputFile.getParent() + File.separator + "temp.txt");
		    Scanner sc = new Scanner(tempFile);
		    
		    // Gets the distribution based on max number of files
		    int[] fibConfig = new int[maxFileNum];
		    
		    int zero = fibList.findZero();
		    int currFileIndex = 0;		    
		    if(zero == 0){currFileIndex = 1; }
		    
		    // Opens first file to output runs to
		    FileWriter currentOutput = new FileWriter(outputFile.getParent() + File.separator + "output" + currFileIndex + ".txt");
		    writer = new PrintWriter(currentOutput);
		    
		    
		    System.err.println("Run distribution: " + Arrays.toString(fibList.runs));	        
		    while(sc.hasNext()) {		        
		        line = sc.nextLine();		        
		        if (line.equals("*")) {
		            writer.println("*");
		            fibConfig[currFileIndex]++;
		            if (fibConfig[currFileIndex] == fibList.runs[currFileIndex]){ // if the correct amount of runs has been distributed move to the next file
		                currFileIndex++;		                
		                if (currFileIndex == zero) { //want file 0 to stay empty, so move to next file
		                    currFileIndex++;
		                }
		                
		                // Open next output file
		                currentOutput = new FileWriter(outputFile.getParent() + File.separator + "output" + currFileIndex + ".txt");
		                writer.close();
		                writer = new PrintWriter(currentOutput);
		            }
		        }
		        else {
		            writer.println(line);
		        }
		    }

		    for(;;){
		        writer.println("*");
		        fibConfig[currFileIndex]++;		        
		        if (fibConfig[currFileIndex] == fibList.runs[currFileIndex]) {
		            currFileIndex++;
		            if (currFileIndex == zero) currFileIndex++;
		            currentOutput = new FileWriter(outputFile.getParent() + File.separator + "output" + currFileIndex + ".txt");
		            writer.close();
		            writer = new PrintWriter(currentOutput);
		        }
		        if (currFileIndex >= maxFileNum) break;
		    }
		    
		    currentOutput = new FileWriter(outputFile.getParent() + File.separator + "output" + zero + ".txt");
            writer.close();
            writer = new PrintWriter(currentOutput);
		    
		    // Closes last writer and scanner
		    writer.close();
		    sc.close();
		    
		    SortRuns(zero, outputFile);
		    
    }
    
    public static void SortRuns(int zero, File outputFile) throws IOException {
		    
    	File firstOut = outputFile;
		    Heap OutputHeads = new Heap(maxFileNum);
	        List<RunScanner> scanners = new ArrayList<RunScanner>();
	        
	        // Find the file that will be open to input and create a writer for that file
	        File input = new File(outputFile.getParent() + File.separator + "output" + zero + ".txt");
	        PrintWriter inputWriter = new PrintWriter(new FileWriter(input));
	        
	        // Create a scanner for each output file we will be using.
	        for (int i = 0; i <= maxFileNum; i++) {
	            outputFile = new File(outputFile.getParent() + File.separator + "output" + i + ".txt");
	            Scanner sc1 = new Scanner(outputFile);
	            RunScanner sn = new RunScanner(sc1, i);
	            scanners.add(sn);
	        }
	        
	        // Remove text from a file until each file has processed an entire run
	        int RunsToRemove = fibList.fib[0];
	        
	        while (RunsToRemove > 0){
	            
	            RunsToRemove--;
	            
	            // Create the heap by storing the head of each run that is open for output
	            for (RunScanner sn : scanners){
	                if (sn.outputID == zero){ continue; }
	                if (sn.next() != null) OutputHeads.pushWithLocation(sn.line, sn.outputID);
	            }
	            
	            // Continue while the heap still has values in it
	            while(!OutputHeads.isEmpty()){
	                
	                Node n = OutputHeads.pop();
	                
	                if (!n.stringValue.equals("*")) { 
	                    inputWriter.println(n.stringValue);
	                    inputWriter.flush();
	                    for (RunScanner sn : scanners){
	                        if (sn.outputID == n.Output){
	                            OutputHeads.pushWithLocation(sn.next(), sn.outputID);
	                        }
	                    }					
	                }	
	            }
	            inputWriter.println("*");
	            inputWriter.flush();
	            
	        }
	        
	        inputWriter.close();
	        
	        for (RunScanner sn : scanners) {
	            if (sn.outputID != zero) {
	                refactorRuns(outputFile.getParent() + File.separator + "output" + sn.outputID + ".txt", fibList.fib[0]);
	            }
	            sn.scanner.close();
	        }
	        
	        zero--;
	        if(zero < 0) zero = maxFileNum;
	        
	        
	        
	        if(fibList.fib[0] == 0) { 
	            for (int i = 1; i <= maxFileNum + 1; i++){
	                File newFile = new File(outputFile.getParent() + File.separator + "output" + i + ".txt");
	                newFile.delete();
	            }
	            
	            File temp = new File(outputFile.getParent() + File.separator +  "temp.txt");
	            temp.delete();
	            
	            File file = new File(outputFile.getParent() + File.separator + "output0.txt");
	            File fileToRename = new File(outputFilepath);
	            file.renameTo(fileToRename);
	            System.err.println("Sort Complete After " + operations + " Passes.");
	        }
	        else {
	            fibList.lastFib();
	            operations++;
	            System.err.println("Sort Operations Completed: " + operations);
	            SortRuns(zero, firstOut);
	}
		    
		    
		}
    
 public static void refactorRuns(String filename, int runsMerged) throws IOException{
	 
        File tempFile = new File("temp2.txt");
        PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
        
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        String line;
        
        //Count until end of merged runs
        while (runsMerged != 0) {
            
            if (!sc.hasNext()){ runsMerged--; }
            else{
                line = sc.nextLine();
                if (line.equals("*")) { runsMerged--; }
            }
        }
        //write the rest of the run to the temp file
        while (sc.hasNext()) {
            writer.println(sc.nextLine());
        }        
        //close scanner and writer
        sc.close();
        writer.close();
        
        //put temp file contents in original file
        File originalfile = new File(filename);
        PrintWriter finalwriter = new PrintWriter(new FileWriter(originalfile));
        Scanner scan = new Scanner(tempFile);
        
        while (scan.hasNext()){
            finalwriter.println(scan.nextLine());
        }
        //closer readers, writers and delete temp file
        scan.close();
        tempFile.delete();
        finalwriter.close();
        
    }
}
