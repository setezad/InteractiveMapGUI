package roadgraphExtensions;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import util.TGraphLoader;
import geography.GeographicPoint;

public class testExtension {
	private TMapGraph simpleTestMap;
	private TMapGraph testMap;
	private TMapGraph theMapS;
	
	
	@Before
	public void setup(){
		simpleTestMap = new TMapGraph();
		TGraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		testMap = new TMapGraph();
		TGraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		theMapS = new TMapGraph();
		TGraphLoader.loadRoadMap("data/graders/mod3/map3.txt", theMapS);
	}
	
	@Test
	public void testDijkstra(){
		// find a path when there are no paths stored
		GeographicPoint stStart1 = new GeographicPoint(1.0, 1.0);
		GeographicPoint stEnd1 = new GeographicPoint(8.0, -1.0);
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(stStart1,stEnd1);
		String expected = "[Lat: 1.0, Lon: 1.0, Lat: 4.0, Lon: 1.0, Lat: 5.0, Lon: 1.0, Lat: 6.5, "
				+ "Lon: 0.0, Lat: 8.0, Lon: -1.0]";
		assertTrue(testroute.toString().equalsIgnoreCase(expected));
		
		// find a path which is a subsection of a path previously found 
		GeographicPoint stStart2 = new GeographicPoint(4.0, 1.0);
		List<GeographicPoint> testroute2 = simpleTestMap.dijkstra(stStart2,stEnd1); 
		expected = "[Lat: 4.0, Lon: 1.0, Lat: 5.0, Lon: 1.0, Lat: 6.5, "
				+ "Lon: 0.0, Lat: 8.0, Lon: -1.0]";
		assertTrue(testroute2.toString().equalsIgnoreCase(expected));
		
		// find a path (in the opposite direction) for which the stored paths are not helpful
		stStart1 = new GeographicPoint(6.5, 0.0);
		stEnd1 = new GeographicPoint(4.0, 2.0);
		testroute = simpleTestMap.dijkstra(stStart1,stEnd1);
		expected = "[Lat: 6.5, Lon: 0.0, Lat: 5.0, Lon: 1.0, Lat: 4.0, Lon: 1.0, Lat: 4.0, Lon: 2.0]";
		assertTrue(testroute.toString().equalsIgnoreCase(expected));
		
		// find a path with real data - check to confirm the program still works properly and extension did not cause malfunction
		GeographicPoint tStart = new GeographicPoint(32.869423, -117.220917);
		GeographicPoint tEnd = new GeographicPoint(32.869255, -117.216927);
		testroute = testMap.dijkstra(tStart,tEnd);
		expected = "[Lat: 32.869423, Lon: -117.220917, Lat: 32.869488, Lon: -117.220108, Lat: 32.869606, "+
				"Lon: -117.21839, Lat: 32.8696045, Lon: -117.2182699, Lat: 32.869255, Lon: -117.216927]";
		assertTrue(testroute.toString().equalsIgnoreCase(expected));
		
		tStart = new GeographicPoint(32.8674388, -117.2190213);
		tEnd = new GeographicPoint(32.8697828, -117.2244506);
		testroute = testMap.dijkstra(tStart,tEnd);
		
		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		List<GeographicPoint> route = testMap.dijkstra(start,end);
		
		
		start = new GeographicPoint(0, 0);
		end = new GeographicPoint(0, 4);
		route = theMapS.dijkstra( start,end);
		
	}
	
	public void testAstar(){
		
	}
	
	
}
