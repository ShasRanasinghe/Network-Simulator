package network;

public class Table {
	
	private Node current;	// Current node in pair
	private Node next;		// Next node to receive the message
	
	/**
	 * A small object class that allows the creation of Node pairs to 
	 * be stored in the graph table.
	 * 
	 * @param c - The Current node the algorithm is on
	 * @param n - The Next node the message will be forwarded to
	 */
	public Table(Node c, Node n)
	{
		this.current = c;
		this.next = n;
	}

}
