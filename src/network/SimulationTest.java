package network;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class SimulationTest {
	Node node1 = null;
	Node node2 = null;
	Simulation simulation1;
	ArrayList<Node> nodes;
	ArrayList<String> list;
	ArrayList<String> edgelist;
	
	@Before
	public void setUp() 
	{
		simulation1 = new Simulation();
		node1 = new Node("A");
		node2 = new Node("B");
		nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		
		
		list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		
		edgelist = new ArrayList<String>();
		edgelist.add("A->B");
		edgelist.add("B->A");
	}

	@Test
	public void testSimulation() {
		Simulation simulation = new Simulation(simulation1);
		assertTrue(simulation.equals(simulation1));
	}

	@Test
	public void testCreateNodes() {
		simulation1.createNodes(list);
		assertTrue(simulation1.getSimulationNodes().equals(nodes));
	}

	@Test
	public void testCreateLink() {
		simulation1.createLink(node1, node2);
		Edge e = new Edge(node1.getID() + "->" + node2.getID(),node1,node2);
		Edge e2 = new Edge(node2.getID() + "->" + node1.getID(),node2,node1);
		assertTrue(node1.containsNeighbor(e) && node2.containsNeighbor(e2));
	}

	@Test
	public void testAddNeighbors() {
		simulation1.addNeighbors(nodes, edgelist);
		Edge e = new Edge(node1.getID() + "->" + node2.getID(),node1,node2);
		Edge e2 = new Edge(node2.getID() + "->" + node1.getID(),node2,node1);
		assertTrue(node1.containsNeighbor(e) && node2.containsNeighbor(e2));
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
		assertEquals(0,simulation1.getAverageHops());
	}

	@Test
	public void testSetAverageHops() {
		simulation1.setAverageHops(69);
		assertEquals(69,simulation1.getAverageHops());
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

}
