package minHeap;

public class Run {

	private int size; // size of the current run
	private arrayList<String> run; // array list to store a run
	

	public Run() {
		
		run = new arrayList<String>();
		size = 0; //set the inital size to zero, empty
	}

	//Writes each run to the same file, seperated by an '*'
	public void writeToFile(String fileDirectory) throws IOException {
		
		File outputFile = new File(fileDirectory + File.seperator + "temp.txt");
		PrintWriter writer = new PrintWriter(new FileWriter(outputFile, true));

		for(String s : run){
			writer.println(s);
		}

		writer.println("*");

		writer.close();
	}

	//check if the run is empty
	public boolean isEmpty() {

		if(size >= 1){
			return false;
		}
		else{
			return true;
		}
	}
	
	//return the last item in the run
	public String peekEnd() {
		
		String last = run.get(run.size()-1) 
		return last;
	}

	//add a node to the run
	public void append(Node pop) {

		run.add(pop);
		size++;
		
	}

}
