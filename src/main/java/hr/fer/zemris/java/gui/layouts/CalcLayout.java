package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.LinkedHashMap;
import java.util.Map;

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

		this.bound = bound / 2;
		container = new LinkedHashMap<>();
	}

	/**
	 * Defined constructor that delegates layout initialization to constructor with
	 * bound space set to 0
	 */
	public CalcLayout() {
		this(0);
	}

	@Override
	public void addLayoutComponent(String position, Component component) {
		addLayoutComponent(component, position);
	}

	@Override
	public void layoutContainer(Container container) {
		double width = 0, height = 0;

		for (Map.Entry<RCPosition, Component> map : this.container.entrySet()) {
			if (map.getValue().getPreferredSize() != null) {
				Dimension preffered = map.getValue().getPreferredSize();
				height = Math.max(height, preffered.height);
				if (map.getKey().getRow() == 1 && map.getKey().getColumn() == 1)
					continue;
				width = Math.max(width, preffered.width);
			}
		}

		Dimension preffered = preferredLayoutSize(container);
		width = (width * (container.getWidth() / preffered.getWidth()));
		height = (height * (container.getHeight() / preffered.getHeight()));

		// for every component we need to set position and new size
		for (Map.Entry<RCPosition, Component> map : this.container.entrySet()) {
			if (map.getKey().getRow() == 1 && map.getKey().getColumn() == 1) { // first element,takes 4 columns
				map.getValue().setBounds(0, 0, (int) (4 * bound + 5 * width), (int) height);
			} else {
				map.getValue().setBounds((int) ((map.getKey().getColumn() - 1) * (width + bound)),
						(int) ((map.getKey().getRow() - 1) * (height + bound)), (int) width, (int) height);
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container container) {
		return extremeDimensions(container, DimensionTypes.MINIMAL);
	}

	@Override
	public Dimension preferredLayoutSize(Container container) {
		return extremeDimensions(container, DimensionTypes.PREFERRED);
	}

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

		container.put(key, component);

	}

	@Override
	public float getLayoutAlignmentX(Container arg0) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container arg0) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container arg0) {
	}

	@Override
	public Dimension maximumLayoutSize(Container container) {
		return extremeDimensions(container, DimensionTypes.MAXIMAL);
	}

	private void checkDimensions(int x, int y) {
		if (x < 1 || x > row || y < 1 || y > column || (x == 1 && y >= 2 && y <= 5)) {
			throw new CalcLayoutException("Position is not valid.(" + x + "," + y + ")");
		}
	}

	public Dimension extremeDimensions(Container container, DimensionTypes type) {
		int height = 0;
		int width = 0;
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

				if (map.getKey().getRow() == 1 && map.getKey().getColumn() == 1) {
					continue;
				}

				width = Math.max(width, dim.width);
			}
		}

		height = insets.top + insets.bottom + row * height + (row - 1) * bound;
		width = insets.left + insets.right + column * width + (column - 1) * bound;

		return new Dimension(width, height);
	}

}
