package minHeap;

public class Heap {
	private Node[] heap;
    private int size;
    private int capacity;
    private int currentUndersize;
    
    public Heap(int maxSize){
	heap = new Node[maxSize];
	size = 0;
	currentUndersize = 0;
	capacity = maxSize;	
    }

    public void push(String s){	
	size = size+1;
	Node n = new Node(s);
	heap[size] = n;
	int current = size;
	upheap(current);
    }
    
    public void pushWithLocation(String s, int o){	
    	size = size+1;
    	Node n = new Node(s, o);
    	heap[size] = n;
    	int current = size;
    	upheap(current);
        }

    public void place(String s){
	currentUndersize++;
	Node n = new Node(s);
	heap[capacity-currentUndersize] = n;
    }

    private void swap(int n1, int n2){
	Node n = heap[n1];
	heap[n1] = heap[n2];
	heap[n2] = n;
    }

    private void upheap(int nodeRef){
	while(nodeRef > 0 && isSmallerThan(nodeRef, nodeRef/2)){
		swap(nodeRef, nodeRef/2);
		nodeRef = nodeRef/2;
	    }
    }

    private void downheap(int nodeRef){
	while((nodeRef*2) <= size){
	    int minChildRef = minChild(nodeRef);

	    if(!isSmallerThan(nodeRef, minChildRef)){
		swap(nodeRef, minChildRef);
	    }
	    else{return;}
	    nodeRef = minChildRef;
	}
    }

    private int minChild(int nodeRef){
	if ((nodeRef * 2) + 1 > size) {
	    return nodeRef * 2;
	}
        if (isSmallerThan(nodeRef*2, (nodeRef*2)+1)){
	    return nodeRef * 2;
	}
	else{
	    return (nodeRef * 2) + 1;
        }
    }

	public boolean isNewRun(){
	if(size <= 0 && currentUndersize > 0){
	    return true;
	}
	return false;
	}

	public boolean isEmpty(){
	if(size + currentUndersize == 0){
	    return true;
	}
	return false;
	}

	public void reheap(){
	Heap newHeap = new Heap(capacity);
	for(int i = 0; i < capacity; i++){
	    if(heap[i] != null){
		newHeap.push(heap[i].Key);
	    }
	}
	heap = newHeap.heap;
	currentUndersize = newHeap.currentUndersize;
	size = newHeap.size;
	}

    public Node pop(){
	Node returnNode = heap[1];
	heap[1] = heap[size];
	heap[size] = null;
	size = size-1;
	downheap(1);
	return returnNode;
    }

    public Node peek(){
	Node tempNode = new Node(heap[1].Key);
	return tempNode;
    }

    private boolean isSmallerThan(int n1, int n2){
    	if(n2 == 0) { return false; }
	if(heap[n1].Key.compareTo(heap[n2].Key) <= 0){
	    return true;
	}
	return false;
    }

    public boolean isFull(){
	if(size + currentUndersize >= capacity -1){
	    return true;
	}
	else{
	    return false;
	}
    }
}
