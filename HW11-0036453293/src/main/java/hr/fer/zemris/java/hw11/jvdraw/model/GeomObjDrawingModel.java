package hr.fer.zemris.java.hw11.jvdraw.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel;
import hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.observers.DrawingModelListener;

/**
 * Class used to hold all the components that the user has drawn. It provides the methods to update and remove
 * the components and each time some change happens all the registered listeners are informed about it.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class GeomObjDrawingModel implements DrawingModel {
	
	private List<GeometricalObject> listOfObjects;
	private List<DrawingModelListener> listeners;
	private int numberOfLines;
	private int numberOfCircles;
	private int numberOfFilledCircles;
	private boolean modified;
	
	/**
	 * Class constructor. Constructor takes no arguments.
	 */
	public GeomObjDrawingModel() {
		this.listOfObjects = new ArrayList<>();
		this.listeners = new ArrayList<>();
		this.numberOfLines = 0;
		this.numberOfCircles = 0;
		this.numberOfFilledCircles = 0;
		this.modified = false;
	}

	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#getSize()
	 */
	@Override
	public int getSize() {
		return this.listOfObjects.size();
	}

	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#getObject(int)
	 */
	@Override
	public GeometricalObject getObject(int index) {
		return this.listOfObjects.get(index);
	}

	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#add(
	 * hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject)
	 */
	@Override
	public void add(GeometricalObject object) {
		this.listOfObjects.add(object);
		this.modified = true;
		for(DrawingModelListener listener: listeners) {
			listener.objectsAdded(this, listOfObjects.size() - 1, listOfObjects.size() - 1);
		}
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#remove(
	 * hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject)
	 */
	@Override
	public void remove(GeometricalObject object) {
		this.listOfObjects.remove(object);
		this.modified = true;
		for(DrawingModelListener listener: listeners) {
			listener.objectsRemoved(this, listOfObjects.size(), listOfObjects.size());
		}
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#update(
	 * hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject, hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject)
	 */
	@Override
	public void update(GeometricalObject oldObj, GeometricalObject newObj) {
		int index;
		if(oldObj.equals(newObj)) {
			return;
		}
		if(this.listOfObjects.contains(oldObj)) {
			index = listOfObjects.indexOf(oldObj);
			listOfObjects.remove(index);
			listOfObjects.add(index, newObj);
			this.modified = true;
			for(DrawingModelListener listener: listeners) {
				listener.objectsChanged(this, index, index);
			}
		}
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#minBoundingBox()
	 */
	public BoundingBox minBoundingBox() {
		BoundingBox boundingBox = null;
		for(GeometricalObject object: listOfObjects) {
			BoundingBox temp = object.getBoundingBox();
			if(boundingBox == null) {
				boundingBox = temp;
			} else {
				int x1 = boundingBox.getX1() < temp.getX1() ? boundingBox.getX1() : temp.getX1();
				int y1 = boundingBox.getY1() < temp.getY1() ? boundingBox.getY1() : temp.getY1();
				int x2 = boundingBox.getX2() > temp.getX2() ? boundingBox.getX2() : temp.getX2();
				int y2 = boundingBox.getY2() > temp.getY2() ? boundingBox.getY2() : temp.getY2();
				boundingBox = new BoundingBox(new Point(x1, y1), new Point(x2, y2));
			}
		}
		return boundingBox;
	}
	
	/**)
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#clear()
	 */
	@Override
	public void clear() {
		this.listOfObjects = new ArrayList<>();
		this.numberOfLines = 0;
		this.numberOfCircles = 0;
		this.numberOfFilledCircles = 0;
		this.modified = true;
		for(DrawingModelListener listener: listeners) {
			listener.objectsRemoved(this, listOfObjects.size(), listOfObjects.size());
		}
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#getModified()
	 */
	@Override
	public boolean getModified() {
		return this.modified;
	}

	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#setModified(boolean)
	 */
	@Override
	public void setModified(boolean value) {
		this.modified = value;
	}

	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#addDrawingModelListener(
	 * hr.fer.zemris.java.hw11.jvdraw.observers.DrawingModelListener)
	 */
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if(!listeners.contains(l)) {
			listeners = new ArrayList<>(listeners);
			listeners.add(l);
		}
	}

	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#removeDrawingModelListener(
	 * hr.fer.zemris.java.hw11.jvdraw.observers.DrawingModelListener)
	 */
	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}

	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#numberOfLines()
	 */
	@Override
	public int numberOfLines() {
		return this.numberOfLines;
	}

	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#numberOfCircles()
	 */
	@Override
	public int numberOfCircles() {
		return this.numberOfCircles;
	}

	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#numberOfFilledCircles()
	 */
	@Override
	public int numberOfFilledCircles() {
		return this.numberOfFilledCircles;
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#setNumberOfLines()
	 */
	public void setNumberOfLines() {
		this.numberOfLines++;
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#setNumberOfCircles()
	 */
	public void setNumberOfCircles() {
		this.numberOfCircles++;
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel#setNumberOfFilledCircles()
	 */
	public void setNumberOfFilledCircles() {
		this.numberOfFilledCircles++;
	}
}
