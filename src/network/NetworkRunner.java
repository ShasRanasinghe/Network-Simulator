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
		View view = new View();
		networkSimulation.addObserver(view);
		
		Controller controller = new Controller(networkSimulation, view);
		
		view.setController(controller);
	}

}
