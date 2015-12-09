package hr.fer.zemris.java.hw11.jvdraw.model;

import java.awt.Point;

/**
 * Class used to represent the minimal bounding box of a component or set of components.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class BoundingBox {
	
	private Point topLeftPoint;
	private Point bottomRightPoint;
	
	/**
	 * Class constructor. Constructor takes 2 arguments, points representing the top left and bottom right
	 * corner of the bounding box.
	 * 
	 * @param topLeftPoint
	 * @param bottomRightPoint
	 */
	public BoundingBox(Point topLeftPoint, Point bottomRightPoint) {
		this.topLeftPoint = topLeftPoint;
		this.bottomRightPoint = bottomRightPoint;
	}

	/**
	 * Method used to get the x coordinate of the top left box corner.
	 * 
	 * @return returns the x coordinate of the top left box corner
	 */
	public int getX1() {
		return this.topLeftPoint.x;
	}
	
	/**
	 * Method used to get the y coordinate of the top left box corner.
	 * 
	 * @return returns the y coordinate of the top left box corner
	 */
	public int getY1() {
		return this.topLeftPoint.y;
	}
	
	/**
	 * Method used to get the x coordinate of the bottom right box corner.
	 * 
	 * @return returns the x coordinate of the bottom right box corner
	 */
	public int getX2() {
		return this.bottomRightPoint.x;
	}
	
	/**
	 * Method used to get the y coordinate of the bottom right box corner.
	 * 
	 * @return returns the y coordinate of the bottom right box corner
	 */
	public int getY2() {
		return this.bottomRightPoint.y;
	}
	
	 /**
	 * @see java.lang.Object#toString()
	 * 
	 * Format x1:number, x2: number, y1: number, y2: number
	 */
	@Override
	public String toString() {
		return "x1:" + topLeftPoint.x + ", y1:" + topLeftPoint.y + ", x2:" + 
				bottomRightPoint.x + ", y2:" + bottomRightPoint.y;
	}
}
