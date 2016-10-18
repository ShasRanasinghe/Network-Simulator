package network;

import java.util.*;

/**
 * @author Alex Hoecht(100933730)
 * 
 * Iteration 1: Implementing the Random algorithm
 * 
 * The following algorithms will be used by the Network:
 * 		- Random: When a router receives a message, it forwards it to a random neighbor;
 * 		- Flooding: When a router receives a message, it forwards it to all of its neighbors except the one
 * 					it came from. But must avoid infinite forward loops!
 * 		- Shortest path: The routing tables are configured globally to deliver messages using the least
 * 						amount of intermediate nodes (a.k.a hops), given the complete knowledge of the network. This
 * 						can be done using a depth-first search.
 * 		- Choose your own Algorithm: Feel free to investigate existing routing algorithms or come
 * 						up with one of your own imagination (but in either case you need to document it precisely).
 * 		Don’t worry: we don’t expect you to come up with the best possible algorithm; just something
 * 					that might reasonably be better than Random or Flooding.
 *
 */
public interface RoutingAlgorithms {

	/**
	 * The default behavior of when a class that implements the RoutingAlgorithms
	 * calls runAlgorithm
	 * 
	 * @param	source	- The vertex the algorithm will start at.
	 * 			destination	- The vertex that the message will end up at.
	 * 			algorithm	-An enum telling the interface which algorithm to run.
	 * 
	 */
	public default void runAlgorithm(Node source, Node destination, ALGORITHM algorithm) 
	{
		switch(algorithm)
		{
			// The message will be forwarded to ONE RANDOM neighbor
			case RANDOM:
				
				Random random = new Random();
				// Select a random edge from the source vertex
				Edge temp = source.getNeighbor(random.nextInt(source.getNeighborhoodsize()));
				
				// The message will be forwarded to the destination of the random edge
				Node tempD = temp.getDestination();
				
				//////////////////////////////////////////////////////////////////////
				//		INSERT SOURCE VERTEX AND TEMPD INTO TABLE
				//////////////////////////////////////////////////////////////////////
				
				// End of case
				break;
				
				
			// The message will be forwarded to ALL neighbors, EXCEPT the one it came from	
			case FLOODING:

				
				
				// End of case
				break;
			
				
			// The message will be forwarded across the shortest path to get to a node(Depth-first)
			case SHORTESTPATH:

				
				
				// End of case
				break;
			
				
			// Undecided on what our own algorithm will be
			case CUSTOM:

				
				
				// End of case
				break;
				
		}
		
	}


}
