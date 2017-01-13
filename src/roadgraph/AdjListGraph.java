package roadgraph;

import geography.GeographicPoint;
import java.util.HashMap;import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;

public class AdjListGraph {
	private int numVertices;
	private int numEdges;
	private HashMap<GeographicPoint,ArrayList<RoadProperty>> adjList;
	
	public AdjListGraph(){
		adjList = new HashMap<GeographicPoint,ArrayList<RoadProperty>>();
	}
	
	public int getNumV(){
		return numVertices;
	}
	public int getNumE(){
		return numEdges;
	}
	// add a vertex only if it's not already added
	public boolean addGPAsVertex(GeographicPoint key){
		boolean added = false;
		if(key!=null && !adjList.containsKey(key)){
			adjList.put(key, new ArrayList<RoadProperty>());
			added = true;
			numVertices++;
		}
		return added;
	}
	
	public void addE(GeographicPoint loc1, GeographicPoint loc2, String nameArg, String typeArg, double length){
		adjList.get(loc1).add(new RoadProperty(loc2,nameArg,typeArg,length));
		numEdges++;
	}
	
	public List<GeographicPoint> getNeighbors(GeographicPoint loc){
		List<GeographicPoint> neighbors = new ArrayList<GeographicPoint>();
		for(RoadProperty entry:adjList.get(loc)){
			neighbors.add(entry.getLocation());
		}
		return neighbors;
	}
	
	public ArrayList<RoadProperty> getProperty(GeographicPoint loc){
		return adjList.get(loc);
	}
	
	public boolean hasV(GeographicPoint loc){
		return adjList.containsKey(loc);
	}
	public Set<GeographicPoint> getAllV(){
		return adjList.keySet();
	}
	
	public void printGraph(){
		for(Map.Entry<GeographicPoint, ArrayList<RoadProperty>> entry: adjList.entrySet()){
			String location = entry.getKey().toString();
			ArrayList<RoadProperty> value = entry.getValue();
			System.out.println(location+" edges: "+value.toString());
			System.out.println("----");
			
		}
		
	}
}


