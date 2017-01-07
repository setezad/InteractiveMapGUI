package roadgraph;

import java.util.Comparator;

import geography.GeographicPoint;

public class WeightedMapNode implements Comparable<WeightedMapNode>, Comparator<WeightedMapNode>{
	private double distance;
	private GeographicPoint location;
	// for A*
	private double heuristic;
	
	public WeightedMapNode(GeographicPoint loc, double dist){
		distance = dist;
		location = loc;
		heuristic = 0;
		
	}
	
	public WeightedMapNode(GeographicPoint loc, double dist, double h){
		distance = dist;
		location = loc;
		heuristic = h;
	}
	
	public WeightedMapNode(GeographicPoint loc){
		distance = Double.POSITIVE_INFINITY;
		location = loc;
		heuristic = 0;
	}
	
	public GeographicPoint getLocation(){
		return location;
	}
	
	public double getDistance(){
		return distance;
	}
	
	public void setDistance(double dist){
		distance = dist;
	}
	public double getHeuristic(){
		return heuristic;
	}
	public void setHeuristic(double n){
		heuristic = n;
	}
	public String toString(){
		double t = distance+heuristic;
		return location.toString()+" "+distance+" "+heuristic+" sum= "+t;
	}
	
	@Override
	public int compareTo(WeightedMapNode other){
		int comp = Double.compare((this.getDistance()+this.getHeuristic()), (other.getDistance()+other.getHeuristic()));
		return comp;
	}

	@Override
	public int compare(WeightedMapNode o1, WeightedMapNode o2) {
		return o1.compareTo(o2);
	}

}
