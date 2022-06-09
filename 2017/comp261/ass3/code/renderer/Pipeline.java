package renderer;

import java.awt.Color;
import java.util.List;

import renderer.Scene.Polygon;

/**
 * The Pipeline class has method stubs for all the major components of the
 * rendering pipeline, for you to fill in.
 * 
 * Some of these methods can get quite long, in which case you should strongly
 * consider moving them out into their own file. You'll need to update the
 * imports in the test suite if you do.
 */
public class Pipeline {

	/**
	 * Returns true if the given polygon is facing away from the camera (and so
	 * should be hidden), and false otherwise.
	 */
	public static boolean isHidden(Polygon poly) {
		Vector3D[] vertices = poly.getVertices();
		Vector3D a = vertices[1].minus(vertices[0]);
		Vector3D b = vertices[2].minus(vertices[1]);
		Vector3D axb = a.crossProduct(b);
		if(axb.z>=0){
			return true;
		}
		return false;
	}

	/**
	 * Computes the colour of a polygon on the screen, once the lights, their
	 * angles relative to the polygon's face, and the reflectance of the polygon
	 * have been accounted for.
	 * 
	 * @param lightDirection
	 *            The Vector3D pointing to the directional light read in from
	 *            the file.
	 * @param lightColor
	 *            The color of that directional light.
	 * @param ambientLight
	 *            The ambient light in the scene, i.e. light that doesn't depend
	 *            on the direction.
	 */
	public static Color getShading(Polygon poly, Vector3D lightDirection, Color lightColor, Color ambientLight) {
		int rR = poly.getReflectance().getRed();
		int gR = poly.getReflectance().getGreen();
		int bR = poly.getReflectance().getBlue();

		float rI = lightColor.getRed()/(float)255;
		float gI = lightColor.getGreen()/(float)255;
		float bI = lightColor.getBlue()/(float)255;

		float rA = ambientLight.getRed()/(float)255;
		float gA = ambientLight.getGreen()/(float)255;
		float bA = ambientLight.getBlue()/(float)255;

		Vector3D n = getNormalVector(poly);
		n = n.unitVector();
		Vector3D d = lightDirection.unitVector();
		float angle = n.cosTheta(d);
		if(angle<0){
			rI=0;
			gI=0;
			bI=0;
		}

		int r = (int)((rA+rI*angle)*rR);
		int g = (int)((gA+gI*angle)*gR);
		int b = (int)((bA+bI*angle)*bR);
		if(r>255){
			r=255;
		}else if(g>255){
			g=255;
		}else if(b>255){
			b=255;
		}
		return new Color(r, g, b);
	}

	private static Vector3D getNormalVector(Polygon poly){
		Vector3D v1 = poly.getVertices()[0];
		Vector3D v2 = poly.getVertices()[1];
		Vector3D v3 = poly.getVertices()[2];

		Vector3D a = v2.minus(v1);
		Vector3D b = v3.minus(v2);

		return a.crossProduct(b);
	}

	/**
	 * This method should rotate the polygons and light such that the viewer is
	 * looking down the Z-axis. The idea is that it returns an entirely new
	 * Scene object, filled with new Polygons, that have been rotated.
	 * 
	 * @param scene
	 *            The original Scene.
	 * @param xRot
	 *            An angle describing the viewer's rotation in the YZ-plane (i.e
	 *            around the X-axis).
	 * @param yRot
	 *            An angle describing the viewer's rotation in the XZ-plane (i.e
	 *            around the Y-axis).
	 * @return A new Scene where all the polygons and the light source have been
	 *         rotated accordingly.
	 */
	public static Scene rotateScene(Scene scene, float xRot, float yRot) {
		Transform xRotation = Transform.newXRotation(xRot);
		Transform yRotation = Transform.newYRotation(yRot);
		Vector3D lightPos = xRotation.multiply(scene.getLight());
		List<Polygon> polygons = scene.getPolygons();

		polygons = transformPolygons(xRotation, polygons);
		polygons = transformPolygons(yRotation, polygons);
		return new Scene(polygons, lightPos);
	}

	/**
	 * This should translate the scene by the appropriate amount.
	 * 
	 * @param scene
	 * @return
	 */
	public static Scene translateScene(Scene scene, float tx, float ty, float tz) {
		Transform translate = Transform.newTranslation(tx, ty, tz);
		Vector3D lightPos = translate.multiply(scene.getLight());
		List<Polygon> polygons = scene.getPolygons();
		polygons = transformPolygons(translate, polygons);
		return new Scene(polygons, lightPos);
	}

