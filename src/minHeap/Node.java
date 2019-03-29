package minHeap;

public class Node {

	public String Key;
	public int Output;

	public Node(String s) {
		Key = s;
	}
	
	public Node(String s, int output) {
		Key = s;
		Output = output;
	}
	
	public String getKey() { return Key; }

}
