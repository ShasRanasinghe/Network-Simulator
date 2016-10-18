package network;
/**
 * 
 * This class creates a simulation of the Network
 * 
 * @author	Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe
 * @version 1.0
 */

public class Simulation {
	
	//Metrics:
	private int hops;
	private int packets;
	
	/**
     * Constructor initializes the simulation
     */
	public Simulation(){
		hops = 0;
		packets = 0;
	}

	/**
	 * @return The average number of hops each message goes through from start to end
	 */
	public int getHops() {
		return hops;
	}

	/**
	 * @param hops Set the number of hops of a message
	 */
	public void setHops(int hops) {
		this.hops = hops;
	}

	/**
	 * @return Total number of packets transmitted
	 */
	public int getPackets() {
		return packets;
	}

	/**
	 * @param packets Set the number of packets transmitted
	 */
	public void setPackets(int packets) {
		this.packets = packets;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

	}
}
