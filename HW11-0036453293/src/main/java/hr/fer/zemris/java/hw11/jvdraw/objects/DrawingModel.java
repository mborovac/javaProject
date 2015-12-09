package hr.fer.zemris.java.hw11.jvdraw.objects;

import hr.fer.zemris.java.hw11.jvdraw.model.BoundingBox;
import hr.fer.zemris.java.hw11.jvdraw.observers.DrawingModelListener;

/**
 * Interface defining all the method needed to implement a drawing model.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface DrawingModel {
	/**
	 * Method used to acquire the number of geometrical objects that need to be drawn.
	 * 
	 * @return returns the number of geometrical objects that need to be drawn
	 */
	int getSize();
	
	/**
	 * Method used to acquire a specific geometrical object from the model.
	 * 
	 * @param index index of the geometrical object in the model's list of objects.
	 * @return returns the requested object from the model
	 */
	GeometricalObject getObject(int index);
	
	/**
	 * Method used to add a geometrical object to the model.
	 * 
	 * @param object geometrical object that will be added
	 */
	void add(GeometricalObject object);
	
	/**
	 * Method used to remove a geometrical object from the model.
	 * 
	 * @param object geometrical object that will be removed
	 */
	void remove(GeometricalObject object);
	
	/**
	 * Method used to update a geometrical object contained in the model.
	 * 
	 * @param oldObj object that needs to be modified
	 * @param newObj new object that is stored in place of the old one
	 */
	void update(GeometricalObject oldObj, GeometricalObject newObj);
	
	/**
	 * Method used to clear the model. All the geometrical objects are removed.
	 */
	void clear();
	
	/**
	 * Method used to calculate the minimal bounding box of the model.
	 * 
	 * @return returns the minimal bounding box of the model
	 */
	BoundingBox minBoundingBox();
	
	/**
	 * Method used to add a listener to the model.
	 * 
	 * @param l listener that will be added
	 */
	void addDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Method used to remove a listener from the model.
	 * 
	 * @param l listener that will be removed
	 */
	void removeDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Method used to check whether the model has been modified since the last save.
	 * 
	 * @return returns true if the model has changed since the last save, false otherwise
	 */
	boolean getModified();
	
	/**
	 * Method used to set the flag indicating whether the model has been modified since the last save.
	 * 
	 * @param value value the flag will be set to
	 */
	void setModified(boolean value);
	
	/**
	 * Method used to acquire number of geometrical objects in the model that are lines.
	 * 
	 * @return returns number of lines in the model
	 */
	int numberOfLines();
	
	/**
	 * Method used to increment the number of lines in the model by one.
	 */
	void setNumberOfLines();
	
	/**
	 * Method used to acquire number of geometrical objects in the model that are circles.
	 * 
	 * @return returns number of circles in the model
	 */
	int numberOfCircles();
	
	/**
	 * Method used to increment the number of circles in the model by one.
	 */
	void setNumberOfCircles();
	
	/**
	 * Method used to acquire number of geometrical objects in the model that are filled circles.
	 * 
	 * @return returns number of filled circles in the model
	 */
	int numberOfFilledCircles();
	
	/**
	 * Method used to increment the number of filled circles in the model by one.
	 */
	void setNumberOfFilledCircles();
}
