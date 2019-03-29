package minHeap;

import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class Main {
    /*
      /R -- Defines max run size
      /F -- Defines the max file number
      /O -- Defines Output Filepath
      /I -- Defines Input Filepath
      Format "Polymerge /R 77 /O c:/users/test.txt"
     */
    public static void main(String[] args) throws IOException{
        int runSize = 0;
        int maxFileNum = 0;
        String outputFilepath = "";
        String inputFilepath = "";
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
				int oLi = 0;
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
							overLap[oLi] = splitLine[i];
							oLi++;
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
		    if(run.isEmpty() || !(run.peekEnd().compareTo((heap.peek().Key)) > 0)){
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

		FibList fibList = new FibList(maxFileNum,totalRuns);

		//PolyPhase is just the merge sort of the already created and sorted runs.

		//polyPhase();
		//distributeRuns();
		//write to output

		public static void distributeRuns(){
        //*
		    // Opens the temp file runs are stored in
		    File tempFile = new File(outputFile.getParent() + File.separator + "temp.txt");
		    Scanner sc = new Scanner(tempFile);
		    
		    // Gets the distribution int [] to use to set the correct amount of runs
		    int[] fibConfig = new int[maxFileNum + 1]; // why +1?
		    
		    // Location by index of the first zero lengthfile
		    int zero = fibList.findZero();
		    int currentFile = 0;
		    
		    if(zero == 0){currentFile = 1; }
		    
		    // Opens first file to output runs to
		    File currentOutput = new File(outputFilepath + File.separator + "output" + currentFile + ".txt");
		    writer = new PrintWriter(new FileWriter(currentOutput, true));
		    
		    
		    System.err.println("Initial distribution of runs: " + Arrays.toString(fibList.runs));
		    
		    // While the tempFile isn't empty, runs are distributed across the output files
		    while(sc.hasNext()) {
		        
		        line = sc.nextLine();
		        
		        if (line.equals("*")) {
		            writer.println("*");// are we keeping these?
		            fibConfig[currentFile]++;
		            if (fibConfig[currentFile] == fibList.runs[currentFile]){ // if the correct amount of runs has been distributed? move to the next file
		                currentFile++;
		                if (currentFile == zero) { //want index zero to stay empty, so move to next file
		                    currentFile++;
		                }
		                
		                // Opens next output file when correct amount of runs are added to the last
		                currentOutput = new File(outputFilepath + File.separator + "output" + currentFile + ".txt");
		                writer.close();
		                writer = new PrintWriter(new FileWriter(currentOutput, true));
		            }
		        }
		        else {
		            writer.println(line);
		        }
		    }
		    
		    
		    // Creates the correct amount of dummy runs by adding "*" to the currentOutput file
		    while(true){
		        writer.println("*");
		        fibConfig[currentFile]++;
		        
		        if (fibConfig[currentFile] == fibList.runs[currentFile]) {
		            currentFile++;
		            if (currentFile == zero) currentFile++;
		            
		            // Opens new output file in the correct amount of dummy runs haven't been added yet
		            currentOutput = new File(outputFilepath + File.separator + "output" + currentFile + ".txt");
		            writer.close();
		            writer = new PrintWriter(new FileWriter(currentOutput, true));
		        }
		        if (currentFile > maxFileNum) break;
		    }
		    
		    // Closes last writer and scanner
		    writer.close();
		    sc.close();//*/
		}

    }
}
