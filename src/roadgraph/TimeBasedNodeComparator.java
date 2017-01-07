package roadgraph;

import java.util.Comparator;

public class TimeBasedNodeComparator implements Comparator<TimeBasedMapNode>{

	@Override
	public int compare(TimeBasedMapNode o1, TimeBasedMapNode o2) {
		if(o1.getDuration()+o1.getHeuristic() < o2.getDuration()+o2.getHeuristic())
			return -1;
		else if(o1.getDuration()+o1.getHeuristic() > o2.getDuration()+o2.getHeuristic())
			return 1;
		else
			return 0;
	}

	

}
