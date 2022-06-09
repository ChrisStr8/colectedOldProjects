import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * This is the main class for the mapping program. It extends the GUI abstract
 * class and implements all the methods necessary, as well as having a main
 * function.
 * 
 * @author tony
 */
public class Mapper extends GUI {
	public static final Color NODE_COLOUR = new Color(77, 113, 255);
	public static final Color SEGMENT_COLOUR = new Color(130, 130, 130);
	public static final Color HIGHLIGHT_COLOUR = new Color(255, 219, 77);

	// these two constants define the size of the node squares at different zoom
	// levels; the equation used is node size = NODE_INTERCEPT + NODE_GRADIENT *
	// log(scale)
	public static final int NODE_INTERCEPT = 1;
	public static final double NODE_GRADIENT = 0.8;

	// defines how much you move per button press, and is dependent on scale.
	public static final double MOVE_AMOUNT = 100;
	// defines how much you zoom in/out per button press, and the maximum and
	// minimum zoom levels.
	public static final double ZOOM_FACTOR = 1.3;
	public static final double MIN_ZOOM = 1, MAX_ZOOM = 200;

	// how far away from a node you can click before it isn't counted.
	public static final double MAX_CLICKED_DISTANCE = 0.15;

	// these two define the 'view' of the program, ie. where you're looking and
	// how zoomed in you are.
	private Location origin;
	private double scale;

	// our data structures.
	private Graph graph;
	private Trie trie;

	private List<Node> clickedNodes = new ArrayList<>();
	private boolean isStartClick = true;

	private boolean forDistance = true;
	private boolean driving = true;
	private boolean walking = false;
	private boolean biking = false;

	@Override
	protected void redraw(Graphics g) {
		if (graph != null)
			graph.draw(g, getDrawingAreaDimension(), origin, scale);
	}

	@Override
	protected void onClick(MouseEvent e) {
		Location clicked = Location.newFromPoint(e.getPoint(), origin, scale);
		// find the closest node.
		double bestDist = Double.MAX_VALUE;
		Node closest = null;

		for (Node node : graph.nodes.values()) {
			double distance = clicked.distance(node.location);
			if (distance < bestDist) {
				bestDist = distance;
				closest = node;
			}
		}

		// if it's close enough, highlight it and show some information.
		if (clicked.distance(closest.location) < MAX_CLICKED_DISTANCE) {
			if(isStartClick){
				clickedNodes = new ArrayList<>();
				clickedNodes.add(closest);
				isStartClick = false;
				graph.setHighlightedSegments(new ArrayList<>());
				getTextOutputArea().setText(closest.toString());
			}else {
				clickedNodes.add(closest);
				isStartClick = true;
				Path p = new Path(clickedNodes.get(0), clickedNodes.get(1), graph);
				getTextOutputArea().setText("Searching for path");
				if(p.findPathAStar(forDistance, driving, walking ,biking)) {
					p.drawPath(graph);
					getTextOutputArea().setText(p.toString());
				}else{
					getTextOutputArea().setText("There is no path between the nodes\n\n"+clickedNodes.get(0).toString()+"\n\nand\n\n"+clickedNodes.get(1).toString());
				}
			}
			graph.setHighlightedNodes(clickedNodes);
			redraw();
		}
	}

	@Override
	protected void onSearch() {
		if (trie == null)
			return;

		graph.setHighlight(new HashSet<>());
		getTextOutputArea().setText("");

		// get the search query and run it through the trie.
		String query = getSearchBox().getText();
		Collection<Road> selected = trie.get(query);

		if(!query.equals("")) {
			// figure out if any of our selected roads exactly matches the search
			// query. if so, as per the specification, we should only highlight
			// exact matches. there may be (and are) many exact matches, however, so
			// we have to do this carefully.
			boolean exactMatch = false;
			for (Road road : selected) {
				if (road.name.equals(query)) {
					exactMatch = true;
					Dimension area = getDrawingAreaDimension();
					Location l = road.components.iterator().next().start.location;
					Point p = l.asPoint(origin, scale);
					Point o = new Point((int) (p.getX() - (area.getWidth() / 2)), (int) (p.getY() - (area.getHeight() / 2)));
					origin = Location.newFromPoint(o, origin, scale);
				}
			}

			// make a set of all the roads that match exactly, and make this our new
			// selected set.
			if (exactMatch) {
				Collection<Road> exactMatches = new HashSet<>();
				for (Road road : selected)
					if (road.name.equals(query))
						exactMatches.add(road);
				selected = exactMatches;
			}

			// set the highlighted roads.
			graph.setHighlight(selected);

			// now build the string for display. we filter out duplicates by putting
			// it through a set first, and then combine it.
			Collection<String> names = new HashSet<>();
			for (Road road : selected)
				names.add(road.name);
			String str = "";
			for (String name : names)
				str += name + "; ";

			if (str.length() != 0)
				str = str.substring(0, str.length() - 2);
			getTextOutputArea().setText(str);
		}
	}

