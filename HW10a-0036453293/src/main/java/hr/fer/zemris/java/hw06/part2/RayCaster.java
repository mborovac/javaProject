package hr.fer.zemris.java.hw06.part2;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj_06.rays.GraphicalObject;
import hr.fer.zemris.java.tecaj_06.rays.IRayTracerProducer;
import hr.fer.zemris.java.tecaj_06.rays.IRayTracerResultObserver;
import hr.fer.zemris.java.tecaj_06.rays.LightSource;
import hr.fer.zemris.java.tecaj_06.rays.Point3D;
import hr.fer.zemris.java.tecaj_06.rays.Ray;
import hr.fer.zemris.java.tecaj_06.rays.RayIntersection;
import hr.fer.zemris.java.tecaj_06.rays.RayTracerViewer;
import hr.fer.zemris.java.tecaj_06.rays.Scene;

/**
 * Class used to implement the computer graphic's ray caster algorithm.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class RayCaster {
	
	/**
	 * Main method used to run the program. Program requires no arguments.
	 * 
	 * @param args program arguments, all arguments will be ignored
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
		new Point3D(10,0,0),
		new Point3D(0,0,0),
		new Point3D(0,0,10),
		20, 20);
	}
	
	/**
	 * Method used to create an object capable of creating scene snapshots.
	 * 
	 * @return returns the created object which will draw our scene
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			/**
			 * @see hr.fer.zemris.java.tecaj_06.rays.IRayTracerProducer#produce(
			 * hr.fer.zemris.java.tecaj_06.rays.Point3D, hr.fer.zemris.java.tecaj_06.rays.Point3D, 
			 * hr.fer.zemris.java.tecaj_06.rays.Point3D, double, double, int, int, long, 
			 * hr.fer.zemris.java.tecaj_06.rays.IRayTracerResultObserver)
			 */
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
			double horizontal, double vertical, int width, int height,
			long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				
				Point3D og = view.sub(eye).normalize();
				Point3D vuv = viewUp.normalize();
				
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
//				Point3D zAxis = ...
				Point3D yAxis = vuv.sub(og.scalarMultiply(og.scalarProduct(vuv))).normalize();
				Point3D xAxis = og.vectorProduct(yAxis).normalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2.0)).add(yAxis.scalarMultiply(vertical/2.0));
				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for(int y = 0; y < height; y++) {
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
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
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
	private static void traceTheRay(Scene scene, Ray ray, short[] rgb) {
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
