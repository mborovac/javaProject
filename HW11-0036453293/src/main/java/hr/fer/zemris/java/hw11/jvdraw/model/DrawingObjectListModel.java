package hr.fer.zemris.java.hw11.jvdraw.model;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel;
import hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.observers.DrawingModelListener;

import javax.swing.ListModel;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class used to register, unregister and inform the listeners on a component content panel. 
 * It is also used as the informer for the component content panel when something has changed.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class DrawingObjectListModel implements ListModel<GeometricalObject>, DrawingModelListener {
	
	private DrawingModel model;
	private List<ListDataListener> listeners = new ArrayList<>();
	
	/**
	 * Class constructor. Constructor takes a single argument, the model containing all the components
	 * currently visible to the user.
	 * 
	 * @param model the model containing all the components currently visible to the user
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}
	
	/**
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return this.model.getSize();
	}
	
	/**
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public GeometricalObject getElementAt(int index) {
		return this.model.getObject(index);
	}

	/**
	 * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		if(!listeners.contains(l)) {
			listeners = new ArrayList<>(listeners);
			listeners.add(l);
		}
	}

	/**
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}

	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.observers.DrawingModelListener#objectsAdded(
	 * hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel, int, int)
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		for(ListDataListener listener: listeners) {
			listener.intervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1));
		}
	}

	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.observers.DrawingModelListener#objectsRemoved(
	 * hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel, int, int)
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		for(ListDataListener listener: listeners) {
			listener.intervalRemoved(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index0, index1));
		}
	}

	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.observers.DrawingModelListener#objectsChanged(
	 * hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel, int, int)
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		for(ListDataListener listener: listeners) {
			listener.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index0, index1));
		}
	}
}
