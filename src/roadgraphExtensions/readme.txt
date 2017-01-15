

A description of classes for week 6 (implementing the 1st extension):

The purpose of this extension: This extension finds the fastest path between two intersections with respect to traffic (rush hour, etc) and speed limits.  Both path-finding algorithms, dijkstra and A*, have been modified to search for the  path of minimum duration versus length.

Instruction for running the program:
If you want to find the shortest path, run the “MapGraph” class. If you want the fastest path, run the “TMapGraph” class.
 
Brief description of implementation:
I used the road type information to assign speed limits to edges. I also added a parameter to factor in the traffic. This parameter can have three values to represent the following states: heavy, light, and no traffic. 
I modified the dijkstra and A* implementation to look for the fastest path. The heuristic for A* is the distance between a node and the goal divided by the speed limit of the edge connecting them. 
I also modified the comparator of priority queue to be able to sort the nodes based on time. 

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




—————————————————————————————————————
————————————————————————


A description of classes for week 6 (implementing the 2nd extension):

The purpose of this extension: When many people search in Google Maps, there is a chance that some of these paths have commonalities. This extension stores the paths it finds so it can re-use them later if part of the path it is currently looking for matches any of the stored paths.
Along with each path, the traffic status/level is also stored. This way, if traffic changes, the same path will not be acceptable anymore and the algorithm searches for a new path in the new conditions.

As of now, this extension considers shared paths when two paths with different starting points have the same ending point. Suppose we have a path from A to E that goes through B,C, D and now, we are looking for a path between J and E. When in our search we reach the node C, found in the previous search, we can use the part C-D of the previous path. This is what’s happening here. 

Instruction for running the program:
Run the “TMapGraph” class.
 
Brief description of implementation:
The “FoundPath” class stores the details and conditions of a given fastest path, like its start and destination, the traffic status and duration, as well as the path itself. An instance of this class in the TMapGraph can store and recall the fastest paths.

———
Detailed list of classes and changes made to them:

Class name: PathDetail

Purpose and description of class: Each instance of this class stores the departure, destination, traffic status, and duration of a given path.


Class name: FoundPath

Purpose and description of class: An instance of this class acts as a database; we can insert, search, and pull out a specific path. We can also ask for the details of a specific path (e.g. duration, traffic status) before we make our decision about choosing it. 
Assume we found a path from A to E that goes through B,C, D. Beside adding the A-E path, we also add B-E, C-E, and D-E with their corresponding durations. 



Class name: TMapGraph

Modifications to this class: The class now has a class variable of type “FoundPath”. Whenever we are using either of the path-finding algorithms, the new shortest paths are added to this database/variable. When searching for a path, in each iteration, we check this variable to find out whether a valid path between the current node and the destination has already been found. If yes, we keep that path in a temporary data structure, but keep on searching. 
We continue the search until the duration of the new path we are generating surpasses the duration of the path we found in the class variable (which proves the superiority of the already found path), or the search reaches the destination (meaning the new discovered path is better than the previous ones).                                                                                                    




