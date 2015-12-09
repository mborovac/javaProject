package hr.fer.zemris.java.hw06.part2;

import java.util.concurrent.ForkJoinPool;

import hr.fer.zemris.java.tecaj_06.rays.IRayTracerProducer;
import hr.fer.zemris.java.tecaj_06.rays.IRayTracerResultObserver;
import hr.fer.zemris.java.tecaj_06.rays.Point3D;
import hr.fer.zemris.java.tecaj_06.rays.RayTracerViewer;

/**
 * Class used to implement the computer graphic's ray caster algorithm using multiple threads
 * and ForkJoin framework.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class RayCasterParallel {
	
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
			 *  hr.fer.zemris.java.tecaj_06.rays.Point3D, double, double, int, int, long, 
			 *  hr.fer.zemris.java.tecaj_06.rays.IRayTracerResultObserver)
			 */
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
			double horizontal, double vertical, int width, int height,
			long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new PixelColorComputation(
						eye, view, viewUp, horizontal, vertical, width, height, red, green, blue, 0, height));
				pool.shutdown();
					
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
}