	@Override
	protected void onMove(Move m) {
		if (m == GUI.Move.NORTH) {
			origin = origin.moveBy(0, MOVE_AMOUNT / scale);
		} else if (m == GUI.Move.SOUTH) {
			origin = origin.moveBy(0, -MOVE_AMOUNT / scale);
		} else if (m == GUI.Move.EAST) {
			origin = origin.moveBy(MOVE_AMOUNT / scale, 0);
		} else if (m == GUI.Move.WEST) {
			origin = origin.moveBy(-MOVE_AMOUNT / scale, 0);
		} else if (m == GUI.Move.ZOOM_IN) {
			if (scale < MAX_ZOOM) {
				// yes, this does allow you to go slightly over/under the
				// max/min scale, but it means that we always zoom exactly to
				// the centre.
				scaleOrigin(true);
				scale *= ZOOM_FACTOR;
			}
		} else if (m == GUI.Move.ZOOM_OUT) {
			if (scale > MIN_ZOOM) {
				scaleOrigin(false);
				scale /= ZOOM_FACTOR;
			}
		}
	}

	@Override
	protected void onScroll(MouseWheelEvent e) {
		Point p = new Point(e.getX(), e.getY());
		if (e.getWheelRotation() == 1 && scale > MIN_ZOOM) {
			zoom(p, scale/ZOOM_FACTOR);
		} else if(scale < MAX_ZOOM) {
			zoom(p, scale*ZOOM_FACTOR);
		}
	}

	protected void onSwitchFocus(){
		forDistance = !forDistance;
		System.out.println(forDistance);
	}

	@Override
	protected void onDriving() {
		driving = true;
		walking = false;
		biking = false ;
	}

	@Override
	protected void onWalking() {
		driving = false;
		walking = true;
		biking = false;
	}

	@Override
	protected void onBiking() {
		driving = false;
		walking = false;
		biking = true;
	}

	@Override
	protected void onPlotRoute() {
		Path p = new Path(graph.nodes.get(27575), graph.nodes.get(42637), graph);
		getTextOutputArea().setText("Searching for path");
		if(p.findPathAStar(forDistance, driving, walking ,biking)) {
			p.drawPath(graph);
			getTextOutputArea().setText(p.toString());
		}else{
			getTextOutputArea().setText("There is no path between the nodes\n\n"+clickedNodes.get(0).toString()+"\n\nand\n\n"+clickedNodes.get(1).toString());
		}
		redraw();
	}

	@Override
	protected void onArticulation() {
		ArticulationPoints a = new ArticulationPoints(graph);
		graph.setHighlightedNodes(a.getArticulationPoints());
		graph.setHighlightedSegments(new HashSet<>());
		getTextOutputArea().setText(a.getNumArticulationPoints()+" Articulation points found");
		redraw();
	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons, File restrictions) {
		graph = new Graph(nodes, roads, segments, polygons, restrictions);
		trie = new Trie(graph.roads.values());
		origin = new Location(-250, 250); // close enough
		scale = 1;
	}

	/**
	 * This method does the nasty logic of making sure we always zoom into/out
	 * of the centre of the screen. It assumes that scale has just been updated
	 * to be either scale * ZOOM_FACTOR (zooming in) or scale / ZOOM_FACTOR
	 * (zooming out). The passed boolean should correspond to this, ie. be true
	 * if the scale was just increased.
	 */
	private void scaleOrigin(boolean zoomIn) {
		Dimension area = getDrawingAreaDimension();
		double zoom = zoomIn ? 1 / ZOOM_FACTOR : ZOOM_FACTOR;

		int dx = (int) ((area.width - (area.width * zoom)) / 2);
		int dy = (int) ((area.height - (area.height * zoom)) / 2);

		origin = Location.newFromPoint(new Point(dx, dy), origin, scale);
	}

	private void zoom(Point p, double s){
		Location l = Location.newFromPoint(p, origin, scale);
		scale = s;
		Point point = l.asPoint(origin, scale);
		int xOffset = (int)(point.getX()-p.getX());
		int yOffset = (int)(point.getY()-p.getY());
		Point oPoint = origin.asPoint(origin,scale);
		oPoint.x+=xOffset;
		oPoint.y+=yOffset;
		origin = Location.newFromPoint(oPoint, origin, scale);
		redraw();
	}

	public static void main(String[] args) {
		new Mapper();
	}
}

// code for COMP261 assignments