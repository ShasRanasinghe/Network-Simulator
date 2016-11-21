package network;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

public class AlgorithmTest {
	
	private Node n1;
	private Node n2;
	private Node n3;
	
	private ArrayList<Node> nodes;
	
	private FloodingAlgorithm flooding;
	private ShortestPathAlgorithm shortestPath;
	
	@Before
	public void setUp()
	{
		n1 = new Node("a");
		n2 = new Node("b");
		n3 = new Node("c");
		
		n1.addNeighbor(n2);
		n1.addNeighbor(n3);
		
		n2.addNeighbor(n1);
		
		n3.addNeighbor(n1);

		nodes = new ArrayList<Node>();
		nodes.add(n1);
		nodes.add(n2);
		nodes.add(n3);

		flooding = new FloodingAlgorithm(nodes, 3);
		shortestPath = new ShortestPathAlgorithm(nodes, 3);
		
	}

	
	@Test
	public void testFlooding() 
	{
		Message m = flooding.completeMessageList.get(0);
		ArrayList<Node> test = m.getSource().getNeighbors();
		
		flooding.run(1);

		if(m.getCurrent().contains(m.getDestination()))
		{
			assertTrue(test.contains(m.getDestination()));
		}
		else
		{
			assertEquals(test,m.getCurrent());
		}
	}
	
	@Test
	public void testShortestPath() 
	{
		Message m = shortestPath.getMessage();
		String s = "" + m.getSourceID() + m.getDestinationID();
		
		assertTrue(shortestPath.paths.containsKey(s));
	}

}
