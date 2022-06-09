package renderer;

import java.util.HashMap;
import java.util.Map;

/**
 * EdgeList should store the data for the edge list of a single polygon in your
 * scene. A few method stubs have been provided so that it can be tested, but
 * you'll need to fill in all the details.
 *
 * You'll probably want to add some setters as well as getters or, for example,
 * an addRow(y, xLeft, xRight, zLeft, zRight) method.
 */
public class EdgeList {

	private int startY;
	private int endY;
	private Map<Integer, float[]> edgeList = new HashMap<>();

	public EdgeList(int startY, int endY) {
		this.startY = startY;
		this.endY = endY;
	}

	public void addRow(int y, float xLeft, float zLeft, float xRight, float zRight){
		float[] row = new float[4];
		row[0] = xLeft;
		row[1] = zLeft;
		row[2] = xRight;
		row[3] = zRight;
		edgeList.put(y, row);
	}

	public void addLeftOfRow(int y, float xLeft, float zLeft) {
		float[] row;
		if (edgeList.containsKey(y)) {
			row = edgeList.get(y);
		}else{
			row = new float[4];
		}
		row[0] = xLeft;
		row[1] = zLeft;
		edgeList.put(y, row);
	}

	public void addRightOfRow(int y, float xRight, float zRight) {
		float[] row;
		if (edgeList.containsKey(y)) {
			row = edgeList.get(y);
		}else{
			row = new float[4];
		}
		row[2] = xRight;
		row[3] = zRight;
		edgeList.put(y, row);
	}

	public int getStartY() {
		return startY;
	}

	public int getEndY() {
		return endY;
	}

	public float getLeftX(int y) {
		return edgeList.get(y)[0];
	}

	public float getRightX(int y) {
		return edgeList.get(y)[2];
	}

	public float getLeftZ(int y) {
		return edgeList.get(y)[1];
	}

	public float getRightZ(int y) {
		return edgeList.get(y)[3];
	}
}

// code for comp261 assignments
