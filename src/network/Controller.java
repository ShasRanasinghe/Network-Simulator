package network;

import java.util.*;

public class Controller {
	private Scanner in;
	private String inputLine;
	private Scanner tokenizer;
	private boolean initializingDone = false;
	private boolean destinationReached = false;

	public static void main(String[] arg){
		Controller cont = new Controller();
	}
	
	public Controller(){
		startProgram();
		createNewNetwork();
		for(;;){
			while(!initializingDone){
				initializeNetwork();
			}
			requestHeadNode();
			runSimulation();
			endSimulation();
			for(;;){
				System.out.println("Would you like to use the same Network again(Y/N)");
				inputLine = inputLine(in.nextLine());
				if(inputLine.equals("Y")){
					break;
				}else if(inputLine.equals("N")){
					createNewNetwork();
					break;
				}
			}
		}
	}

	private void endSimulation() {
		initializingDone = false;
		destinationReached = false;
		//TODO
		in.close();
	}

	private void startProgram() {
		System.out.println("Starting New Program...");
		System.out.println("Enter \"quit\" at any time to exit the program");
		
	}
	
	private void createNewNetwork() {
		in = new Scanner(System.in);
		System.out.println("Creating a new Network...");
		
		System.out.print("Enter Root Node Name: ");
		inputLine = inputLine(in.nextLine());
		//TODO
	}

	private void initializeNetwork() {
		
		System.out.print("Enter Node Name: ");
		inputLine = inputLine(in.nextLine());
		//TODO
		
		System.out.print("Enter Node Links(seperated with spaces): ");
		inputLine = inputLine(in.nextLine());
		tokenizer = new Scanner(inputLine);
		while(tokenizer.hasNext()){
			//TODO
		}
		tokenizer.close();
		System.out.println("Are you done? (Y/N)");
		for(;;){
			inputLine = inputLine(in.nextLine());
			if(inputLine.equals("Y")){
				initializingDone = true;
				break;
			}else if (inputLine.equals("N")){
				break;
			}
		}
	}
	
	private String inputLine(String line){
		switch(line){
		case "quit": System.out.println("Ending Program...");
						System.exit(0);
		default: return line;
		}
	}

	private void requestHeadNode() {
		System.out.print("Enter the Head Node and End Node (seperate with a space): ");
		inputLine = inputLine(in.nextLine());
		tokenizer = new Scanner(inputLine);
		if(tokenizer.hasNext()){
			//TODO
			if(tokenizer.hasNext()){
				//TODO
				//ignore the rest of the line
			}
		}
		tokenizer.close();
		
		System.out.print("Enter Message to be Sent: ");
		inputLine = inputLine(in.nextLine());
		//TODO
		
		System.out.println("Select the Algorith you would like to use");
		System.out.println("(options: " + printAlgorithms() + ")");
		System.out.print("Enter your choice: ");
		inputLine = inputLine(in.nextLine());
		//TODO
		initializingDone = true;
	}
	
	private void runSimulation() {
		System.out.println("type \"next\" to step through the program");
		while(!destinationReached ){
			inputLine = inputLine(in.nextLine());
			if(inputLine.equals("next")){
				System.out.println("At Node: " + getCurrentNode());
				if(getNextNode() != null){
					System.out.println("Next Node: " + getNextNode() + "\n");
				}else{
					destinationReached = true;
					System.out.println("You have reached the final destination");
				}
			}else{
				System.out.println("type \"next\" to step through the program");
			}
		}
	}

	private String getNextNode() {
		// TODO Auto-generated method stub
		return null;
	}

	private String getCurrentNode() {
		// TODO Auto-generated method stub
		return null;
	}

	private String printAlgorithms() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
