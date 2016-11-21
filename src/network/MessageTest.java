package network;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MessageTest {
	
	Message message1 = null;
	Node node1 = null;
	Node node2 = null;
	
	@Before
	public void setUp() 
	{
		node1 = new Node("A");
		node2 = new Node("B");
		message1 = new Message(node1,node2);
	}

	@Test
	public void testMessage() {
		Message message = new Message(message1);
		assertTrue(message.equals(message1));
	}

	@Test
	public void testGetSource() {
		assertTrue(node1.equals(message1.getSource()));
	}

	@Test
	public void testSetSource() {
		message1.setSource(node2);
		assertTrue(node2.equals(message1.getSource()));
	}

	@Test
	public void testGetDestination() {
		assertTrue(node2.equals(message1.getDestination()));
	}

	@Test
	public void testSetDestination() {
		message1.setDestination(node1);
		assertTrue(node1.equals(message1.getDestination()));
	}

	@Test
	public void testGetHopCount() {
		assertEquals(0,message1.getHopCount());
	}

	@Test
	public void testIncrumentHopCount() {
		message1.incrementHopCount();
		assertEquals(1,message1.getHopCount());
	}
	
	@Test
	public void testtoString() {
		message1.setName("john");
		assertEquals("john",message1.toString());
	}

	@Test
	public void testSetName() {
		message1.setName("shazmaniadevil");
		assertEquals("shazmaniadevil",message1.toString());
	}

	@Test
	public void testGetCurrentNode() {
		assertTrue(message1.getCurrent().get(0).equals(node1));
	}
}