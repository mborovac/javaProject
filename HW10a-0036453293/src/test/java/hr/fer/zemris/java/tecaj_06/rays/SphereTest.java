package hr.fer.zemris.java.tecaj_06.rays;

import static org.junit.Assert.*;

import org.junit.Test;

public class SphereTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void sphereConstructorTestException() {
		new Sphere(null, 4.0, 15, 15, 15, 15, 15, 15, 20);
	}
	
	@Test
	public void sphereConstructorAndGetterTest() {
		Sphere sphere = new Sphere(new Point3D(), 4.0, 15, 15, 15, 15, 15, 15, 20);
		assertEquals("expected = 0", 0, sphere.getCenter().x, 1e-6);
		assertEquals("expected = 0", 0, sphere.getCenter().y, 1e-6);
		assertEquals("expected = 0", 0, sphere.getCenter().z, 1e-6);
		assertEquals("expected = 4", 4, sphere.getRadius(), 1e-6);
		assertEquals("expected = 15", 15, sphere.getKdr(), 1e-6);
		assertEquals("expected = 15", 15, sphere.getKdg(), 1e-6);
		assertEquals("expected = 15", 15, sphere.getKdb(), 1e-6);
		assertEquals("expected = 15", 15, sphere.getKrr(), 1e-6);
		assertEquals("expected = 15", 15, sphere.getKrg(), 1e-6);
		assertEquals("expected = 15", 15, sphere.getKrb(), 1e-6);
		assertEquals("expected = 20", 20, sphere.getKrn(), 1e-6);
	}
	
	@Test
	public void rayIntersectionTest1() {
		Sphere sphere = new Sphere(new Point3D(), 4.0, 15, 15, 15, 15, 15, 15, 20);
		Ray ray = new Ray(new Point3D(0,0,0), new Point3D(1,0,0));
		assertEquals("expected = 4", 4, sphere.findClosestRayIntersection(ray).getDistance(), 1e-6);
	}
	
	@Test
	public void rayIntersectionTest2() {
		Sphere sphere = new Sphere(new Point3D(), 4.0, 15, 15, 15, 15, 15, 15, 20);
		Ray ray = new Ray(new Point3D(5,0,0), new Point3D(-1,0,0));
		assertEquals("expected = 1", 1, sphere.findClosestRayIntersection(ray).getDistance(), 1e-6);
	}
	
	@Test
	public void rayIntersectionTest3() {
		Sphere sphere = new Sphere(new Point3D(10,10,10), 2.0, 15, 15, 15, 15, 15, 15, 20);
		Ray ray = new Ray(new Point3D(5,0,0), new Point3D(1,0,0));
		assertNull(sphere.findClosestRayIntersection(ray));
	}
	
	@Test
	public void rayIntersectionTest4() {
		Sphere sphere = new Sphere(new Point3D(2,2,2), 2.0, 15, 15, 15, 15, 15, 15, 20);
		Ray ray = new Ray(new Point3D(-2,0,0), new Point3D(1,0,0));
		assertNull(sphere.findClosestRayIntersection(ray));
	}
	
	@Test
	public void rayIntersectionTest5() {
		Sphere sphere = new Sphere(new Point3D(0,0,0), 4.0, 15, 15, 15, 15, 15, 15, 20);
		Ray ray = Ray.fromPoints(new Point3D(5,0,0), new Point3D(4,0,0));
		assertEquals("expected = 1", 1, sphere.findClosestRayIntersection(ray).getDistance(), 1e-6);
	}
}
