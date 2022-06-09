package renderer;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import renderer.Scene.Polygon;

public class Renderer extends GUI {

	private Scene scene;
	private Vector3D veiw = new Vector3D(0, 0, 0);

	@Override
	protected void onLoad(File file) {
		/*
		 * This method should parse the given file into a Scene object, which
		 * you store and use to render an image.
		 */
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			String[] parts = line.split(" ");
			float x = Float.parseFloat(parts[0]);
			float y = Float.parseFloat(parts[1]);
			float z = Float.parseFloat(parts[2]);

			Vector3D lightPos = new Vector3D(x, y, z);

			List<Polygon> polygons = new ArrayList<>();
			while (br.ready()) {
				line = br.readLine();
				parts = line.split(" ");
				float[] points = new float[9];
				for(int i=0; i<9; i++){
					points[i] = Float.parseFloat(parts[i]);
				}
				int[] colour = new int[3];
				for(int i=0; i<3; i++) {
					colour[i] = Integer.parseInt(parts[9+i]);
				}
				polygons.add(new Polygon(points, colour));
			}

			scene = new Scene(polygons, lightPos);
			veiw = new Vector3D(0, 0, 0);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	@Override
	protected void onKeyPress(KeyEvent ev) {
		/*
		 * This method should be used to rotate the user's viewpoint.
		 */
		if(ev.getKeyCode()==37 || ev.getKeyCode()==65){//left
			scene = Pipeline.rotateScene(scene, 0, 0.1f);
			redraw();
		}else if(ev.getKeyCode()==39 || ev.getKeyCode()==68){//right
			scene = Pipeline.rotateScene(scene, 0, -0.1f);
			System.out.println("rotate");
			redraw();
		}else if(ev.getKeyCode()==38 || ev.getKeyCode()==87){//up
			scene = Pipeline.rotateScene(scene, 0.1f, 0);
			redraw();
		}else if(ev.getKeyCode()==40 || ev.getKeyCode()==83){//down
			scene = Pipeline.rotateScene(scene, -0.1f, 0);
			redraw();
		}
	}

	@Override
	protected BufferedImage render() {
		/*
		 * This method should put together the pieces of your renderer, as
		 * described in the lecture. This will involve calling each of the
		 * static method stubs in the Pipeline class, which you also need to
		 * fill in.
		 */

		if(scene==null){
			return null;
		}

		float scaleFactor = getScaleFactor();
		float xTranslation = getXTranslation();
		float yTranslation = getYTranslation();

		//scene = Pipeline.rotateScene(scene, veiw.x, veiw.y);
		scene = Pipeline.translateScene(scene, xTranslation, yTranslation, 0);
		scene = Pipeline.scaleScene(scene, scaleFactor, scaleFactor, scaleFactor);
		scene.setBoundingBox();

		Color[][] zBuffer = new Color[CANVAS_WIDTH][CANVAS_HEIGHT];
		float[][] zDepth = new float[CANVAS_WIDTH][CANVAS_HEIGHT];
		for(int x=0; x<CANVAS_WIDTH; x++){
			for(int y=0; y<CANVAS_HEIGHT; y++){
				zBuffer[x][y] = new Color(200,200,200);
				zDepth[x][y] = Float.POSITIVE_INFINITY;
			}
		}
		List<Polygon>  polygons =  scene.getPolygons();
		Vector3D lightPos = scene.getLight();
		int[] lightC = getAmbientLight();
		Color lightColour = new Color(lightC[0], lightC[1], lightC[2]);

		for (Polygon p : polygons) {
			if(!Pipeline.isHidden(p)) {
				Color colour = Pipeline.getShading(p, lightPos, lightColour, lightColour);
				EdgeList edgeList =  Pipeline.computeEdgeList(p);
				Pipeline.computeZBuffer(zBuffer, zDepth, edgeList, colour);
				Pipeline.drawEdges(zBuffer, zDepth, edgeList, colour);
			}
		}

		return convertBitmapToImage(zBuffer);
	}

	private float getScaleFactor(){
		Vector3D min = scene.getBoundingMin();
		Vector3D max = scene.getBoundingMax();
		return  CANVAS_WIDTH/(max.x - min.x);
	}

	private float getXTranslation(){
		Vector3D min = scene.getBoundingMin();
		Vector3D max = scene.getBoundingMax();
		if(min.x<0){
			return Math.abs(min.x);
		}else if(max.x>CANVAS_WIDTH){
			return CANVAS_WIDTH-max.x;
		}
		return 0;
	}

	private float getYTranslation(){
		Vector3D min = scene.getBoundingMin();
		Vector3D max = scene.getBoundingMax();
		if(min.y<0){
			return Math.abs(min.y);
		}else if(max.y>CANVAS_HEIGHT){
			return CANVAS_HEIGHT-max.y;
		}
		return 0;
	}

	/**
	 * Converts a 2D array of Colors to a BufferedImage. Assumes that bitmap is
	 * indexed by column then row and has imageHeight rows and imageWidth
	 * columns. Note that image.setRGB requires x (col) and y (row) are given in
	 * that order.
	 */
	private BufferedImage convertBitmapToImage(Color[][] bitmap) {
		BufferedImage image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < CANVAS_WIDTH; x++) {
			for (int y = 0; y < CANVAS_HEIGHT; y++) {
				image.setRGB(x, y, bitmap[x][y].getRGB());
			}
		}
		return image;
	}

	public static void main(String[] args) {
		new Renderer();
	}
}

// code for comp261 assignments
