package roadgraph;

import geography.GeographicPoint;
import java.util.HashMap;import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;

class AdjListGraph {
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

class RoadProperty{
	String name;
	String type;
	double len;
	GeographicPoint end;
	double speed;
	
	RoadProperty(GeographicPoint loc,String arg1, String arg2, double n){
		name = arg1;
		type = arg2;
		len = n;
		end = loc;
		setSpeed(type);
	}
	RoadProperty(){
		name = "Street name";
		type = "Street type";
		len = 0;
		end = new GeographicPoint(0,0);
		setSpeed(type);
	}
	
	private void setSpeed(String arg){
		switch(arg){
			case "residential": speed = 40;
			break;
			case "city street": speed = 50;
			break;
			case "unclassified": speed = 50;
			break;
			case "living_street": speed = 30;
			break;
			case "secondary": speed = 70;
			break;
			case "tertiary": speed = 60;
			break;
			default: speed = 50;
		}
	}
	
	double getLength(){
		return len;
	}
	GeographicPoint getLocation(){
		return end;
	}
	// For Extension
	double getDuration(){
		return (len/speed);
	}
	double getSpeed(){
		return speed;
	}
	public String toString(){
		return end.toString()+" "+name+" "+type+" "+len+" "+speed+"time= "+ (len/speed)+"\n";
	}
}
