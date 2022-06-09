import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * Node represents an intersection in the road graph. It stores its ID and its
 * location, as well as all the segments that it connects to. It knows how to
 * draw itself, and has an informative toString method.
 * 
 * @author tony
 */
public class Node implements Comparable{

	public final int nodeID;
	public final Location location;
	public final Collection<Segment> segments;

	public double heuristic = 0;
	public double gCost = 0;
	public Node parent;

	public int count = 0;//(int)Double.POSITIVE_INFINITY;
	public int reachBack;
	public Queue<Node> children;

	public Node(int nodeID, double lat, double lon) {
		this.nodeID = nodeID;
		this.location = Location.newFromLatLon(lat, lon);
		this.segments = new HashSet<>();
	}

	public void addSegment(Segment seg) {
		segments.add(seg);
	}

	public void draw(Graphics g, Dimension area, Location origin, double scale) {
		Point p = location.asPoint(origin, scale);

		// for efficiency, don't render nodes that are off-screen.
		if (p.x < 0 || p.x > area.width || p.y < 0 || p.y > area.height)
			return;

		int size = (int) (Mapper.NODE_GRADIENT * Math.log(scale) + Mapper.NODE_INTERCEPT);
		g.fillRect(p.x - size / 2, p.y - size / 2, size, size);
	}

	public String toString() {
		Set<String> edges = new HashSet<String>();
		for (Segment s : segments) {
			if (!edges.contains(s.road.name))
				edges.add(s.road.name);
		}

		String str = "ID: " + nodeID + "  loc: " + location + "\nroads: ";
		for (String e : edges) {
			str += e + ", ";
		}
		return str.substring(0, str.length() - 2);
	}

	public double getCost(){
		return heuristic + gCost;
	}

	@Override
	public int compareTo(Object o) {
		if(o==null){
			throw new NullPointerException();
		}else if(!(o instanceof Node)){
			throw new ClassCastException();
		}

		Node n = (Node)o;
		if(this.getCost()>n.getCost()){
			return 1;
		}else if(this.getCost()==n.getCost()){
			return 0;
		}else if(this.getCost()<n.getCost()){
			return -1;
		}
		return -1;
	}

	public boolean equals(Object o){
		if(o==null){
			throw new NullPointerException();
		}else if(!(o instanceof Node)){
			throw new ClassCastException();
		}

		Node n = (Node)o;
		if(this.nodeID==n.nodeID){
			return true;
		}
		return false;
	}
}

// code for COMP261 assignments