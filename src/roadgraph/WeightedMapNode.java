package roadgraph;

import java.util.Comparator;

import geography.GeographicPoint;

public class WeightedMapNode implements Comparable<WeightedMapNode>, Comparator<WeightedMapNode>{
	private double distance;
	private GeographicPoint location;
	// for A*
	private double heuristicVal;
	
	public WeightedMapNode(GeographicPoint loc, double dist){
		distance = dist;
		location = loc;
		heuristicVal = 0;
		
	}
	
	public WeightedMapNode(GeographicPoint loc, double dist, double h){
		distance = dist;
		location = loc;
		heuristicVal = h;
	}
	
	public WeightedMapNode(GeographicPoint loc){
		distance = Double.POSITIVE_INFINITY;
		location = loc;
		heuristicVal = 0;
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
		return heuristicVal;
	}
	public void setHeuristic(double n){
		heuristicVal = n;
	}
	public String toString(){
		double t = distance+heuristicVal;
		return location.toString()+" "+distance+" "+heuristicVal+" sum= "+t;
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