	/**
	 * This should scale the scene.
	 * 
	 * @param scene
	 * @return
	 */
	public static Scene scaleScene(Scene scene, float sx, float sy, float sz) {
		Transform scale = Transform.newScale(sx, sy, sz);

		Vector3D lightPos = scale.multiply(scene.getLight());
		List<Polygon> polygons = transformPolygons(scale, scene.getPolygons());
		return new Scene(polygons, lightPos);
	}

	private static List<Polygon> transformPolygons(Transform transform, List<Polygon> polygons){
		for (Polygon p : polygons) {
			Vector3D[] vertices = p.getVertices();
			vertices[0] = transform.multiply(vertices[0]);
			vertices[1] = transform.multiply(vertices[1]);
			vertices[2] = transform.multiply(vertices[2]);
		}
		return polygons;
	}

	/**
	 * Computes the edgelist of a single provided polygon, as per the lecture
	 * slides.
	 */
	public static EdgeList computeEdgeList(Polygon poly) {
		Vector3D[] vertices = poly.getVertices();
		float v1y = vertices[0].y;
		float v2y = vertices[1].y;
		float v3y = vertices[2].y;

		int startY = (int)Math.min(v1y, Math.min(v2y,v3y));
		int endY = (int)Math.max(v1y, Math.max(v2y,v3y));
		EdgeList edgeList = new EdgeList(startY, endY);

		for(int i=0; i<3; i++){
			Vector3D a = vertices[i];
			Vector3D b;
			if(i==2){
				b = vertices[0];
			}else{
				b = vertices[i+1];
			}
			float slopeX = (b.x-a.x)/(b.y-a.y);
			float slopeZ = (b.z-a.z)/(b.y-a.y);
			float x = a.x;
			int y = (int)a.y;
			float z = a.z;

			if(a.y<b.y){
				while(y<=(int)b.y){
					float xLeft = x;
					float zLeft = z;
					edgeList.addLeftOfRow(y, xLeft, zLeft);
					x = x+slopeX;
					z = z+slopeZ;
					y++;
				}
			}else{
				while(y>=(int)b.y){
					float xRight = x;
					float zRight = z;
					edgeList.addRightOfRow(y, xRight, zRight);
					x = x-slopeX;
					z = z-slopeZ;
					y--;
				}
			}
		}
		return edgeList;
	}

	/**
	 * Fills a zbuffer with the contents of a single edge list according to the
	 * lecture slides.
	 * 
	 * The idea here is to make zbuffer and zdepth arrays in your main loop, and
	 * pass them into the method to be modified.
	 * 
	 * @param zbuffer
	 *            A double array of colours representing the Color at each pixel
	 *            so far.
	 * @param zdepth
	 *            A double array of floats storing the z-value of each pixel
	 *            that has been coloured in so far.
	 * @param polyEdgeList
	 *            The edgelist of the polygon to add into the zbuffer.
	 * @param polyColor
	 *            The colour of the polygon to add into the zbuffer.
	 */
	public static void computeZBuffer(Color[][] zbuffer, float[][] zdepth, EdgeList polyEdgeList, Color polyColor) {
		int startY = polyEdgeList.getStartY();
		int endY = polyEdgeList.getEndY();
		float slope;
		float z;

		for (int y=startY; y<endY; y++){
			slope = (polyEdgeList.getRightZ(y)-polyEdgeList.getLeftZ(y))/(polyEdgeList.getRightX(y)-polyEdgeList.getLeftX(y));
			z = polyEdgeList.getLeftZ(y);

			for(int x=(int)polyEdgeList.getLeftX(y); x<(int)polyEdgeList.getRightX(y); x++){
				if(x>=0 && x<zbuffer.length && y>=0 && y<zbuffer[x].length) {
					if (z < zdepth[x][y]) {
						zbuffer[x][y] = polyColor;
						zdepth[x][y] = z;
					}
				}
				z += slope;
			}
		}
	}

	public static void drawEdges(Color[][] zbuffer, float[][] zdepth, EdgeList edgeList, Color colour){
		int startY = edgeList.getStartY();
		int endY = edgeList.getEndY();

		int x1;
		int x2;
		float z1;
		float z2;
		for (int y=startY; y<endY; y++){
			x1=(int)edgeList.getLeftX(y);
			x2=(int)edgeList.getRightX(y);
			z1 = edgeList.getLeftZ(y);
			z2 = edgeList.getRightZ(y);
			if((x1>=0 && x1<zbuffer.length) && (y>=0 && y<zbuffer[x1].length)) {
				if (z1 < zdepth[x1][y]) {
					zbuffer[x1][y] = colour;
				}
			}
			if((x2>=0 && x2<zbuffer.length) && (y>=0 && y<zbuffer[x2].length)) {
				if (z2 < zdepth[x2][y]) {
					zbuffer[x2][y] = colour;
				}
			}
		}
	}

}

// code for comp261 assignments
