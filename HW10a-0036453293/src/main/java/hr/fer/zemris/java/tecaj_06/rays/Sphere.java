package hr.fer.zemris.java.tecaj_06.rays;

/**
 * CLass representing a sphere graphical object. It is represented by its center point and a radius.
 * Kd* properties are parameters for diffuse component, kr* properties are parameters for reflective component
 * of the object.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Sphere extends GraphicalObject {
	
	private Point3D center;
	private double radius;
	private double kdr;
	private double kdg;
	private double kdb;
	private double krr;
	private double krg;
	private double krb;
	private double krn;
	
	/**
	 * Class constructor. Constructor takes 9 arguments, the center point of the sphere, its radius and
	 * parameters of the diffuse and reflective component.
	 * 
	 * @param center sphere center point
	 * @param radius radius of the sphere
	 * @param kdr red diffuse component
	 * @param kdg green diffuse component
	 * @param kdb blue diffuse component
	 * @param krr red reflective component
	 * @param krg green reflective component
	 * @param krb blue reflective component
	 * @param krn spheres' outer layer roughness
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		if(center == null) {
			throw new IllegalArgumentException("Center point can not be null.");
		}
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj_06.rays.GraphicalObject#findClosestRayIntersection(hr.fer.zemris.java.tecaj_06.rays.Ray)
	 */
	@Override
	public RayAndSphereIntersection findClosestRayIntersection(Ray ray) {
		Point3D vectorToCenter = ray.start.sub(this.center);
		double discriminant = vectorToCenter.scalarProduct(ray.direction)*vectorToCenter.scalarProduct(ray.direction)
				- (vectorToCenter.scalarProduct(vectorToCenter) - this.radius*this.radius);
		// single intersection, the ray is the sphere's tangent
		if (discriminant == 0) {
			discriminant = Math.sqrt(discriminant);
			double intersection = -vectorToCenter.scalarProduct(ray.direction) + discriminant;
			// intersection is behind the viewer, we don't care about it
			if(intersection < 0) {
				return null;
			}
			return createIntersection(ray, intersection, true);
		} else {
			// no intersection
			if(discriminant < 0) {
				return null;
			// discriminant is > 0, 2 intersections
			} else {
				discriminant = Math.sqrt(discriminant);
				double intersection1 = -vectorToCenter.scalarProduct(ray.direction) + discriminant;
				double intersection2 = -vectorToCenter.scalarProduct(ray.direction) - discriminant;
				// 2nd one is behind the viewer
				if(intersection1 >= 0 && intersection2 < 0) {
					return createIntersection(ray, intersection1, false);
				// 1st one is behind the viewer	
				} else if(intersection1 < 0 && intersection2 >= 0) {
					return createIntersection(ray, intersection2, false);
				// both are in front of the viewer, the closer one is needed	
				} else {
					double intersection = Math.min(intersection1, intersection2);
					return createIntersection(ray, intersection, true);
				}
			} 	
		}
	}
	
	/**
	 * Method creates an instance of the RayAndSphereIntersection object based on the given ray
	 * and its intersection point with the sphere.
	 * 
	 * @param ray
	 * @param intersection
	 * @param outer
	 * @return
	 */
	private RayAndSphereIntersection createIntersection(Ray ray, double intersection, boolean outer) {
		Point3D point = ray.start.add(ray.direction.scalarMultiply(intersection));
		double distance = ray.start.sub(point).norm();
		return new RayAndSphereIntersection(point, distance, outer, this);
	}

	/**
	 * Center parameter getter.
	 * 
	 * @return the center
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * Radius parameter getter.
	 * 
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Red diffuse component getter.
	 * 
	 * @return the kdr
	 */
	public double getKdr() {
		return kdr;
	}

	/**
	 * Green diffuse component getter.
	 * 
	 * @return the kdg
	 */
	public double getKdg() {
		return kdg;
	}

	/**
	 * Blue diffuse component getter.
	 * 
	 * @return the kdb
	 */
	public double getKdb() {
		return kdb;
	}

	/**
	 * Red reflective component getter.
	 * 
	 * @return the krr
	 */
	public double getKrr() {
		return krr;
	}

	/**
	 * Green reflective component getter.
	 * 
	 * @return the krg
	 */
	public double getKrg() {
		return krg;
	}

	/**
	 * Blue reflective component getter.
	 * 
	 * @return the krb
	 */
	public double getKrb() {
		return krb;
	}

	/**
	 * Spheres' outer layer roughness getter.
	 * 
	 * @return the krn
	 */
	public double getKrn() {
		return krn;
	}
}
