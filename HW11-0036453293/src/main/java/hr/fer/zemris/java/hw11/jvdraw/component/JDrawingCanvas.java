package hr.fer.zemris.java.hw11.jvdraw.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import hr.fer.zemris.java.hw11.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel;
import hr.fer.zemris.java.hw11.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.objects.Line;
import hr.fer.zemris.java.hw11.jvdraw.observers.DrawingModelListener;

import javax.swing.JComponent;

/**
 * Class representing a canvas the user will draw on. 1st click of the mouse will set component's
 * starting point. Moving the mouse shows the user the end result if he clicks the mouse buttong again.
 * 2nd click fixates the component.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {
	
	private static final long serialVersionUID = 1L;
	private DrawingModel drawingModel;
	private boolean clicked;
	private Point point;
	private IColorProvider frontColorProvider;
	private IColorProvider backColorProvider;
	
	private GeometricalObject tempLine;
	private GeometricalObject tempCircle;
	private GeometricalObject tempFilledCircle;
	private boolean isLine = false;
	private boolean isCircle = false;
	private boolean isFilledCircle = false;
	
	/**
	 * Class constructor. Constructor takes 3 arguments, the model that is being used to store 
	 * components and the 2 color providers, front and background ones.
	 * 
	 * @param drawingModel the model that is being used to store components
	 * @param frontColorProvider front color provider
	 * @param backColorProvider background color provider
	 */
	public JDrawingCanvas(DrawingModel drawingModel, IColorProvider frontColorProvider, 
			IColorProvider backColorProvider) {
		if(drawingModel == null) {
			throw new IllegalArgumentException("Drawing model can not be null.");
		}
		this.drawingModel = drawingModel;
		this.drawingModel.addDrawingModelListener(this);
		clicked = false;
		this.addMouseListener(click);
		this.addMouseMotionListener(move);
		this.frontColorProvider = frontColorProvider;
		this.backColorProvider = backColorProvider;
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.observers.DrawingModelListener#objectsAdded(
	 * hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel, int, int)
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		this.paintComponent(getGraphics());
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.observers.DrawingModelListener#objectsRemoved(
	 * hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel, int, int)
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		this.paintComponent(getGraphics());
	}
	
	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.observers.DrawingModelListener#objectsChanged(
	 * hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel, int, int)
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		this.paintComponent(getGraphics());
	}
	
	MouseListener click = new MouseAdapter() {
		
		/**
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 * 
		 * Method defines the way the canvas will draw the components depending on the number
		 * of clicks. Component being drawn is defined by the selected button in the GUI.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if(!(isLine == false && isCircle == false && isFilledCircle == false)) {
				if(e.getClickCount() == 1) {
					if(!clicked) {
						point = e.getPoint();
						clicked = true;
					} else {
						Point temp = e.getPoint();
						if(isLine) {
							drawingModel.setNumberOfLines();
							String name = "Line "+drawingModel.numberOfLines();
							drawingModel.add(new Line(name, point.x, point.y, temp.x, temp.y, 
									backColorProvider.getCurrentColor()));
							tempLine = null;
						} else {
							int radius = (int) Math.sqrt(Math.pow(e.getPoint().x - point.x, 2) + 
									Math.pow(e.getPoint().y - point.y, 2));
							if(isCircle) {
								drawingModel.setNumberOfCircles();
								String name = "Circle "+drawingModel.numberOfCircles();
								drawingModel.add(new Circle(name, point.x, point.y, radius, 
										backColorProvider.getCurrentColor()));
								tempCircle = null;
							} else if(isFilledCircle) {
								drawingModel.setNumberOfFilledCircles();
								String name = "Filled circle "+drawingModel.numberOfFilledCircles();
								drawingModel.add(new FilledCircle(name, point.x, point.y, 
										radius, frontColorProvider.getCurrentColor(), 
										backColorProvider.getCurrentColor()));
								tempFilledCircle = null;
							}
						}
						clicked = false;
					}
				}
			}
		}
	};
	
	MouseMotionListener move = new MouseAdapter() {
		
		/**
		 * @see java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
		 * 
		 * Method draws the selected component from the 1st fixed point/center to the mouse
		 * cursor. It gives user the preview of a resizing component as long as the component
		 * is not fixated by the 2nd mmouse click.
		 */
		@Override
		public void mouseMoved(MouseEvent e) {
			if(clicked) {
				if(isLine) {
					tempLine = new Line("", point.x, point.y, e.getPoint().x, e.getPoint().y, 
							backColorProvider.getCurrentColor());
					tempLine.repaint();
				} else {
					int radius = (int) Math.sqrt(Math.pow(e.getPoint().x - point.x, 2) + 
							Math.pow(e.getPoint().y - point.y, 2));
					if(isCircle) {
						tempCircle = new Circle("", point.x, point.y, radius, backColorProvider.getCurrentColor());
						tempCircle.repaint();
					} else if(isFilledCircle) {
						tempFilledCircle = new FilledCircle("", point.x, point.y, 
								radius, frontColorProvider.getCurrentColor(), 
								backColorProvider.getCurrentColor());
						tempFilledCircle.repaint();
					}
				}
				e.getComponent().repaint();
			}
		}
	};
	
	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * 
	 * Method paints the canvas in white and paints all of the components stored in the model.
	 */
	@Override
	public void paintComponent(Graphics g) {
		Dimension dim = this.getSize();
		Insets insets = this.getInsets();
		g.setColor(Color.WHITE);
		g.fillRect(insets.left, insets.top, dim.width - insets.left - insets.right, 
				dim.height - insets.top - insets.bottom);
		for(int i = 0; i < drawingModel.getSize(); i++) {
			drawingModel.getObject(i).paintComponent(g);
		}
		if(tempLine != null) {
			tempLine.paintComponent(g);
		}
		if(tempCircle != null) {
			tempCircle.paintComponent(g);
		}
		if(tempFilledCircle != null) {
			tempFilledCircle.paintComponent(g);
		}
	}
	
	/**
	 * Method used to let canvas know that the Line button is selected in the GUI and that 
	 * all drawn components should be lines. 
	 */
	public void setIsLine() {
		this.isLine = true;
		this.isCircle = false;
		this.isFilledCircle = false;
	}
	
	/**
	 * Method used to let canvas know that the Circle button is selected in the GUI and that 
	 * all drawn components should be circles.
	 */
	public void setIsCircle() {
		this.isLine = false;
		this.isCircle = true;
		this.isFilledCircle = false;
	}
	
	/**
	 * Method used to let canvas know that the Filled Circle button is selected in the GUI and that 
	 * all drawn components should be filled circles.
	 */
	public void setIsFilledCircle() {
		this.isLine = false;
		this.isCircle = false;
		this.isFilledCircle = true;
	}
	
	/**
	 * Method used to acquire the reference to the background color provider.
	 * 
	 * @return returns the reference to the background color provider
	 */
	public IColorProvider getBackColorProvider() {
		return this.backColorProvider;
	}
	
	/**
	 * Method used to acquire the reference to the front color provider.
	 * 
	 * @return returns the reference to the front color provider
	 */
	public IColorProvider getFrontColorProvider() {
		return this.frontColorProvider;
	}
}
