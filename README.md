##SYSC 3110 Group Project: Network Routing Simulator
###Group Members: Alex Hoecht, Andrew Ward, Mohamed Dahrouj, Shasthra Ranasinghe

###Summary:
----------------------------------------------
The goal of this team project is to build a simulator to evaluate the performance of various routing
algorithms. This project will implement the following routing algorithms:
* Random
* Fllooding
* Shortest path
* Custom method

A user interface will be impleneted for the simulator to display the network topology and let the user edit it. The interface will always provide the metrics of the network and the ability to view and step through a simulation.

###Milestones:
----------------------------------------------
The project is divided into 4 iterations, each ending with corresponding to a milestone with deliverables.

| Milestone | Description| Due Date      | Weight|
| --------- | ---------- |-------------- |-------|
| 1         | A simple console-based version. Only the random algorithm will be implemented. | Oct 21 | 15%|
| 2         | GUI and unit tests.  |Nov 7| 20% |
| 3         | All routing algorithms are to be implemented and selectable through the GUI. |Nov 21 | 30%|
| 4         | Add the ability to step back in a network (i.e. undo function) and the ability to save/restore a simulation of the netwrork topology.  | Dec 5 | 35%|

###Design Choices
----------------------------------------------
For implementing our four routing algorithms, we decided to use the strategy pattern by creating a **RoutingAlgorithm** interface. 
A **Message** class was created to store the source and destination of a message being sent through the network.
A class was also created for **Graph** which includes a set of vertices and list of edges also modeled as classes (**Vertex** and **Edge**)
A **Simulation** class was created to simulate the project generating a network and displaying progress and metrics to the user using a **Controller UI** class
  
###UML Diagram
----------------------------------------------
///////////////////////////INSERT UML HERE///////////////////////////

###Developer Contributions
----------------------------------------------
| Developer | Contributions to Milestone 1|
| --------- | ----------------------- |
| Alex Hoecht |Implemented the random routing algorithm and layed grounwork for other algorithms |
| Andrew Ward | Implemented the Graph class consisting of Vertex and Edge objects |
| Mohamed Dahrouj | Implemented a Simulation class that creates a simulation and stores metrics |
| Shashtra Ranasinghe | Implemented a console UI to interact with users and Simulation	|
| Everyone    | Created block comments for JavaDoc, Created UML Diagram |

###How to Run
----------------------------------------------
///////////////////////////INSERT INSTRUCTIONS HERE///////////////////////////
