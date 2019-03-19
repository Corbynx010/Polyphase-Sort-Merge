//Liam Rodgers 1248912
//Corbyn Noble-May 1314639

package minHeap

public class FibList{

	public int[] fib;
	private int numFiles;
	public int[] runs

	public FibList(int filesNum, int totalRuns){
		numFiles = filesNum; // the number of files given to allocate the runs.
		runs = new int[numFiles]; // distributed runs array, each index represents a file for allocating runs.
		fib = new int[2]; // array to store the current and next fibbonaci numbers.
		fib[0] = 1;
		fib[1] = 1;
	}

	int i = 0;
	
	//distrube 1 run to each file.
	while(i < numFiles){
		runs[i] = 1;
		i++
	}
	runs[0] = 0; //set the first file to have zero runs, while the rest still have one, this is the end case we want to result in.

	while(Distribute() < totalRuns){
		nextLayer();
	}
	
	//returns the total number of runs distributed across the files.
	private int Distribute(){
		int total = 0;
		for(int i : runs[]) // for every file add the number of runs to the total.
		total += i;
		
		return total;
	}
	
	//
	public void nextLayer(){
		int i = 0;
		int z = findZero(); // get the index containig 0 runs.
		nextFib();// generate next fibbonaci number.
		int fib = fib[0];// get next fibbonaci number.
		
		//iterate through files distrubting runs equal to current fibonacci number to each file
		while(i < numfiles){
			runs[i] += fib; //initial number = second 1.
		}

		//we dont want to set the same file that was zero to be zero again so we instead set the next file to be zero,
		//if the next file will exceed the boundaries of our array set the first file to zero instead
		if((z+1) == numFiles){
			runs[0] = 0;
		}
		else{
			runs[z+1] = 0;
		}
		
	}
	//method used to set the next number in the fibonnaci sequence
	private void nextFib(){
		int temp = fib[1];
		fib[1] = fib[0] + fib[1];
		fib[0] = temp;
	}

	//iterate through files finding which has no current runs
	public int findZero()
	{
		int i = 0;
		
		while(true){
			if(runs[i] == 0) return i;
			i++;
		}
	}
}
