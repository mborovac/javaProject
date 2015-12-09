package hr.fer.zemris.java.hw11.jvdraw.objects;

import hr.fer.zemris.java.hw11.jvdraw.component.JColorArea;
import hr.fer.zemris.java.hw11.jvdraw.model.BoundingBox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.geom.Line2D;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class representing a simple straight line drawn in a single color defined in the constructor.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Line extends GeometricalObject {
	
	private static final long serialVersionUID = 1L;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private Color color;

	/**
	 * Class constructor. Constructor takes 6 arguments, name of the object, x and y coordinates of the
	 * starting and ending points and and the line color.
	 *
	 * @param name name of the object
	 * @param x1 x coordinate of the starting point
	 * @param y1 y coordinate of the starting point
	 * @param x2 x coordinate of the ending point
	 * @param y2 y coordinate of the ending point
	 * @param color line color
	 */
	public Line(String name, int x1, int y1, int x2, int y2, Color color) {
		super(name);
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		if(color == null) {
			throw new IllegalArgumentException("Color can not be null.");
		}
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
		Line2D line = new Line2D.Float(x1, y1, x2, y2);
		g2.draw(line);
		g2.setColor(previousColor);
	}

	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject#modifyObject(
	 * hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel)
	 */
	@Override
	public void modifyObject(DrawingModel model) {
		JPanel modifyPanel = new JPanel(new GridLayout(6, 2));
		JTextField x1Text = new JTextField();
		JTextField y1Text = new JTextField();
		JTextField x2Text = new JTextField();
		JTextField y2Text = new JTextField();
		modifyPanel.add(new JLabel("x1 coordinate"));
		modifyPanel.add(x1Text);
		modifyPanel.add(new JLabel("y1 coordinate"));
		modifyPanel.add(y1Text);
		modifyPanel.add(new JLabel("x2 coordinate"));
		modifyPanel.add(x2Text);
		modifyPanel.add(new JLabel("y2 coordinate"));
		modifyPanel.add(y2Text);
		modifyPanel.add(new JLabel("Line color"));
		JColorArea lineColor = new JColorArea(this.color);
		modifyPanel.add(lineColor);
		modifyPanel.add(new JLabel());
		JCheckBox deleteBox = new JCheckBox("Delete");
		modifyPanel.add(deleteBox);
		int selected = JOptionPane.showConfirmDialog(null, modifyPanel, "Modify "+this.getName(),
				JOptionPane.OK_CANCEL_OPTION);
		if(selected == 2) {							// Cancel
			return;
		} else if(selected == 0) {					// Ok
			if(deleteBox.isSelected()) {
				model.remove(this);
				return;
			} else {
				int x1 = x1Text.getText().isEmpty() ? this.x1 : Integer.parseInt(x1Text.getText());
				int y1 = y1Text.getText().isEmpty() ? this.y1 : Integer.parseInt(y1Text.getText());
				int x2 = x2Text.getText().isEmpty() ? this.x2 : Integer.parseInt(x2Text.getText());
				int y2 = y2Text.getText().isEmpty() ? this.y2 : Integer.parseInt(y2Text.getText());
				Line newLine = new Line(this.getName(), x1, y1, x2, y2, lineColor.getCurrentColor());
				model.update(this, newLine);
			}
		}
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject#getBoundingBox()
	 */
	public BoundingBox getBoundingBox() {
		int x1;
		int y1;
		int x2;
		int y2;
		if(this.x1 < this.x2) {
			x1 = this.x1;
			x2 = this.x2;
		} else {
			x1 = this.x2;
			x2 = this.x1;
		}
		if(this.y1 < this.y2) {
			y1 = this.y1;
			y2 = this.y2;
		} else {
			y1 = this.y2;
			y2 = this.y1;
		}
		return new BoundingBox(new Point(x1, y1), new Point(x2, y2));
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject#toFile()
	 */
	@Override
	public String toFile() {
		return "LINE " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRed() + " " + color.getGreen()
				+ " " + color.getBlue();
	}
	
	@Override
	public void modifyComponent(int left, int up) {
		this.x1 -= left;
		this.x2 -= left;
		this.y1 -= up;
		this.y2 -= up;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + x1;
		result = prime * result + x2;
		result = prime * result + y1;
		result = prime * result + y2;
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
		if (!(obj instanceof Line)) {
			return false;
		}
		Line other = (Line) obj;
		if (color == null) {
			if (other.color != null) {
				return false;
			}
		} else if (!color.equals(other.color)) {
			return false;
		}
		if (x1 != other.x1) {
			return false;
		}
		if (x2 != other.x2) {
			return false;
		}
		if (y1 != other.y1) {
			return false;
		}
		if (y2 != other.y2) {
			return false;
		}
		return true;
	}
}
