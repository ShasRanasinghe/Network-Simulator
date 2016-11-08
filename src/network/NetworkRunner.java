package network;

/**
 * @author Shasthra Ranasinghe, Alex Hoecht
 * 
 * Runs the simulation
 *
 */
public class NetworkRunner {
	
	public static void main(String args[])
	{
		Simulation networkSimulation = new Simulation();
		new View(networkSimulation);
	}

}
