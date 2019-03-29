package polyphaseSortMerge;

import java.util.Scanner;

public class RunScanner{
	public Scanner scanner;
    public int outputID;
	public String line = null;
	
	public RunScanner(Scanner s, int id){
		scanner = s;
		outputID = id;
		
	}

	public String next(){		
		if(scanner.hasNext()){
			line = scanner.nextLine();
			return line;
		}
		
		return null;
	}
}
