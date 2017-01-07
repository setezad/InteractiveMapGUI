package roadgraph;

import java.util.Comparator;
import geography.GeographicPoint;

public class TimeBasedMapNode implements Comparable<TimeBasedMapNode>, Comparator<TimeBasedMapNode>{
	//private double distance;
	private GeographicPoint location;
	// for A*
	private double heuristic;
	// For Extension
	private double duration;
	
	public TimeBasedMapNode(GeographicPoint loc,double t){
		duration = t;
		location = loc;
		heuristic = 0;
	}
	public TimeBasedMapNode(GeographicPoint loc){
		duration = Double.POSITIVE_INFINITY;
		location = loc;
		heuristic = 0;
	}
	public TimeBasedMapNode(GeographicPoint loc,double t,  double h){
		duration = t;
		location = loc;
		heuristic = h;
	}
	
	public void setDuration(double t){
		duration = t;
	}
	public double getDuration(){
		return duration;
	}
	
	public GeographicPoint getLocation(){
		return location;
	}
	
	public String toString(){
		double t = duration+heuristic;
		return location.toString()+" "+duration+" "+heuristic+" sum= "+t;
	}
	public double getHeuristic(){
		return heuristic;
	}
	public void setHeuristic(double h){
		heuristic = h;
	}
	
	@Override
	public int compare(TimeBasedMapNode o1, TimeBasedMapNode o2) {
		return o1.compareTo(o2);
	}

	@Override
	public int compareTo(TimeBasedMapNode o) {
		int comp = Double.compare(this.getDuration()+this.getHeuristic(), o.getDuration()+o.getHeuristic());
		return comp;
	}
	

	
	
}

