package roadgraphExtensions;

//import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
//import java.util.List;
import geography.GeographicPoint;

public class FoundPath {
	//private LinkedList<GeographicPoint> nodes;
	private HashMap<PathDetail,LinkedList<GeographicPoint>> foundPaths;
	
	public FoundPath(){
		foundPaths = new HashMap<PathDetail,LinkedList<GeographicPoint>>();
	}
	public FoundPath(GeographicPoint s, GeographicPoint g, double tr, double c, LinkedList<TimeBasedMapNode> nodes, 
			LinkedList<GeographicPoint> p){
		if(s==null || g==null || tr<0 || c<0 || nodes==null)
			throw new IllegalArgumentException();
		foundPaths = new HashMap<PathDetail,LinkedList<GeographicPoint>>();
		PathDetail key = new PathDetail(s,g,tr,c);
		LinkedList<GeographicPoint> value = new LinkedList<GeographicPoint>();
		value = p;
		foundPaths.put(key, value);
		//add smaller subsections of this path
		for(int i=0;i<p.size()-2;i++){
			//update cost
			c = c - findCost(nodes,value.peek());
			value.remove();
			key = new PathDetail(value.peek(),g,tr,c);
			foundPaths.put(key, value);
		}
	}
	
	public void addPath(GeographicPoint s, GeographicPoint g, double tr, double c, LinkedList<TimeBasedMapNode> nodes, 
			LinkedList<GeographicPoint> p){
		if(s==null || g==null || tr<0 || c<0 || nodes==null || p==null)
			throw new IllegalArgumentException();
		PathDetail key = new PathDetail(s,g,tr,c);
		LinkedList<GeographicPoint> value = new LinkedList<GeographicPoint>();
		// value = p;  WRONG!! this way, value is pointing to "p" so all the changes actually affect "p"!!
		value.addAll(p);
		foundPaths.put(key, value);
		//add smaller subsections of this path
		for(int i=0;i<(p.size()-2);i++){
			//update cost = the cost of getting to vertex v is stored in the vertex following it
			value.remove();
			c = c - findCost(nodes,value.peek());
			
			key = new PathDetail(value.peek(),g,tr,c);
			foundPaths.put(key, value);
		}
	}
	//get the path between two vertices
	public LinkedList<GeographicPoint> getPath(GeographicPoint s, GeographicPoint g, double tr){
		if(s==null || g==null || tr<0)
			throw new IllegalArgumentException();
		for(PathDetail entry:foundPaths.keySet()){
			if(entry.getStart().equals(s) && entry.getDest().equals(g) && entry.getTraffic() == tr){ 
				return foundPaths.get(entry);
			}
		}
		return null;
	}
	
	
	public boolean containsPathBetween(GeographicPoint s, GeographicPoint g, double tr){
		if(s==null || g==null || tr<0)
			throw new IllegalArgumentException();
		for(PathDetail entry:foundPaths.keySet()){
			if(entry.getStart().equals(s) && entry.getDest().equals(g) && entry.getTraffic() == tr){
				return true;
			}
		}
		return false;
	}
	public double getCost(GeographicPoint s, GeographicPoint g, double tr){
		if(s==null || g==null || tr<0)
			throw new IllegalArgumentException();
		for(PathDetail entry:foundPaths.keySet()){
			if(entry.getStart().equals(s) && entry.getDest().equals(g) && entry.getTraffic() == tr){
				return entry.getCost();
			}
		}
		return -1;
	}
	
	public int size(){
		return foundPaths.size();
	}
	
	
	private double findCost(LinkedList<TimeBasedMapNode> nodes, GeographicPoint loc){
		if(nodes==null || loc==null)
			throw new IllegalArgumentException();
		for(TimeBasedMapNode entry:nodes){
			if(entry.getLocation().equals(loc)){
				return entry.getIndivDur();
			}
		}
		return 0;
	}
	
	
}



class PathDetail {
	private GeographicPoint start;
	private GeographicPoint dest;
	private double cost;
	private double trafficStat;
	
	PathDetail(){
		start = new GeographicPoint(0,0);
		dest = new GeographicPoint(0,0);
	}
	public PathDetail(GeographicPoint s, GeographicPoint g, double t,double c){
		if(s==null || g==null || t<0 || c<0)
			throw new IllegalArgumentException();
		start = s;
		dest = g;
		trafficStat = t;
		cost = c;
	}
	public PathDetail(GeographicPoint s, GeographicPoint g){
		if(s==null || g==null)
			throw new IllegalArgumentException();
		start = s;
		dest = g;
		trafficStat = 0;
		cost = 0;
	}
	public GeographicPoint getStart(){
		return start;
	}
	public GeographicPoint getDest(){
		return dest;	
	}
	public double getTraffic(){
		return trafficStat;
	}
	public double getCost(){
		return cost;
	}
	
	public String toString(){
		return this.getStart()+" "+this.getDest()+" cost=  "+this.getCost()+" tr= "+this.getTraffic();
	}
	
}
