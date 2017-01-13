/**
 * @author Sahba
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraphExtensions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Collections;
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
import roadgraph.AdjListGraph;
import roadgraph.RoadProperty;
//import util.GraphLoader;
import util.TGraphLoader;

public class TMapGraph {
	private AdjListGraph graph;
	//private ArrayList<TimeBasedMapNode> nodes;
	private LinkedList<TimeBasedMapNode> nodes;
	private static FoundPath data = new FoundPath();
	private static double trafficLevel;
	private Calendar cal = Calendar.getInstance();
	private SimpleDateFormat hformat = new SimpleDateFormat("H");
	
	public TMapGraph(){
		nodes = new LinkedList<TimeBasedMapNode>();
		graph = new AdjListGraph();
		setTrafficLevel();
	}
	public void setTrafficLevel(){
		String str = hformat.format(cal.getTime());
		int hour = Integer.parseInt(str);
		//int hour = 14;
		if((hour>=7 && hour <=9) || (hour>=17 && hour<19))
			trafficLevel = 3;  		// heavy traffic
		else if(hour>11 && hour<13)
			trafficLevel = 1.5; 		// light traffic
		else
			trafficLevel = 1;			// moving traffic
	}  
	
	public int getNumVertices()
	{
		return graph.getNumV();
	}
	
	public int getNumEdges()
	{
		return graph.getNumE();
	}
	
	public Set<GeographicPoint> getVertices()
	{
		return graph.getAllV();
	}
	
	public boolean addVertex(GeographicPoint location)
	{
		nodes.add(new TimeBasedMapNode(location));
		return graph.addGPAsVertex(location);
	}
	
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
	String roadType, double length) throws IllegalArgumentException {
		if(from==null || to==null || !graph.hasV(from) || !graph.hasV(to) || length<0)
			throw new IllegalArgumentException();
		graph.addE(from, to, roadName, roadType, length);
			
	}
	
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
	// Dummy variable for calling the search algorithms
		Consumer<GeographicPoint> temp = (x) -> {};
    	return bfs(start, goal, temp);
	}
	
	public List<GeographicPoint> bfs(GeographicPoint start, 
     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched){
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
		if(start==null || goal==null || map==null)
			throw new IllegalArgumentException();
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		path.add(goal);
		GeographicPoint cur = goal;
		while(!cur.equals(start)){
			cur = map.get(cur);
			path.addFirst(cur);
		}
		return path;
	}

	
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
	    Consumer<GeographicPoint> temp = (x) -> {};
	    return dijkstra(start, goal, temp);
	}
	
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
	  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		int count = 0;
		System.out.println("Dijkstra........");
		//graph.printGraph();
		PriorityQueue<TimeBasedMapNode> pq = new PriorityQueue<TimeBasedMapNode>();
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		HashMap<GeographicPoint,GeographicPoint> traversal = new HashMap<GeographicPoint,GeographicPoint>();
		GeographicPoint cur = new GeographicPoint(0,0);
		boolean found = false;
		boolean alreadyFound = false; //
		HashMap<Double, LinkedList<GeographicPoint>> pathList = new HashMap<Double, LinkedList<GeographicPoint>>();
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		ArrayList<RoadProperty> neighbors;
		//initialize distances to infinity
		for(TimeBasedMapNode entry:nodes){
			entry.setDuration(Double.POSITIVE_INFINITY);
		}
		double curW, newCost;
		double finalcost = 0;
		pq.add(new TimeBasedMapNode(start,0));
		//this.setDistance(start, 0);
		while(!pq.isEmpty()){
			curW = pq.peek().getDuration();
			//search is over if the path we are following now is more expensive than the ones we have found and stored
			if(curW>=getMinCost(pathList) && alreadyFound){
				System.out.println("final cost (using previous paths)= "+getMinCost(pathList));
				break;
			}
			cur = pq.remove().getLocation();
			//It's important to count the nodes as they're removed from the priority queue!
			++count;
			//System.out.print(count+" "+cur.toString()+"\n");   // this line is for debugging
			if(!visited.contains(cur)){
				visited.add(cur);
				// check whether the current node is the goal
				if(cur.equals(goal)){
					found = true;
					//report the final cost
					// this needs to be updated if we are re-using partial paths instead of searching ??
					finalcost = reportFinalCost(goal);
					System.out.println("final cost (using an option better than the previously found paths)="+finalcost);
					break;
				}
				nodeSearched.accept(cur);
				neighbors = graph.getProperty(cur);
				for(RoadProperty neighbor:neighbors){
					if(!visited.contains(neighbor.getLocation()) ){
						double t1= neighbor.getDuration()*trafficLevel+curW;
						double t2= this.getDuration(neighbor.getLocation());
						if(t1<t2){
							//update duration
							this.setDuration(neighbor.getLocation(),neighbor.getDuration(),curW); // multiplied by the trafficLevel in the function
							if(traversal.containsKey(neighbor.getLocation()))
								traversal.replace(neighbor.getLocation(),cur);
							else
								traversal.put(neighbor.getLocation(),cur);
							newCost = neighbor.getDuration()*trafficLevel+curW;
							// check whether a path between this node/neighbor and the goal has been already found 
							if(data.size()>0){
								int num = pathList.size();
								checkPreviouslyFoundPaths(pathList, neighbor.getLocation(), start, goal, newCost, traversal);
								if(pathList.size()>num)
									alreadyFound = true;
								if(!alreadyFound)
									pq.add(new TimeBasedMapNode(neighbor.getLocation(),newCost));
							}
							else{
								pq.add(new TimeBasedMapNode(neighbor.getLocation(),newCost));
							}
						}
					}
				}
			}
		}
		System.out.println(count);
		// construct the path
		if((found & !alreadyFound) || (found && alreadyFound)){
			// this means that the fastest path, with these conditions (start, goal, traffic), had not been found before 
			//so we store this one now.
			path  = constructPath(start,goal,traversal);
			data.addPath(start, goal, trafficLevel, finalcost, nodes, path); //keep this path
		}
		else if (!found && alreadyFound){
			// this means that the fastest path has been found before
			path = getMincostPath(pathList);
		}
		else
			path = null;
		
		return path;
	}
	
	// checkPreviouslyFoundPaths(pathList, neighbor.getLocation(), start, goal, newCost, traversal)
	private void checkPreviouslyFoundPaths(HashMap<Double, LinkedList<GeographicPoint>> list, GeographicPoint loc, GeographicPoint st, 
			 GeographicPoint g, double c, HashMap<GeographicPoint,GeographicPoint> map){
		if(list==null || loc==null || st==null || g==null || c<0 || map==null)
			throw new IllegalArgumentException("invalid input");
		double w = data.getCost(loc, g, trafficLevel);
		if(w>0 ){
			w += c;
			LinkedList<GeographicPoint> temp = constructPath(st,loc,map);
			temp.addAll(data.getPath(loc, g, trafficLevel));
			list.put(w,temp );   
			//return true;
		}
		//return false;
	}
	
	
	//find the minimum cost among the previously found paths that match the conditions of our current search
	private double getMinCost(HashMap<Double, LinkedList<GeographicPoint>> list){
		double min = Double.MAX_VALUE;
		if(list==null)
			throw new IllegalArgumentException();
		for(Double entry:list.keySet()){
			if(entry<min)
				min = entry;
		}
		return min;
	}
	// get the path of minimum cost among those previously found that match the conditions of our current search
	private LinkedList<GeographicPoint> getMincostPath(HashMap<Double, LinkedList<GeographicPoint>> list){
		if(list==null)
			throw new IllegalArgumentException();
		for(Double entry:list.keySet()){
			if(entry==this.getMinCost(list)){
				return list.get(entry);
			}
		}
		return null;
	}

	
	// set duration
	private void setDuration(GeographicPoint loc,double t,double cur){
		if(loc==null || t<0 || cur<0)
			throw new IllegalArgumentException("Invalide input for this method.");
		for(TimeBasedMapNode entry:nodes){
			if(entry.getLocation().equals(loc)){
				entry.setDuration(t*trafficLevel+cur);
				entry.setIndivDur(t*trafficLevel);  // new for extension
				break;
			}
		}
	}
	
	private double getDuration(GeographicPoint loc){
		if(loc==null)
			throw new IllegalArgumentException();
		for(TimeBasedMapNode entry:nodes){
			if(entry.getLocation().equals(loc)){
				return entry.getDuration();
			}
		}
		return Double.POSITIVE_INFINITY;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
	    Consumer<GeographicPoint> temp = (x) -> {};
	    return aStarSearch(start, goal, temp);
	}
	
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
				 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		if(goal==null || start == null)
			throw new NullPointerException();
		// a counter to count the nodes processed here
		int count = 0;
		System.out.println("AStar........");
		//System.out.println(start.distance(goal));
		//graph.printGraph();
		//Set comparator explicitly
		Comparator<TimeBasedMapNode> comparator = new TimeBasedNodeComparator(); 
		PriorityQueue<TimeBasedMapNode> pq = new PriorityQueue<TimeBasedMapNode>(5,comparator);
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		HashMap<GeographicPoint,GeographicPoint> traversal = new HashMap<GeographicPoint,GeographicPoint>();
		GeographicPoint cur;
		boolean flag = false;
		ArrayList<RoadProperty> neighbors;
		//initialize distances to infinity
		for(TimeBasedMapNode entry:nodes){
			entry.setDuration(Double.POSITIVE_INFINITY);
		}
		double curW, cost, heur;
		pq.add(new TimeBasedMapNode(start,0));
		this.setDuration(start, 0,0);
		while(!pq.isEmpty()){
			curW = pq.peek().getDuration();
			cost = pq.peek().getHeuristic();
			cur = pq.remove().getLocation();
			++count;
			//System.out.println(count+" "+cur.toString()+" current cost: "+curW+" heuristic= "+cost+"     sum= "+(cost+curW));
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
						double t1= neighbor.getDuration()*trafficLevel+curW;
						double t2= this.getDuration(neighbor.getLocation());
						if(t1<t2){
							//update distance
							this.setDuration(neighbor.getLocation(),neighbor.getDuration(),curW);
							if(traversal.containsKey(neighbor.getLocation()))
								traversal.replace(neighbor.getLocation(),cur);
							else
								traversal.put(neighbor.getLocation(),cur);
							//double t = neighbor.getDuration()*trafficLevel+curW;
							heur = neighbor.getLocation().distance(goal)/neighbor.getSpeed();
							heur = heur*trafficLevel;
							pq.add(new TimeBasedMapNode(neighbor.getLocation(),t1,heur)); 
						}
					}
				}
			}
		}
		System.out.println("total number of nodes processed= "+count);
		// construct the path
		LinkedList<GeographicPoint> path; 
		if(flag)
			path  = constructPath(start,goal,traversal);
		else
			path = null;
		
		return path;
	
	}
	
	private double reportFinalCost(GeographicPoint loc){
		for(TimeBasedMapNode entry:nodes){
			if(entry.getLocation().equals(loc)){
				return(entry.getDuration());
				
			}
		}
		return 0;				
	}
	
	
	public static void main(String[] args){
		// testing generating a new map
//		System.out.print("Making a new map...");
//		TMapGraph firstMap = new TMapGraph();
//		System.out.print("DONE. \nLoading the map...");
//		TGraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap); 
//		System.out.println("DONE.");

		
		TMapGraph simpleTestMap = new TMapGraph();
		TGraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		//System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		//@SuppressWarnings("unused")
		//List<GeographicPoint> testroute0 = simpleTestMap.bfs(new GeographicPoint(1.0, 1.0), new GeographicPoint(5.0, 1.0));
		@SuppressWarnings("unused")
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		System.out.println("PATH - no previous data: "+testroute.toString()+"\n");
		//
		testroute = simpleTestMap.dijkstra(new GeographicPoint(4,1),new GeographicPoint(8,-1));
		System.out.println("PATH - with data: "+testroute.toString()+"\n");
//		@SuppressWarnings("unused")
//		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
//		System.out.println("PATH: "+testroute2.toString()+"\n");
//		
		TMapGraph testMap = new TMapGraph();
		TGraphLoader.loadRoadMap("data/maps/utc.map", testMap);
//		
//		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		//System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		System.out.println("PATH: "+testroute.toString()+"\n");
//		testroute2 = testMap.aStarSearch(testStart,testEnd);
//		System.out.println("PATH: "+testroute2.toString()+"\n");
//		
//		
//		// A slightly more complex test using real data
//		testStart = new GeographicPoint(32.8674388, -117.2190213);
//		testEnd = new GeographicPoint(32.8697828, -117.2244506);
//		//System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
//		testroute = testMap.dijkstra(testStart,testEnd);
//		System.out.println("PATH: "+testroute.toString()+"\n");
//		testroute2 = testMap.aStarSearch(testStart,testEnd);
//		System.out.println("PATH: "+testroute2.toString()+"\n");
//		
//		
//		/* Use this code in Week 3 End of Week Quiz */
//		TMapGraph theMap = new TMapGraph();
//		System.out.print("DONE. \nLoading the map...");
//		TGraphLoader.loadRoadMap("data/maps/utc.map", theMap);
//		System.out.println("DONE.");
//
//		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
//		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
//		
//		List<GeographicPoint> route = theMap.dijkstra(start,end);
//		System.out.println("PATH: "+route.toString()+"\n");
//		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
//		System.out.println("PATH: "+route2.toString()+"\n");
//		System.out.println("Here .............");
//		//System.out.println("******   ******************   ********************   ********************\n\n\n\n\n");
//		TMapGraph theMapS = new TMapGraph();
//		TGraphLoader.loadRoadMap("data/graders/mod3/map3.txt", theMapS);
//		start = new GeographicPoint(0, 0);
//		end = new GeographicPoint(0, 4);
//		route = theMapS.dijkstra( start,end);
//		System.out.println("PATH: "+route.toString()+"\n");
		
	}
	
	
	
}

		





