package roadgraph;

import java.util.Comparator;

public class WeightedNodeComparator implements Comparator<WeightedMapNode>{

	@Override
	public int compare(WeightedMapNode o1, WeightedMapNode o2) {
		// TODO Auto-generated method stub
		if(o1.getDistance()+o1.getHeuristic() < o2.getDistance()+o2.getHeuristic())
			return -1;
		else if (o1.getDistance()+o1.getHeuristic() > o2.getDistance()+o2.getHeuristic())
			return 1;
		else
			return 0;
		
//		if(o1.getHeuristic() < o2.getHeuristic())
//			return -1;
//		else if (o1.getHeuristic() > o2.getHeuristic())
//			return 1;
//		else
//			return 0;
	}
	
}
