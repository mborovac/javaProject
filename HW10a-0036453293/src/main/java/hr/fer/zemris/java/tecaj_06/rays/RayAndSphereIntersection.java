package hr.fer.zemris.java.tecaj_06.rays;

/**
 * Class representing an intersection between a sphere and a ray.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class RayAndSphereIntersection extends RayIntersection {
	
	private Point3D normal;
	private double kdr;
	private double kdg;
	private double kdb;
	private double krr;
	private double krg;
	private double krb;
	private double krn;
	
	/**
	 * Class constructor. Constructor takes 4 arguments, point representing the coordinates of the intersection,
	 * intersection's distance from the viewer, flag determining whether the intersection is an outer one and
	 * the reference to the sphere the intersection is on.
	 * 
	 * @param point point representing the coordinates of the intersection
	 * @param distance intersection's distance from the viewer
	 * @param outer flag determining whether the intersection is an outer one
	 * @param sphere reference to the sphere the intersection is on
	 */
	public RayAndSphereIntersection(Point3D point, double distance, boolean outer, Sphere sphere) {
		super(point, distance, outer);
		this.normal = point.sub(sphere.getCenter()).normalize();
		this.kdr = sphere.getKdr();
		this.kdg = sphere.getKdg();
		this.kdb = sphere.getKdb();
		this.krr = sphere.getKrr();
		this.krg = sphere.getKrg();
		this.krb = sphere.getKrb();
		this.krn = sphere.getKrn();
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj_06.rays.RayIntersection#getNormal()
	 */
	@Override
	public Point3D getNormal() {
		return normal;
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj_06.rays.RayIntersection#getKdr()
	 */
	@Override
	public double getKdr() {
		return kdr;
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj_06.rays.RayIntersection#getKdg()
	 */
	@Override
	public double getKdg() {
		return kdg;
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj_06.rays.RayIntersection#getKdb()
	 */
	@Override
	public double getKdb() {
		return kdb;
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj_06.rays.RayIntersection#getKrr()
	 */
	@Override
	public double getKrr() {
		return krr;
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj_06.rays.RayIntersection#getKrg()
	 */
	@Override
	public double getKrg() {
		return krg;
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj_06.rays.RayIntersection#getKrb()
	 */
	@Override
	public double getKrb() {
		return krb;
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj_06.rays.RayIntersection#getKrn()
	 */
	@Override
	public double getKrn() {
		return krn;
	}
}
