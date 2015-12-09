package hr.fer.zemris.java.hw11.jvdraw.objects;

import hr.fer.zemris.java.hw11.jvdraw.model.BoundingBox;

import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * Class representing a geometrical object.
 * 
 * @author MarkoB
 * @version 1.0
 */
public abstract class GeometricalObject extends JComponent {
	
	private static final long serialVersionUID = 1L;
	private String name;

	/**
	 * Class constructor. Constructor takes a single argument, the name of the object.
	 * 
	 * @param name the name of the object
	 */
	public GeometricalObject(String name) {
		this.name = name;
	}
	
	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public abstract void paintComponent(Graphics g);
	
	/**
	 * Method used to modify the geometrical object. Each geometrical object implements this method 
	 * their own way thus deciding which component can be modified and in what way.
	 *
	 * @param model
	 */
	public abstract void modifyObject(DrawingModel model);
	
	/**
	 * Method used to calculate the minimal bounding box of each geometrical object.
	 * 
	 * @return returns the minimal bounding box of the object
	 */
	public abstract BoundingBox getBoundingBox();
	
	/**
	 * Method used to acquire the definition of the geometrical object as used by a .jvd file.
	 * 
	 * @return returns a string containing the definition of the geometrical object as used by a .jvd file
	 */
	public abstract String toFile();
	
	/**
	 * Method called when the image is being exported. It shifts the geometrical object so it fits 
	 * the exported image.
	 * 
	 * @param left number of pixels the x coordinate needs to be move to the left
	 * @param up number of pixels the y coordinate needs to be moved upwards
	 */
	public abstract void modifyComponent(int left, int up);
	
	/**
	 * @see java.awt.Component#getName()
	 * 
	 * Method returns the name of the object.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @see java.awt.Component#toString()
	 * 
	 * Method sets the String representation of the object to be the object's name
	 */
	public String toString() {
		return this.name;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof GeometricalObject)) {
			return false;
		}
		GeometricalObject other = (GeometricalObject) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
