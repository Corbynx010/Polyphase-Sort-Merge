//Liam Rodgers 1248912
//Corbyn Noble-May 1314639

package polyphaseSortMerge;

public class Node {

	public String stringValue;
	public int Output;

	public Node(String s) {
		stringValue = s;
	}
	
	public Node(String s, int output) {
		stringValue = s;
		Output = output;
	}
	
	public String getKey() { return stringValue; }

}
