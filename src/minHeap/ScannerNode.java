package minHeap;

import java.util.Scanner;

public class ScannerNode{

	public Scanner sc;
    public int outputID;
	public String line = null;
	
	public ScannerNode(Scanner s, int id){
		sc = s;
		outputID = id;
		
	}

	public String next(){
		
		if(sc.hasNext()){
			line = sc.nextLine();
			return line;
		}
		
		return null;
	}
}
