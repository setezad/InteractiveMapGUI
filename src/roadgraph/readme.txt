A description of classes for week 3 (implementing BFS and its required data structures)

Class: MapGraph

Modifications made to MapGraph (what and why):
I added a private field of type “AdjListGraph” which is instantiated in the constructor. This field is used to call the methods from AdjListGraph. These methods include all the data required in setting up and modification of a graph.

I implemented the bfs search in this class.
A private method is used to construct the path.


Class name: AdjListGraph

Purpose and description of class:
The purpose of this class is to handle all the aspects of generating a graph, modifying, and extracting information about it.
The methods of this class are called from the MapGraph class. The functions of these methods include add a vertex, add an edge, get the number of vertices and edges, print vertices, get all the vertices, and so forth.


Class name: RoadProperty

Purpose and description of class:
The purpose of this class was to store the various data related to a neighbor such as street name and type, and etc. 
An instance of this class is a vertex with a geographical point, along with three properties (two strings and one double). 

...

Overall Design Justification (4-6 sentences):
I created another class to handle the process of initializing a graph and store the data related to it. This way, the MapGraph just needs to deal with the search. By separating these functions, the debugging can be easier. Instead of having one class that deals with everything, I have different classes, each processing their part. 

—————————————————————————————————————
————————————————————————

A description of classes for week 4 (implementing Dijkstra and A*):


Class name: WeightedMapNode

Purpose and description of class:
The purpose of this class was to accommodate the two path-finding algorithms (Dijkstra and Astar) by storing the required information for each node which are its location, its distance from the source node, and the value of the heuristic function. In other words, each instance of this class has three fields: location, distance, and heuristicVal. For dijkstra, the heuristicVal was set to 0. 
Several methods have been provided to set and get the value of these fields.
This class implements the Comparable interface. 



Class name: WeightedNodeComparator

Purpose and description of class:
This class implements the Comparator interface and provides a comparator method for a priority queue containing “WeightedMapNode”s. 


Class name: MapGraph

Modifications to this class:
This is not a new class but it was modified. A new data structure was added - an array list of WeightedMapNodes. This array was initialized for each instance of MapGraph. As the new vertices are added to the graph, their locations are also added to this array. Their instances are initialized to infinity. The path-finding algorithms use this structure to update the distances as search through the graph. 




—————————————————————————————————————
————————————————————————


A description of classes for week 6 (implementing an extension):

The purpose of this extension: This extension finds the fastest path between two intersections with respect to traffic (rush hour, etc) and speed limits.  Both path-finding algorithms, dijkstra and A*, have been modified to search for the  path of minimum duration versus length.
 
Brief description of implementation:
I used the road type information to assign speed limits to edges. I also added a parameter to factor in the traffic. This parameter can have three values to represent the following states: heavy, light, and no traffic. 
I modified the dijkstra and A* implementation to look for the fastest path. The heuristic for A* is the distance between a node and goal divided by the speed limit of the edge connecting them. 
I also modified the comparator for the priority queue to be able to sort the nodes based on time. 

———
Detailed list of classes and changes made to them:

Class name: TimeBasedMapNode

Purpose and description of class:
This class is similar to the WeightedMapNode class. Each instance of this class has three private fields: duration, heuristicVal, and location. Duration represents the time it takes to reach that node from the source node which has replaced the distance field due to our change of goal. 
Several methods have been provided to set and get the value of these fields.
This class implements both the Comparable and the Comparator interface.  


Class name: TimeBasedNodeComparator

Purpose and description of class:
This class implements the Comparator interface and provides a comparator method for a priority queue containing “TimeBasedMapNode”s.


Class name: TMapGraph

Purpose and description of class:
This class is a modified version of the MapGraph class. These are the list of modifications: 1-This class has a static field called “traffic level” which determines the impact of traffic on lowering the speed. This field uses the Calendar library to get the time of the day in hours. Some assumptions have been made to define three levels of traffic: rush hours, periods with light traffic, and ones with no traffic. 
2-The methods, dijkstra and Astar, have been modified to look for the path with minimum duration. They now work with a priority queue of “TimeBasedMapNode”s. 

The methods for generating a graph (adding vertices, edges, etc) and bfs method stayed the same. 


Class name: TGraphLoader

Purpose and description of class:
This graph is the same as the GraphLoader class in util package with the only difference that it takes in an instance of “TMapGraph”.


I also modified the RoadProperty class to assign a speed limit to each edge based on its type, eg. speed limit for a residential street is 40kmph, and for a city street is 50kmph. 



