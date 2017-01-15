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
I created another class to handle the process of initializing a graph and store the data related to it. This way, the MapGraph just needs to deal with the search. By separating these functions, the debugging can be easier. Instead of having one class that deals with everything, I have different classes, each taking their part. 

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
This is not a new class but it was modified. A new data structure was added - an array list of WeightedMapNodes. This array was initialized for each instance of MapGraph. As the new vertices are added to the graph, their locations are also added to this array. Their instances are initialized to infinity. The path-finding algorithms use this structure to update the distances as they search through the graph. 



