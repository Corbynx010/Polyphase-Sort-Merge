package minHeap;

import java.io.*;
import java.util.Scanner;

public class Main {
    /*
      /R -- Defines max run size
      /F -- Defines the max file number
      /O -- Defines Output Filepath
      /I -- Defines Input Filepath
      Format "Polymerge /R 77 /O c:/users/test.txt"
     */
    public static void main(String[] args){
        int runSize = 0;
        int maxFileNum = 0;
        String outputFilepath = "";
        String inputFilepath = "";
	//Deal with input arguments
	if (args.length == 0){
	    System.out.println("Please input arguments i.e. \"Polymerge /R 77 /O c:/users/test.txt\"");
	    return;
	}
	for(int i = 0; i <= args.length; i++){
	    try{
	    /*if(args[i].equalsIgnoreCase("//R")){
	    	runSize = Integer.parseInt(args[i+1]);
	    }*/
	    if(args[i].equalsIgnoreCase("//P")){
	    	maxFileNum = Integer.parseInt(args[i+1]);
	    }
	    else if(args[i].equalsIgnoreCase("//O")){
	    	outputFilepath = args[i+1];
	    }
	    else if(args[i].equalsIgnoreCase("//I")){
	    	inputFilepath = args[i+1];
	    }
	    }catch(Exception e){
		System.err.println("Please input valid input arguments");
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

	    //Fill initial heap
	    while(scanner.hasNext() && !heap.isFull()){
		line = scanner.nextLine();
		heap.push(line);
	    }
	    
	    File outputFile = new File(outputFilepath);
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile.getParentDirectory() + File.seperator + "temp.txt"));
	    
	    try
	    {
	    while(scanner.hasNext()) {
		if(heap.isNewRun()){
		    heap.reheap();
		    run.writeToFile(writer);	//*
		    totalRuns++;
		    run = new Run();
		}
		if(run.isEmpty() || !(run.peekEnd().compareTo((heap.peek().Key)) >= 0)){
		    run.append(heap.pop());
		    line = scanner.nextLine();
		    if(line.compareTo(run.peekEnd()) >= 0){
			    heap.place(line);
			}
			else{
			    heap.push(line);
			}
		}
	    }

		while(!heap.isEmpty()){
		    if(heap.isNewRun()){
			heap.reheap();
			run.writeToFile(writer);	//*
			totalRuns++;
			run = new Run();
		    }
		    if(run.isEmpty() || !(run.peekEnd().compareTo((heap.peek().Key)) >= 0)){
			run.append(heap.pop());			
		    }
		}

		run.writeToFile(writer);	//*
		totalRuns++;

		scanner.close();
	    }finally{
		writer.close;    
	    }

		File outputFile = new File(outputFilepath);
		distributeRuns();
		polyPhase();
		//write to output
    }
}
