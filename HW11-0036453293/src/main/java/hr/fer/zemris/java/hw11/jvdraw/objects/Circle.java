package hr.fer.zemris.java.hw11.jvdraw.objects;

import hr.fer.zemris.java.hw11.jvdraw.component.JColorArea;
import hr.fer.zemris.java.hw11.jvdraw.model.BoundingBox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class representing a simple circle drawn in a single color defined in the constructor.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Circle extends GeometricalObject {
	
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private int r;
	private Color color;
	
	/**
	 * Class constructor. Constructor takes 5 arguments, name of the object, x and y coordinates of the
	 * circle center, radius and the color.
	 * 
	 * @param name name of the object
	 * @param x x coordinate of the center
	 * @param y y coordinate of the center
	 * @param r radius
	 * @param color circle color
	 */
	public Circle(String name, int x, int y, int r, Color color) {
		super(name);
		this.x = x;
		this.y = y;
		this.r = r;
		this.color = color;
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		Color previousColor = g2.getColor();
		g2.setColor(color);
		g2.drawOval(x - r, y - r, 2*r, 2*r);
		g2.setColor(previousColor);
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject#modifyObject(
	 * hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel)
	 */
	@Override
	public void modifyObject(DrawingModel model) {
		JPanel modifyPanel = new JPanel(new GridLayout(5, 2));
		JTextField xText = new JTextField();
		JTextField yText = new JTextField();
		JTextField rText = new JTextField();
		modifyPanel.add(new JLabel("Center x coordinate"));
		modifyPanel.add(xText);
		modifyPanel.add(new JLabel("Center y coordinate"));
		modifyPanel.add(yText);
		modifyPanel.add(new JLabel("Radius"));
		modifyPanel.add(rText);
		modifyPanel.add(new JLabel("Circle color"));
		JColorArea circleColor = new JColorArea(this.color);
		modifyPanel.add(circleColor);
		modifyPanel.add(new JLabel());
		JCheckBox deleteBox = new JCheckBox("Delete");
		modifyPanel.add(deleteBox);
		int selected = JOptionPane.showConfirmDialog(null, modifyPanel, "Modify "+this.getName(),
				JOptionPane.OK_CANCEL_OPTION);
		if(selected == 2) {						// Cancel
			return;
		} else if(selected == 0) {				// Ok
			if(deleteBox.isSelected()) {
				model.remove(this);
				return;
			} else {
				int x = xText.getText().isEmpty() ? this.x : Integer.parseInt(xText.getText());
				int y = yText.getText().isEmpty() ? this.y : Integer.parseInt(yText.getText());
				int r = rText.getText().isEmpty() ? this.r : Integer.parseInt(rText.getText());
				Circle newCircle = new Circle(this.getName(), x, y, r, circleColor.getCurrentColor());
				model.update(this, newCircle);
			}
		}
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject#getBoundingBox()
	 */
	@Override
	public BoundingBox getBoundingBox() {
		BoundingBox temp = new BoundingBox(new Point(x - r, y - r), new Point(x + r, y + r));
		return temp;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject#toFile()
	 */
	@Override
	public String toFile() {
		return "CIRCLE " + x + " " + y + " " + r + " " + color.getRed() + " " + color.getGreen() +
				" " + color.getBlue();
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject#modifyComponent(int, int)
	 */
	@Override
	public void modifyComponent(int left, int up) {
		this.x -= left;
		this.y -= up;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + r;
		result = prime * result + x;
		result = prime * result + y;
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
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Circle)) {
			return false;
		}
		Circle other = (Circle) obj;
		if (color == null) {
			if (other.color != null) {
				return false;
			}
		} else if (!color.equals(other.color)) {
			return false;
		}
		if (r != other.r) {
			return false;
		}
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}
}
