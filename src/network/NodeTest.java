package network;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NodeTest {
	
	private Node node1 = null;
	private Node node2 = null;
	private Node node3 = null;
	
	@Before
	public void setUp() 
	{
		node1 = new Node("A");
		node2 = new Node("B");
		node3 = new Node("C");
		node1.addNeighbor(node2);	
	}

	@Test
	public void testNode() {
		Node node = new Node(node1);
		assertTrue(node.equals(node1));
	}

	@Test
	public void testAddNeighbor() {
		node1.addNeighbor(node3);
		assertEquals(2,node1.getNeighborhoodsize());
	}

	@Test
	public void testContainsNeighbor() {
		assertTrue(node1.containsNeighbor(node2));
	}

	@Test
	public void testRemoveNeighbor() {
		node1.addNeighbor(node3);
		node1.removeNeighbor(node2);
		assertEquals(1,node1.getNeighborhoodsize());
	}

	@Test
	public void testGetNeighborhoodsize() {
		node1.addNeighbor(node3);
		assertEquals(2,node1.getNeighborhoodsize());
	}

	@Test
	public void testtoString() {
		String a = "A";
		assertEquals(a,node1.toString());
	}
}

