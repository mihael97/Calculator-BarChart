package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class implements {@link LayoutManager2} and present layout for calculator
 * 
 * @author Mihael
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Maximal number of components
	 */
	private static final int MAX_ITEM = 31;

	/**
	 * Maximal numbers of rows
	 */
	private final static int row = 5;
	/**
	 * Maximal number of column
	 */
	private final static int column = 7;

	/**
	 * Map contains all stored items. Key is position in field and value is stored
	 * component
	 */
	private LinkedHashMap<RCPosition, Component> container;

	/**
	 * Space between two elements
	 */
	private int bound;

	/**
	 * Constructor that initialize layout size to matrix 5x7
	 * 
	 * @param bound
	 *            - space between items
	 * @throws CalcLayoutException
	 *             - is bound argument is smaller than zero
	 */
	public CalcLayout(int bound) {
		if (bound < 0) {
			throw new CalcLayoutException("Space between items must be greather than zero. Given argument is " + bound);
		}

		this.bound = bound;
		container = new LinkedHashMap<>();
	}

	/**
	 * Defined constructor that delegates layout initialization to constructor with
	 * bound space set to 0
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Method delegates new layout component adding to method with argument
	 * (Component,Object(RCPosition))
	 */
	@Override
	public void addLayoutComponent(String position, Component component) {
		addLayoutComponent(component, position);
	}

	/**
	 * When frame resize itself, method sets new bounds and component sizes depends
	 * on new {@link Container} size
	 * 
	 * @param container
	 *            - {@link Container} where we store our components
	 */
	@Override
	public void layoutContainer(Container container) {
		Dimension size = calculateSize(container);

		// size has dimensions of all cells,we need dimension of one cell
		size.height /= row;
		size.width /= column;

		// we go trough all components and we set them to new location and new size
		for (Map.Entry<RCPosition, Component> component : this.container.entrySet()) {
			// location
			component.getValue().setLocation(
					container.getInsets().left + (component.getKey().getColumn() - 1) * (size.width + bound),
					container.getInsets().top + (component.getKey().getRow() - 1) * (bound + size.height));

			// we set size depends on location,if component is on position (1,1) it would
			// take 5 width cells

			component.getValue()
					.setSize((component.getKey().getRow() == 1 && component.getKey().getColumn() == 1)
							? (5 * size.width + 4 * bound)
							: size.width, size.height);
		}

	}

	/**
	 * Method calculates size of all components together,without spaces and insets.
	 * 
	 * @param container
	 *            - container
	 * @return size of all components
	 */
	private Dimension calculateSize(Container container) {

		Dimension size = container.getSize(); // size of screen
		// we need to subtract all insets and spaces between cells
		size.height -= (container.getInsets().bottom + container.getInsets().top + (row - 1) * bound);
		size.width -= (container.getInsets().left + container.getInsets().right + (column - 1) * bound);

		return size;
	}

	/**
	 * Method returns minimal layout size
	 * 
	 * @param container
	 *            - place where we store our components
	 * @return {@link Dimension} of minimal layout
	 */
	@Override
	public Dimension minimumLayoutSize(Container container) {
		return extremeDimensions(container, DimensionTypes.MINIMAL);
	}

	/**
	 * Method returns preferred layout size
	 * 
	 * @param container
	 *            - place where we store our components
	 * @return {@link Dimension} of preferred layout
	 */
	@Override
	public Dimension preferredLayoutSize(Container container) {
		Dimension poc = extremeDimensions(container, DimensionTypes.PREFERRED);
		return poc;
	}

	/**
	 * Method removes component from container
	 * 
	 * @param component
	 *            - component we want to remove
	 */
	@Override
	public void removeLayoutComponent(Component component) {
		RCPosition forDelete = null;

		for (Map.Entry<RCPosition, Component> pomMap : container.entrySet()) {
			if (pomMap.getValue() == component) {
				forDelete = pomMap.getKey();
				break;
			}
		}

		container.remove(forDelete);
	}

	/**
	 * Mehtod adds {@link Component} to {@link Container} on specific position given
	 * by {@link RCPosition}
	 * 
	 * @param component
	 *            - component we want to store
	 * 
	 * @param object
	 *            - {@link RCPosition},position
	 */
	@Override
	public void addLayoutComponent(Component component, Object object) {

		RCPosition key;

		if (object instanceof RCPosition) {
			checkDimensions(((RCPosition) object).getRow(), ((RCPosition) object).getColumn());
			key = (RCPosition) object;
		} else if (object instanceof String) {
			String[] array = ((String) object).split(",");

			if (array.length != 2) {
				throw new CalcLayoutException(
						"Wrong format of position. It has " + array.length + " elements but must have 2!");
			}

			try {
				key = new RCPosition(Integer.parseInt(array[0]), Integer.parseInt(array[1]));
			} catch (NumberFormatException e) {
				throw new CalcLayoutException("Position cannot be parsed to Integer!");
			}

			checkDimensions(key.getRow(), key.getColumn());
		} else {
			throw new CalcLayoutException("Type of position must be RCPosition or String!");
		}

		if (container.size() >= MAX_ITEM) {
			throw new CalcLayoutException("You can only store 31 element!");
		} else if (container.containsKey(key)) {
			throw new CalcLayoutException("Value with key " + key.toString() + " already exists!");
		}

		if (key.getRow() == 1 && key.getColumn() == 1) {
			component.setBackground(Color.YELLOW);
		} else {
			component.setBackground(Color.CYAN);
		}

		container.put(key, component);

	}

	/**
	 * Unimplemented
	 */
	@Override
	public float getLayoutAlignmentX(Container arg0) {
		return 0;
	}

	/**
	 * Unimplemented
	 */
	@Override
	public float getLayoutAlignmentY(Container arg0) {
		return 0;
	}

	/**
	 * Unimplemented
	 */
	@Override
	public void invalidateLayout(Container arg0) {
	}

	/**
	 * Method returns maximal layout size
	 * 
	 * @param container
	 *            - place where we store our components
	 * @return {@link Dimension} of maximal layout
	 */
	@Override
	public Dimension maximumLayoutSize(Container container) {
		return extremeDimensions(container, DimensionTypes.MAXIMAL);
	}

	/**
	 * Method checks if position is valid. <br>
	 * Row position must be between 1 and 5,and column position between 1 and 7. If
	 * row number is 1,column number cannot be between 2 and 6
	 * 
	 * @param x
	 *            - row
	 * @param y
	 *            - column
	 * 
	 * @throws CalcLayoutException
	 *             - if position is not valid
	 */
	private void checkDimensions(int x, int y) {
		if (x < 1 || x > row || y < 1 || y > column || (x == 1 && y >= 2 && y <= 5)) {
			throw new CalcLayoutException("Position is not valid.(" + x + "," + y + ")");
		}
	}

	/**
	 * Method calculates minimal,maximal of preferred size of components
	 * 
	 * @param container
	 *            - container
	 * @param type
	 *            - type of result we want
	 * @return {@link Dimension}
	 */
	public Dimension extremeDimensions(Container container, DimensionTypes type) {
		int height = 0;
		int width = 0;
		int maxFirst = 0;
		Insets insets = container.getInsets();

		for (Map.Entry<RCPosition, Component> map : this.container.entrySet()) {
			if (type == DimensionTypes.MINIMAL && map.getValue().getMinimumSize() != null
					|| type == DimensionTypes.MAXIMAL && map.getValue().getMaximumSize() != null
					|| type == DimensionTypes.PREFERRED && map.getValue().getPreferredSize() != null) {
				Dimension dim;

				if (type == DimensionTypes.MINIMAL)
					dim = map.getValue().getMinimumSize();
				else if (type == DimensionTypes.MAXIMAL)
					dim = map.getValue().getMaximumSize();
				else if (type == DimensionTypes.PREFERRED) {
					dim = map.getValue().getPreferredSize();
				} else {
					throw new IllegalArgumentException("Unsuporrted dimension type!");
				}

				height = Math.max(height, dim.height);

				boolean flag = map.getKey().getRow() == 1 && map.getKey().getColumn() == 1;

				if (flag && type == DimensionTypes.PREFERRED) {
					maxFirst = dim.width;

					continue;
				} else if (flag) {
					continue;
				}

				width = Math.max(width, dim.width);
			}
		}

		height = insets.top + insets.bottom + row * height + (row - 1) * bound;
		width = insets.left + insets.right + (maxFirst != 0 ? (maxFirst + 2 * width) : column * width)
				+ (column - 1) * bound;
		// width = insets.left + insets.right + column * width + (column - 1) * bound;

		return new Dimension(width, height);
	}

}
