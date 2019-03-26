package minHeap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Run {

	private int size; // size of the current run
	private ArrayList<String> run; // array list to store a run
	private PrintWriter myWriter;

	public Run() {
		
		run = new ArrayList<String>();
		size = 0; //set the inital size to zero, empty
	}

	//Writes each run to the same file, seperated by an '*'
	public void writeToFile(PrintWriter writer) throws IOException {
		
		myWriter = writer;		

		for(String s : run){
			myWriter.println(s);
		}

		myWriter.println("*");
		myWriter.flush();
		myWriter.close();
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
		
		String last = run.get(run.size()-1);
		return last;
	}

	//add a node to the run
	public void append(Node pop) {

		run.add(pop);
		size++;
		
	}

}
