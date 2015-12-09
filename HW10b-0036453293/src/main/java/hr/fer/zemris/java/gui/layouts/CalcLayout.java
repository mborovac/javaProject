package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used to define the grid layout with 5 rows and 7 columns. Element at the position
 * 1,1 spans through the columns 2, 3, 4 and 5. Elements can not be added to those positions.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class CalcLayout implements LayoutManager2 {
	
	private int space;
	private Dimension gridSize;
	private Map<Component, RCPosition> mapOfConstraints;

	private static final int[] FIRST_COMP_COLS = {2, 3, 4, 5};
	
	/**
	 * Class constructor.
	 */
	public CalcLayout() {
		this(0);
	}
	
	/**
	 * Class constructor defining the space between the components and the window.
	 * 
	 * @param space
	 */
	public CalcLayout(int space) {
		if(space < 0) {
			throw new IllegalArgumentException("Space between components can not be lower than 0.");
		}
		this.space = space;
		this.gridSize = new Dimension(7, 5);
		mapOfConstraints = new HashMap<>();
	}

	/**
	 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
	 * 
	 * Unsupported operation. Use the add method that uses constraints.
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		if(comp == null) {
			throw new IllegalArgumentException("Component can not be null.");
		}
		mapOfConstraints.remove(comp);
	}

	/**
	 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		int numOfComponents = parent.getComponentCount();
		int width = 0;
        int height = 0;
        for (int i = 0; i < numOfComponents; i++) {
            Component comp = parent.getComponent(i);
            Dimension dim = comp.getPreferredSize();
            int tempWidth;
            RCPosition restraint = mapOfConstraints.get(comp);
            if(restraint.getRow() == 1 && restraint.getColumn() == 1) {
            	tempWidth = (dim.width - FIRST_COMP_COLS.length*space)/(FIRST_COMP_COLS.length + 1);
            } else {
            	tempWidth = dim.width;
            }
             
            if (width < tempWidth) {
            	width = tempWidth;
            }
            if (height < dim.height) {
            	height = dim.height;
            }
        }
		return new Dimension(insets.left + insets.right + (this.gridSize.width - 1)*space + this.gridSize.width*width,
				 insets.top + insets.bottom + (this.gridSize.height - 1)*space + this.gridSize.height*height);
	}

	/**
	 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		int numOfComponents = parent.getComponentCount();
		int width = 0;
        int height = 0;
        for (int i = 0; i < numOfComponents; i++) {
            Component comp = parent.getComponent(i);
            Dimension dim = comp.getMinimumSize();
            int tempWidth;
            RCPosition restraint = mapOfConstraints.get(comp);
            if(restraint.getRow() == 1 && restraint.getColumn() == 1) {
            	tempWidth = (dim.width - FIRST_COMP_COLS.length*space)/(FIRST_COMP_COLS.length + 1);
            } else {
            	tempWidth = dim.width;
            }
             
            if (width < tempWidth) {
            	width = tempWidth;
            }
            if (height < dim.height) {
            	height = dim.height;
            }
        }
		return new Dimension(insets.left + insets.right + (this.gridSize.width - 1)*space + this.gridSize.width*width,
				 insets.top + insets.bottom + (this.gridSize.height - 1)*space + this.gridSize.height*height);
	}

	/**
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			int totalGapsWidth = (gridSize.width + 1) * space;
			int widthWOInsets = parent.getWidth() - (insets.left + insets.right);
			int widthPerComponent = (widthWOInsets - totalGapsWidth) / gridSize.width;
			int extraWidthAvailable = (widthWOInsets - (widthPerComponent*gridSize.width + totalGapsWidth)) / 2;
					
			int totalGapsHeight = (gridSize.height + 1) * space;
	        int heightWOInsets = parent.getHeight() - (insets.top + insets.bottom);
	        int heightPerComponent = (heightWOInsets - totalGapsHeight) / gridSize.height;
	        int extraHeightAvailable = (heightWOInsets - (heightPerComponent*gridSize.height + totalGapsHeight)) / 2;
	        
	        for(Component component: mapOfConstraints.keySet()) {
	        	RCPosition position = mapOfConstraints.get(component);
	        	if(position.getRow() == 1 && position.getColumn() == 1) {
	        		component.setBounds(insets.left + space + extraWidthAvailable + 
	        								(widthPerComponent + space)*(position.getColumn() - 1),
			    						insets.top + space + extraHeightAvailable + 
			    							(heightPerComponent + space)*(position.getRow() - 1),
			    						(FIRST_COMP_COLS.length + 1)*widthPerComponent + (FIRST_COMP_COLS.length)*space,
			    						heightPerComponent);
	            } else {
	            	component.setBounds(insets.left + space + extraWidthAvailable + 
	            							(widthPerComponent + space)*(position.getColumn() - 1),
			    						insets.top + space + extraHeightAvailable + 
			    							(heightPerComponent + space)*(position.getRow() - 1),
			    						widthPerComponent,
			    						heightPerComponent);
	            }
	        }
		}
	}

	/**
	 * @see java.awt.LayoutManager2#addLayoutComponent(java.awt.Component, java.lang.Object)
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(!(constraints instanceof RCPosition || constraints instanceof String)) {
			throw new IllegalArgumentException("CalcLayout accepts only instances of RCPosition.");
		}
		RCPosition currentConstraint;
		if(constraints instanceof String) {
			String stringConstraints = (String) constraints;
			if(stringConstraints.isEmpty()) {
				throw new IllegalArgumentException("Constraints can not be empty.");
			}
			if(!stringConstraints.matches("[0-9],[0-9]")) {
				throw new IllegalArgumentException("Ill-formed position.");
			}
			String[] coordinates = stringConstraints.split(",");
			currentConstraint = new RCPosition(Integer.parseInt(coordinates[0].trim()), 
					Integer.parseInt(coordinates[1].trim()));
		} else {
			currentConstraint = (RCPosition) constraints;
		}
		
		if(currentConstraint.getRow() == 1) {
			for(int col: FIRST_COMP_COLS) {
				if(currentConstraint.getColumn() == col) {
					String coveredCols = "";
					for(int i = 0; i < FIRST_COMP_COLS.length; i++) {
						coveredCols += " " + FIRST_COMP_COLS[i];
					}
					throw new IllegalArgumentException("Element 1,1 covers columns" 
							+ coveredCols + ". Can not add them.");
				}
			}
		}
		boolean acceptable = true;
		if(mapOfConstraints.values().contains(currentConstraint)) {
			acceptable = false;
		}
		if(acceptable) {
			mapOfConstraints.put(comp, currentConstraint);
		} else {
			throw new IllegalArgumentException("Component has already been added.");
		}
	}

	/**
	 * @see java.awt.LayoutManager2#maximumLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * @see java.awt.LayoutManager2#getLayoutAlignmentX(java.awt.Container)
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	/**
	 * @see java.awt.LayoutManager2#getLayoutAlignmentY(java.awt.Container)
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	/**
	 * @see java.awt.LayoutManager2#invalidateLayout(java.awt.Container)
	 */
	@Override
	public void invalidateLayout(Container target) {
		// do nothing
	}
}
