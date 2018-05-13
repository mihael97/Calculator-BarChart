package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

public class Layout3 implements LayoutManager2 {

	/** Fixed number of rows. */
	public static final int ROWS = 5;
	
	/** Fixed number of columns. */
	public static final int COLUMNS = 7;
	
	/** First component in this layout, has a special treatment. */
	public static final RCPosition FIRST_COMPONENT = new RCPosition(1,1);
	
	/** horizontal gap between elements. */
	private int gap;
	
	/** Map with components. */
	private Map<Component, RCPosition> position = new HashMap<>();
	
	/**
	 * Creates a new {@link CalcLayout} with no gap between elements.
	 */
	public Layout3() {
		this(0);
	}
	
	/**
	 * Constructor that creates a new CalcLayout with preset gap between components.
	 * 
	 * @param gap gap between elements
	 */
	public Layout3(int gap) {
		if (gap < 0) {
			throw new IllegalArgumentException("Gap cannot be negative");
		}
		this.gap = gap;
	}
	
	@Override
	public void removeLayoutComponent(Component comp) {
		position.remove(comp);
	}
	
	@Override
	public void layoutContainer(Container parent) {
		int w = 0;
		int h = 0;
		for (Map.Entry<Component, RCPosition> entry : position.entrySet()) {
			Component c = entry.getKey();
			RCPosition pos = entry.getValue();
			if (c.getPreferredSize() != null) {
				h = Math.max(h, c.getPreferredSize().height);
				if(pos.equals(FIRST_COMPONENT)) continue;
				w = Math.max(w, c.getPreferredSize().width);
			}
		}
		
		double ratioX = parent.getWidth() * 1.0 / preferredLayoutSize(parent).getWidth();
		double ratioY = parent.getHeight() * 1.0/ preferredLayoutSize(parent).getHeight();

		w *= ratioX;
		h *= ratioY;

		for (Map.Entry<Component, RCPosition> entry : position.entrySet()) {
			Component c = entry.getKey();
			RCPosition pos = entry.getValue();
			if(pos.equals(FIRST_COMPONENT)) {
				// first element takes up first 5 spots
				c.setBounds(0, 0, 5*w + 4*gap, h);
			} else {
				int row = pos.getRow();
				int col = pos.getColumn();
				c.setBounds((col-1)*(w + gap), (h + gap) * (row-1), w, h);
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return calcDimension(parent, Component::getMinimumSize);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return calcDimension(parent, Component::getPreferredSize);
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return calcDimension(target, Component::getMaximumSize);
	}

	/**
	 * Calculates a dimension based on a provided {@link SizeGetter}.
	 * 
	 * @param parent parent component
	 * @param getter maximum/minimum/preferredSize getter
	 * @return dimension based on a provided getter dimension
	 */
	private Dimension calcDimension(Container parent, SizeGetter getter) {
		Insets insets = parent.getInsets();
		int h = 0;
		int w = 0;
		for (Map.Entry<Component, RCPosition> entry : position.entrySet()) {
			Component c = entry.getKey();
			RCPosition pos = entry.getValue();
			if (getter.getSize(c) != null) {
				h = Math.max(h, getter.getSize(c).height);
				if(pos.equals(FIRST_COMPONENT)) continue;
				w = Math.max(w, getter.getSize(c).width);
			}
		}
		return new Dimension(insets.left + insets.right + COLUMNS*w + (COLUMNS-1)*gap,
							 insets.top + insets.bottom + ROWS*h + (ROWS-1)*gap);
	}

	
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition pos;
		if( constraints instanceof String) {
			pos = parseRC((String)constraints);
		} else if ( constraints instanceof RCPosition ) {
			pos = (RCPosition) constraints; 
		} else {
			throw new IllegalArgumentException("Invalid constraint");
			
		}
		if (!checkPosition(pos)) {
			throw new IllegalArgumentException("Invalid position");
		}
		RCPosition existing = position.get(comp);
		if(existing != null) {
			throw new IllegalArgumentException("Component already exists");
		}
		if(position.containsValue(pos)) {
			throw new IllegalArgumentException("Component at that position already exists");
		}
		position.put(comp, pos);
	}

	/**
	 * Helper method that checks if a valid position has been provided.
	 * 
	 * @param pos Position to be checked
	 * @return <code>True</code> if a valid position has been provided <code>false</code> otherwise
	 */
	private boolean checkPosition(RCPosition pos) {
		if (pos.getColumn() > COLUMNS || pos.getColumn() < 1) return false;
		if (pos.getRow() > ROWS || pos.getRow() < 1) return false;
		if (pos.getRow() == 1 && pos.getColumn() >= 2 && pos.getColumn() <= 5) return false;
		return true;
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	/**
	 * Interface that is used to model the preferred/minimum/maximum size for this layout.
	 * 
	 * @author Ante Spajic
	 *
	 */
	private interface SizeGetter {
		
		/**
		 * Gets the size.
		 *
		 * @param comp the component that we want the size of
		 * @return the size of component
		 */
		Dimension getSize(Component comp);
	}
	
	/**
	 * Creates a new RCposition from a provided string.
	 * 
	 * @param name string with a position
	 * @return a {@link RCPosition} based on a provided string
	 */
	private RCPosition parseRC(String name) {
		RCPosition pos = null;
		try {
			String s = name.trim().replace("\\s+", "");
			if(s.isEmpty()) {
				throw new IllegalArgumentException("Invalid RCPosition");
			}
			String[] rh = s.split(",");
			if(rh.length != 2) {
				throw new IllegalArgumentException("Invalid RCPosition, expected 2 arguments, row and column");
			}
			int row = Integer.parseInt(rh[0]);
			int column = Integer.parseInt(rh[1]);
			pos = new RCPosition(row, column);
		} catch (NumberFormatException e ) {
			throw new IllegalArgumentException("Row and column must be integers");
		}
		return pos;
	}
	
	@Override
	public void invalidateLayout(Container target) {}

	@Override
	public void addLayoutComponent(String name, Component comp) {}

}
