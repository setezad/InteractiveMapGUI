/**
 * @author UCSD MOOC development team and Sahba
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.Queue;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and Sahba
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 3
	private ArrayList<WeightedMapNode> nodes;
	private AdjListGraph graph;
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3
		nodes = new ArrayList<WeightedMapNode>();
		graph = new AdjListGraph();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return graph.getNumV();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		return graph.getAllV();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		return graph.getNumE();
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		nodes.add(new WeightedMapNode(location));
		return graph.addGPAsVertex(location);
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 3
		if(from!=null && to!=null && graph.hasV(from) && graph.hasV(to) && length>=0){
			graph.addE(from, to, roadName, roadType, length);
		}
		else
			throw new IllegalArgumentException();
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		Queue<GeographicPoint> queue = new LinkedList<GeographicPoint>();
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		HashMap<GeographicPoint,GeographicPoint> traversal = new HashMap<GeographicPoint,GeographicPoint>();
		GeographicPoint cur;
		boolean flag = false;
		queue.add(start);
		while(!queue.isEmpty()){
			cur = queue.remove();
			if(!cur.equals(goal)){
				nodeSearched.accept(cur);
				for(GeographicPoint loc:graph.getNeighbors(cur)){
					if(!visited.contains(loc)){
						queue.add(loc);
						visited.add(loc);
						traversal.put(loc,cur);
					}
				}
			}
			else {
				flag = true;
			}
			
		}
		// construct the path
		LinkedList<GeographicPoint> path; 
		if(flag)
			path  = constructPath(start,goal,traversal);
		else
			path = null;
		
		return path;
	}
	
	private LinkedList<GeographicPoint> constructPath(GeographicPoint start, GeographicPoint goal,
			HashMap<GeographicPoint,GeographicPoint> map){
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		path.add(goal);
		GeographicPoint cur = goal;
		while(!cur.equals(start)){
			cur = map.get(cur);
			path.addFirst(cur);
		}
		return path;
	}

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		int count = 0;
		System.out.println("Dijkstra........");
		//graph.printGraph();
		PriorityQueue<WeightedMapNode> pq = new PriorityQueue<WeightedMapNode>();
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		HashMap<GeographicPoint,GeographicPoint> traversal = new HashMap<GeographicPoint,GeographicPoint>();
		GeographicPoint cur;
		boolean flag = false;
		ArrayList<RoadProperty> neighbors;
		//initialize distances to infinity
		for(WeightedMapNode entry:nodes){
			entry.setDistance(Double.POSITIVE_INFINITY);
		}
		double curW;
		pq.add(new WeightedMapNode(start,0));
		this.setDistance(start, 0);
		while(!pq.isEmpty()){
			curW = pq.peek().getDistance();
			cur = pq.remove().getLocation();
			//It's important to count the nodes as they're removed from the priority queue!
			++count;
//			System.out.print(count+" "+cur.toString()+"\n");
			if(!visited.contains(cur)){
				visited.add(cur);
				if(cur.equals(goal)){
					flag = true;
					//report the final cost
					int index = nodes.indexOf(goal);
					System.out.println(nodes.get(index).getDistance());
					break;
				}
				nodeSearched.accept(cur);
				neighbors = graph.getProperty(cur);
				for(RoadProperty neighbor:neighbors){
					if(!visited.contains(neighbor.getLocation()) ){
						double len1= neighbor.getLength()+curW;
						double len2= this.getDistance(neighbor.getLocation());
						if(len1<len2){
							//update distance
							this.setDistance(neighbor.getLocation(),neighbor.getLength()+curW);
							if(traversal.containsKey(neighbor.getLocation()))
								traversal.replace(neighbor.getLocation(),cur);
							else
								traversal.put(neighbor.getLocation(),cur);
							pq.add(new WeightedMapNode(neighbor.getLocation(),neighbor.getLength()+curW));
						}
					}
				}
			}
		}
		System.out.println(count);
		// construct the path
		LinkedList<GeographicPoint> path; 
		if(flag)
			path  = constructPath(start,goal,traversal);
		else
			path = null;
		
		return path;
	}
	
	// set distance 
	private void setDistance(GeographicPoint loc,double dist){
		for(WeightedMapNode entry:nodes){
			if(entry.getLocation().equals(loc)){
				entry.setDistance(dist);
				break;
			}
		}
	}
	
	private double getDistance(GeographicPoint loc){
		for(WeightedMapNode entry:nodes){
			if(entry.getLocation().equals(loc)){
				return entry.getDistance();
			}
		}
		return Double.POSITIVE_INFINITY;
	}
	
	
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		// Hook for visualization.  See writeup.
		
		if(goal==null || start == null)
			throw new NullPointerException();
		// a counter to count the nodes processed here
		int count = 0;
		System.out.println("AStar........");
		//System.out.println(start.distance(goal));
		graph.printGraph();
		//Set comparator explicitly
		Comparator<WeightedMapNode> comparator = new WeightedNodeComparator();
		PriorityQueue<WeightedMapNode> pq = new PriorityQueue<WeightedMapNode>(5,comparator);
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		HashMap<GeographicPoint,GeographicPoint> traversal = new HashMap<GeographicPoint,GeographicPoint>();
		GeographicPoint cur;
		boolean flag = false;
		ArrayList<RoadProperty> neighbors;
		//initialize distances to infinity
		for(WeightedMapNode entry:nodes){
			entry.setDistance(Double.POSITIVE_INFINITY);
		}
		double curW, cost;
		pq.add(new WeightedMapNode(start,0));
		//Object aaa = pq.comparator();
		this.setDistance(start, 0);
		while(!pq.isEmpty()){
			curW = pq.peek().getDistance();
			cur = pq.remove().getLocation();
			++count;
			cost = curW+cur.distance(goal);
			//System.out.println(count+" "+cur.toString()+" current cost: "+curW+" distance to goal= "+cur.distance(goal)+" sum= "+cost);
			if(!visited.contains(cur)){
				visited.add(cur);
				if(cur.equals(goal)){
					flag = true;
					//report the final cost
					System.out.println("final cost= "+reportFinalCost(goal));
					break;
				}
				nodeSearched.accept(cur);
				neighbors = graph.getProperty(cur);
				for(RoadProperty neighbor:neighbors){
					if(!visited.contains(neighbor.getLocation()) ){
						double len1= neighbor.getLength()+curW;
						double len2= this.getDistance(neighbor.getLocation());
						if(len1<len2){
							//update distance
							this.setDistance(neighbor.getLocation(),len1);
							if(traversal.containsKey(neighbor.getLocation()))
								traversal.replace(neighbor.getLocation(),cur);
							else
								traversal.put(neighbor.getLocation(),cur);
							pq.add(new WeightedMapNode(neighbor.getLocation(),len1,neighbor.getLocation().distance(goal))); 
						}
					}
				}
			}
		}
		//System.out.println("total number of nodes processed "+count);
		// construct the path
		LinkedList<GeographicPoint> path; 
		if(flag)
			path  = constructPath(start,goal,traversal);
		else
			path = null;
		
		return path;
		
	}
	
	private double reportFinalCost(GeographicPoint loc){
		for(WeightedMapNode entry:nodes){
			if(entry.getLocation().equals(loc)){
				return(entry.getDistance());
				
			}
		}
		return 0;
	}

	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		@SuppressWarnings("unused")
		List<GeographicPoint> testroute0 = simpleTestMap.bfs(new GeographicPoint(1.0, 1.0), new GeographicPoint(5.0, 1.0));
		@SuppressWarnings("unused")
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		@SuppressWarnings("unused")
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		
		/* Use this code in Week 3 End of Week Quiz */
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		System.out.println("Here .............");
		//System.out.println("******   ******************   ********************   ********************\n\n\n\n\n");
		MapGraph theMapS = new MapGraph();
		GraphLoader.loadRoadMap("data/graders/mod3/map3.txt", theMapS);
		start = new GeographicPoint(0, 0);
		end = new GeographicPoint(0, 4);
		route = theMapS.dijkstra( start,end);
		
		
		//quiz
		MapGraph theMapq = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMapq);
		System.out.println("DONE.");

		start = new GeographicPoint(32.8648772, -117.2254046);
		end = new GeographicPoint(32.8660691, -117.217393);
		route = theMapq.dijkstra(start,end);
		route2 = theMapq.aStarSearch(start,end);
		
	}
	
}



