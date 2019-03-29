package minHeap;

import java.util.scnaner;

public class ScannerNode{

	public Scanner sc;
    public int outputID;
	public String next = null;
	
	public Scannernode(Scanner s, int id){
		Scanner = s;
		outputID = id;
		
	}

	public String next(){
		
		if(sc.hasNext()){
			next = sc.nextLine();
			return next;
		}
		
		return null;
	}
}
