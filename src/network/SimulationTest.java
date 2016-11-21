package network;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class SimulationTest {

	Node node1 = null;
	Node node2 = null;
	Simulation simulation1;
	ArrayList<Node> nodes;
	String[] list;
	String[] edgelist;
	
	ArrayList<String> temp;
	
	@Before
	public void setUp() 
	{
		simulation1 = new Simulation();
		node1 = new Node("A");
		node2 = new Node("B");
		nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		simulation1.setSimulationNodes(nodes);
		
		
		list = new String[2];
		list[0] = "A";
		list[1] = "B";
		
		edgelist = new String[2];
		edgelist[0] = "A->B";
		edgelist[1] = "B->A";
		
		String s = "69";
		temp = new ArrayList<String>();
		temp.add(s);
	}
	
	@Test
	public void testCreateNodes() {
		simulation1.createNodes(list);
		assertTrue(simulation1.getSimulationNodes().equals(nodes));
	}


	@Test
	public void testCreateLink() {
		simulation1.createLink("A","B");
		assertTrue(node1.containsNeighbor(node2) && node2.containsNeighbor(node1));
	}

	@Test
	public void testAddNeighbors() {
		simulation1.addNeighbors(edgelist);
		assertTrue(node1.containsNeighbor(node2) && node2.containsNeighbor(node1));
	}

	@Test
	public void testGetSimulationNodes() {
		simulation1.setSimulationNodes(nodes);
		assertTrue(simulation1.getSimulationNodes().equals(nodes));
	}

	@Test
	public void testSetSimulationNodes() {
		simulation1.setSimulationNodes(nodes);
		assertTrue(simulation1.getSimulationNodes().equals(nodes));
	}

	@Test
	public void testGetAverageHops() {
		simulation1.setAverageHops(temp);
		assertEquals(temp,simulation1.getAverageHops());
	}

	@Test
	public void testSetAverageHops() {
		simulation1.setAverageHops(temp);
		assertEquals(temp,simulation1.getAverageHops());
	}

	@Test
	public void testGetPackets() {
		assertEquals(0,simulation1.getPackets());
	}

	@Test
	public void testSetPackets() {
		simulation1.setPackets(45);
		assertEquals(45,simulation1.getPackets());
	}

	@Test
	public void testSetTotalMessages() {
		simulation1.setTotalMessages(427);
		assertEquals(427,simulation1.getTotalMessages());
	}
	
	@Test
	public void getTotalMessages()
	{
		assertEquals(0,simulation1.getTotalMessages());
	}

	@Test
	public void testSetFrequency() {
		simulation1.setFrequency(47);
		assertEquals(47,simulation1.getFrequency());
	}

	@Test
	public void testGetFrequency() {
		simulation1.setFrequency(28);
		assertEquals(28, simulation1.getFrequency());
	}

	@Test
	public void testSetAlgorithm() {
		simulation1.setAlgorithm(ALGORITHM.FLOODING);
		assertEquals(ALGORITHM.FLOODING, simulation1.getAlgorithm());
	}

	@Test
	public void testGetAlgorithm() {
		simulation1.setAlgorithm(ALGORITHM.SHORTESTPATH);
		assertEquals(ALGORITHM.SHORTESTPATH, simulation1.getAlgorithm());
	}
	
	
	@Test
	public void testRemoveLink() {
		simulation1.createLink("A","B");
		simulation1.removeLink("B","A");
		assertFalse(node1.getNeighbors().contains(node2));
		
	}

	@Test
	public void testGetNodeGivenID() {
		assertTrue(node1.equals(simulation1.getNodeGivenID("A")));
	}
}