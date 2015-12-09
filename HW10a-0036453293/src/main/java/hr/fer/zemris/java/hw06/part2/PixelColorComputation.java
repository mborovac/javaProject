package hr.fer.zemris.java.hw06.part2;

import hr.fer.zemris.java.tecaj_06.rays.GraphicalObject;
import hr.fer.zemris.java.tecaj_06.rays.LightSource;
import hr.fer.zemris.java.tecaj_06.rays.Point3D;
import hr.fer.zemris.java.tecaj_06.rays.Ray;
import hr.fer.zemris.java.tecaj_06.rays.RayIntersection;
import hr.fer.zemris.java.tecaj_06.rays.RayTracerViewer;
import hr.fer.zemris.java.tecaj_06.rays.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * CLass used to calculate the color of pixels using Ray Caster algorithm.
 * Class is intended for use as a Recursive Action.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class PixelColorComputation extends RecursiveAction {
	
	private static final long serialVersionUID = 1L;
	Point3D eye;
	Point3D view; 
	Point3D viewUp;
	double horizontal;
	double vertical;
	int width; 
	int height;
	short[] red;
	short[] green;
	short[] blue;
	int yMin;
	int yMax;
	final static int LIMIT = 16;
	
	/**
	 * Class constructor. Constructor takes 12 arguments: point representing the viewer's position, 
	 * point representing the referred point in the scene, a view-up vector, horizontal width of observed space, 
	 * vertical vertical height of observed space, number of pixels per screen row, number of pixel per screen column,
	 * 3 times array of size width*height representing the rgb components for each pixel and starting an ending
	 * pixel of the current calculation
	 * 
	 * @param eye
	 * @param view
	 * @param viewUp viewUp specification of view-up vector which is used to determine y-axis for screen
	 * @param horizontal horizontal width of observed space
	 * @param vertical vertical height of observed space
	 * @param width number of pixels per screen row
	 * @param height number of pixel per screen column
	 * @param red array of size width*height representing the rgb red component for each pixel
	 * @param green array of size width*height representing the rgb green component for each pixel
	 * @param blue array of size width*height representing the rgb blue component for each pixel
	 * @param yMin starting pixel of the current calculation
	 * @param yMax ending pixel of the current calculation
	 */
	public PixelColorComputation(Point3D eye, Point3D view, Point3D viewUp,
			double horizontal, double vertical, int width, int height,
			short[] red, short[] green, short[] blue, int yMin, int yMax) {
		super();
		this.eye = eye;
		this.view = view;
		this.viewUp = viewUp;
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.width = width;
		this.height = height;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.yMin = yMin;
		this.yMax = yMax;
	}
	
	/**
	 * @see java.util.concurrent.RecursiveAction#compute()
	 */
	@Override
	protected void compute() {
		if (yMax - yMin + 1 < LIMIT) {
			createScene();
			return;
		}
		invokeAll(new PixelColorComputation(eye, view, viewUp, horizontal, vertical, width, height, red, green, blue,
				yMin, yMin + (yMax - yMin)/2));
		invokeAll(new PixelColorComputation(eye, view, viewUp, horizontal, vertical, width, height, red, green, blue,
				yMin + (yMax - yMin)/2, yMax));
	}
	
	/**
	 * Method used to calculate all the vectors needed to create a scene, create it and color every pixel
	 * according to what the viewer can see.
	 */
	private void createScene() {
		Point3D og = view.sub(eye).normalize();
		Point3D vuv = viewUp.normalize();
	//	Point3D zAxis = ...
		Point3D yAxis = vuv.sub(og.scalarMultiply(og.scalarProduct(vuv))).normalize();
		Point3D xAxis = og.vectorProduct(yAxis).normalize();
		Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2.0)).add(yAxis.scalarMultiply(vertical/2.0));
		Scene scene = RayTracerViewer.createPredefinedScene();
		short[] rgb = new short[3];
		int offset = width*yMin;
		for(int y = yMin; y < yMax; y++) {
			for(int x = 0; x < width; x++) {
				Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x*horizontal/(width-1.0)))
						.sub(yAxis.scalarMultiply(y*vertical/(height-1.0)));
				Ray ray = Ray.fromPoints(eye, screenPoint);
				traceTheRay(scene, ray, rgb);
				red[offset] = rgb[0] > 255 ? 255 : rgb[0];
				green[offset] = rgb[1] > 255 ? 255 : rgb[1];
				blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
				offset++;
			}
		}
	}
	
	/**
	 * Method used to trace a ray in the scene. It calculates all the intersections of that ray with
	 * every object in the scene and determines the one closest to the viewer. It then calculates the 
	 * effects of each light source on the calculated intersection and calculates the rbg components 
	 * of the pixel representing the intersection.
	 * The method is huge but breaking it in to smaller methods is less effective for the image creation.
	 * 
	 * @param scene scene the ray is placed and traced in
	 * @param ray ray that is being traced
	 * @param rgb rbg components of the intersection pixel
	 */
	public static void traceTheRay(Scene scene, Ray ray, short[] rgb) {
		List<LightSource> lightSources = new ArrayList<>(scene.getLights());
		List<GraphicalObject> objects = new ArrayList<>(scene.getObjects());
		RayIntersection closestObjectIntersection = null;
		// finding intersections with every object in the scene
		// this part of code is used twice without creating a new method purely because of the code performance
		for(GraphicalObject object: objects) {
			RayIntersection intersection = object.findClosestRayIntersection(ray);
			if(intersection != null) {
				if(closestObjectIntersection == null) {
					closestObjectIntersection = intersection;
				} else {
					if(intersection.getDistance() < closestObjectIntersection.getDistance()) {
						closestObjectIntersection = intersection;
					}
				}
			}
		}
		// no intersections
		if(closestObjectIntersection == null) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
			
		} else {
			rgb[0] = 15;
			rgb[1] = 15;
			rgb[2] = 15;
			
			// calculating effects of each light source
			for(LightSource lightSource: lightSources) {
				Ray lightSourceRay = Ray.fromPoints(lightSource.getPoint(), closestObjectIntersection.getPoint());
				RayIntersection lightRayClosestIntersection = null;
				for(GraphicalObject object: objects) {
					RayIntersection lightRayIntersection = object.findClosestRayIntersection(lightSourceRay);
					if(lightRayIntersection != null) {
						if(lightRayClosestIntersection == null) {
							lightRayClosestIntersection = lightRayIntersection;
						} else {
							if(lightRayIntersection.getDistance() < lightRayClosestIntersection.getDistance()) {
								lightRayClosestIntersection = lightRayIntersection;
							}
						}
					}
				}
				if(lightRayClosestIntersection == null) {
					continue;
				} else {
					double lightRayIntersectionDistance = lightSource.getPoint()
							.sub(lightRayClosestIntersection.getPoint()).norm();
					double seenObjectDistance = lightSource.getPoint()
							.sub(closestObjectIntersection.getPoint()).norm();
					if(lightRayIntersectionDistance < seenObjectDistance && 
							Math.abs(seenObjectDistance - lightRayIntersectionDistance) > 1.e-7) {
						continue;
					} else {
						// calculating the effects of the light source according to phong's model
						double red = lightSource.getR();
						double green = lightSource.getG();
						double blue = lightSource.getB();
						double kdr = lightRayClosestIntersection.getKdr();
						double kdg = lightRayClosestIntersection.getKdg();
						double kdb = lightRayClosestIntersection.getKdb();
						double krr = lightRayClosestIntersection.getKrr();
						double krg = lightRayClosestIntersection.getKrg();
						double krb = lightRayClosestIntersection.getKrb();
						double krn = lightRayClosestIntersection.getKrn();
						Point3D normal = lightRayClosestIntersection.getNormal();
						Point3D l = lightSource.getPoint().sub(lightRayClosestIntersection.getPoint()).normalize();
						Point3D r = l.sub(normal.scalarMultiply(l.scalarProduct(normal)).scalarMultiply(2.0)).normalize();
						double angleKoef = l.scalarProduct(normal);
						double alpha = ray.direction.scalarProduct(r);
						if(alpha < 0) {
							alpha = 0;
						}
						double reflKoef = Math.pow(alpha, krn);
						
						// calculating rgb color components
						// red
						rgb[0] += angleKoef*red*kdr + reflKoef*red*krr;
						// green
						rgb[1] += angleKoef*green*kdg + reflKoef*green*krg;
						// blue
						rgb[2] += angleKoef*blue*kdb + reflKoef*blue*krb;
					}
				}
			}
		}
	}
}
