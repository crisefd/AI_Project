# AI_Project
Search algorithms for artificial intelligence

# Contents:
- Breadth-first search
- Depth-first search
- Greedy search
- Cost uniform search
- A* search


# Problem Overview:

SORTING ROBOT: The Sorting Robot is an agent created to order picking and resettling different types of objects based on the location they should correspond. The  assumed environment is modeled as an NxN grid. Two objects of any weight is considered; the Robot aim is to bring the two objects to the places it deserves within of the grid. For each scan of the environment, the agent can perform simple movements like moving up, down, left or right. The robot could carry both objects at the same time. Although this environment does not considers walls or boundaries beyond that of the delimits of the grid, there are some cells  in which it is more difficult to move, these cells have an associated value greater than zero, the remaining cells have a default value of zero.  

The cost of robot movement depends on the weight load: If does not carry weight, cost is one plus the value of the grid which it moved, otherwise the cost corresponds to one plus the weight of objects carrying value plus the value of the grid to which it moved.     

Note that when you get a box that has an object, the agent must necessarily take it, that is, you can not decide wether taking it the object, or not taking it the object. Similarly, when the box has reached the point where it should be located, the object left there is necessarily the corresponding object. The problem ends when the robot locate objects in positions that They correspond.

Initial environment information is represented by a matrix of N X N in the each cell has the following values:
- 0 if it is not difficult empty cell 
- 1 If the robot is located in the cell 
- 2 If the object 1 is located in the cell
- 3 If the object 2 is located in the cell
- 4 If the cell is the place of the object 1
- 5 If the cell is the place of object 2
- M: where M is a number greater than zero indicates the degree of difficulty respective cell.

# Application Use

When runnig the app, an input dialog will be displayed. The required input is: size of grid, weigth of objec 1 and object 2 and the penalty value for the diffcult cells. Once the grid is display you have to choose between one of the above search algorithms to resolve the problem.

Note: the main class is Ventana.java

