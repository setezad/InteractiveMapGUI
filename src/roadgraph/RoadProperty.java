package roadgraph;

import geography.GeographicPoint;

public class RoadProperty{
	String name;
	String type;
	double len;
	GeographicPoint end;
	double speed;
	
	public RoadProperty(GeographicPoint loc,String arg1, String arg2, double n){
		name = arg1;
		type = arg2;
		len = n;
		end = loc;
		setSpeed(type);
	}
	public RoadProperty(){
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
	
	public double getLength(){
		return len;
	}
	public GeographicPoint getLocation(){
		return end;
	}
	// For Extension
	public double getDuration(){
		return (len/speed);
	}
	public double getSpeed(){
		return speed;
	}
	public String toString(){
		return end.toString()+" "+name+" "+type+" "+len+" "+speed+"time= "+ (len/speed)+"\n";
	}
}
