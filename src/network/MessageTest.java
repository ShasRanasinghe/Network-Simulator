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
	public void testSetHopCount() {
		message1.setHopCount(27);
		assertEquals(27,message1.getHopCount());
	}

	@Test
	public void testIncrumentHopCount() {
		message1.incrumentHopCount();
		assertEquals(1,message1.getHopCount());
	}

}
