package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;

import javafx.css.Size;

public class CalcLayout implements LayoutManager2 {

	/**
	 * Maximal number of components
	 */
	private static final int MAX_ITEM = 31;
	
	/**
	 * Current number of stored elements
	 */
	private int stored = 0;

	/**
	 * Maximal numbers of rows
	 */
	private final static int row = 5;
	/**
	 * Maximal number of column
	 */
	private final static int column = 7;

	@Override
	public void addLayoutComponent(String arg0, Component arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void layoutContainer(Container arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeLayoutComponent(Component arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addLayoutComponent(Component arg0, Object arg1) {
		
	}

	@Override
	public float getLayoutAlignmentX(Container arg0) {
		return stored % 7;
	}

	@Override
	public float getLayoutAlignmentY(Container arg0) {
		return stored / 7;
	}

	@Override
	public void invalidateLayout(Container arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Method returns maximal possible dimension of layout
	 * 
	 * @return {@link Dimension} - maximal possible dimension
	 */
	@Override
	public Dimension maximumLayoutSize(Container arg0) {
		return null;
	}

}
