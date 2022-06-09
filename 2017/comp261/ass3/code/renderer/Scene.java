package renderer;

import java.awt.Color;
import java.util.List;

/**
 * The Scene class is where we store data about a 3D model and light source
 * inside our renderer. It also contains a static inner class that represents one
 * single polygon.
 * 
 * Method stubs have been provided, but you'll need to fill them in.
 * 
 * If you were to implement more fancy rendering, e.g. Phong shading, you'd want
 * to store more information in this class.
 */
public class Scene {

	private List<Polygon> polygons;
	private Vector3D lightPos;
	private float boundingXMin = Float.POSITIVE_INFINITY;
	private float boundingYMin = Float.POSITIVE_INFINITY;
	private float boundingZMin = Float.POSITIVE_INFINITY;

	private float boundingXMax = Float.NEGATIVE_INFINITY;
	private float boundingYMax = Float.NEGATIVE_INFINITY;
	private float boundingZMax = Float.NEGATIVE_INFINITY;

	public Scene(List<Polygon> polygons, Vector3D lightPos) {
		this.polygons = polygons;
		this.lightPos = lightPos;
		setBoundingBox();
	}

	public Vector3D getLight() {
          return lightPos;
	}

	public List<Polygon> getPolygons() {
          return polygons;
	}

	public Vector3D getBoundingMin() {
		return new Vector3D(boundingXMin, boundingYMin, boundingZMin);
	}

	public Vector3D getBoundingMax() {
		return new Vector3D(boundingXMax, boundingYMax, boundingZMax);
	}

	public void setBoundingBox(){
		float x1;
		float y1;
		float z1;

		float x2;
		float y2;
		float z2;
		if(polygons!=null) {
			for (Polygon p : polygons) {
				Vector3D v1 = p.getVertices()[0];
				Vector3D v2 = p.getVertices()[1];
				Vector3D v3 = p.getVertices()[2];
				x1 = Math.min(v1.x, Math.min(v2.x, v3.x));
				y1 = Math.min(v1.y, Math.min(v2.y, v3.y));
				z1 = Math.min(v1.z, Math.min(v2.z, v3.z));

				x2 = Math.max(v1.x, Math.max(v2.x, v3.x));
				y2 = Math.max(v1.y, Math.max(v2.y, v3.y));
				z2 = Math.max(v1.z, Math.max(v2.z, v3.z));
				if (x1 < boundingXMin) {
					boundingXMin = x1;
				}
				if (y1 < boundingYMin) {
					boundingYMin = y1;
				}
				if (z1 < boundingZMin) {
					boundingZMin = z1;
				}

				if (x2 > boundingXMax) {
					boundingXMax = x2;
				}
				if (y2 > boundingYMax) {
					boundingYMax = y2;
				}
				if (z2 > boundingZMax) {
					boundingZMax = z2;
				}
			}
		}
	}


	/**
	 * Polygon stores data about a single polygon in a scene, keeping track of
	 * (at least!) its three vertices and its reflectance.
         *
         * This class has been done for you.
	 */
	public static class Polygon {
		Vector3D[] vertices;
		Color reflectance;

		/**
		 * @param points
		 *            An array of floats with 9 elements, corresponding to the
		 *            (x,y,z) coordinates of the three vertices that make up
		 *            this polygon. If the three vertices are A, B, C then the
		 *            array should be [A_x, A_y, A_z, B_x, B_y, B_z, C_x, C_y,
		 *            C_z].
		 * @param color
		 *            An array of three ints corresponding to the RGB values of
		 *            the polygon, i.e. [r, g, b] where all values are between 0
		 *            and 255.
		 */
		public Polygon(float[] points, int[] color) {
			this.vertices = new Vector3D[3];

			float x, y, z;
			for (int i = 0; i < 3; i++) {
				x = points[i * 3];
				y = points[i * 3 + 1];
				z = points[i * 3 + 2];
				this.vertices[i] = new Vector3D(x, y, z);
			}

			int r = color[0];
			int g = color[1];
			int b = color[2];
			this.reflectance = new Color(r, g, b);
		}

		/**
		 * An alternative constructor that directly takes three Vector3D objects
		 * and a Color object.
		 */
		public Polygon(Vector3D a, Vector3D b, Vector3D c, Color color) {
			this.vertices = new Vector3D[] { a, b, c };
			this.reflectance = color;
		}

		public Vector3D[] getVertices() {
			return vertices;
		}

		public Color getReflectance() {
			return reflectance;
		}

		@Override
		public String toString() {
			String str = "polygon:";

			for (Vector3D p : vertices)
				str += "\n  " + p.toString();

			str += "\n  " + reflectance.toString();

			return str;
		}
	}
}

// code for COMP261 assignments
