package hr.fer.zemris.java.hw11.jvdraw.observers;

import hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel;

/**
 * Interface defining the methods needed to implement a DrawingModelListener.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface DrawingModelListener {
	
	/**
	 * Method called by the subject on all the registered listeners when new objects are added
	 * to it.
	 * 
	 * @param source the subject
	 * @param index0 starting index of the added objects
	 * @param index1 ending index of the added objects
	 */
	void objectsAdded(DrawingModel source, int index0, int index1);
	
	/**
	 * Method called by the subject on all the registered listeners when some objects are removed
	 * from it.
	 * 
	 * @param source the subject
	 * @param index0 starting index of the removed objects
	 * @param index1 ending index of the removed objects
	 */
	void objectsRemoved(DrawingModel source, int index0, int index1);
	
	/**
	 * Method called by the subject on all the registered listeners when some objects have changed.
	 * 
	 * @param source the subject
	 * @param index0 starting index of the changed objects
	 * @param index1 ending index of the changed objects
	 */
	void objectsChanged(DrawingModel source, int index0, int index1);
}
