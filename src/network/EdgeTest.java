package network;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EdgeTest {
	
	Node node1 = null;
	Node node2 = null;
	Node node3 = null;
	Node node4 = null;
	
	Edge edge1 = null;
	Edge edge2 = null;
	
	@Before
	public void setUp() 
	{
		node1 = new Node("A");
		node2 = new Node("B");
		edge1 = new Edge("A->B",node1,node2);
		
		node3 = new Node("C");
		node4 = new Node("D");
		edge2 = new Edge("C->D",node3,node4);
				
	}

	@Test
	public void testEdge() {
		Edge edge = new Edge(edge1);
		assertTrue(edge.equals(edge1));
	}

	@Test
	public void testGetSource() {
		assertTrue(node1.equals(edge1.getSource()));
	}

	@Test
	public void testGetDestination() {
		assertTrue(node2.equals(edge1.getDestination()));
	}

	@Test
	public void testGetId() {
		String a = "A->B";
		assertEquals(a,edge1.getId());
	}

	@Test
	public void testSetId() {
		String a = "C->D";
		edge1.setId("C->D");
		assertEquals(a,edge1.getId());
	}

}