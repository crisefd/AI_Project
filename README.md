# Sorting Robot
Search algorithms for artificial intelligence

Requirements.
- Netbeans IDE
- Java 7

# Contents:
- Breadth-first search
- Depth-first search
- Greedy search
- Cost uniform search
- A* search


# Problem Overview:

SORTING ROBOT: The Sorting Robot is an agent created to order picking and resettling different types of objects based on the location they should correspond to . The  assumed environment is modeled as an NxN grid. Two objects of any weight are considered; the robot's aim is to put the two objects to the corresponding cells within the grid. For each scan of the environment, the agent can perform the simple movements: go up, down, left and right. The robot can carry both objects at the same time. Although this environment does not considers walls or boundaries beyond that of the boundaries of the grid, there are some cells where it is more difficult to move on, these cells have an associated value greater than zero, all other cells have a default value of zero.  

The cost of robot's movement depends on the load weight: If it's not carrying any objects, cost is one plus the cost of the cell where it moved on, otherwise the cost corresponds to one plus the weight of the objects that it's carrying plus the cost of the cell where it moved on.     

Note that when the robot moves on a cell where an object is on, the agent must take it, that is, the agent can not decide wether taking it the object, or not taking it. Similarly, when the an object has reached the point where it should be located, the object must be left there. The problem ends when the robot locates the two objects in the corresponding places.

Initial environment information is represented by a matrix of N X N where each cell has the following costs:
- 0 if it is not difficult empty cell 
- -1 If the robot is located in the cell 
- -2 If the object 1 is located in the cell
- -3 If the object 2 is located in the cell
- -4 If the cell is the place of the object 1
- -5 If the cell is the place of object 2
- M: where M is a number greater than zero indicates the degree of difficulty of the respective cell.

# Application Use

When runnig the app, an input dialog will be displayed. The required input is: size of grid, weigth of object one and object two and the penalty value for the difficult cells. Once the grid is display you have to choose among the above search algorithms to solve the problem.

Note: the main class is Ventana.java

