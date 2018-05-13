package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;

public class BarChart {
	/**
	 * List of {@link XYValue} elements
	 */
	List<XYValue> list;
	/**
	 * Description for x axis
	 */
	String xDesc;
	/**
	 * Description for y axis
	 */
	String yDesc;
	/**
	 * minimal y
	 */
	int yMin;
	/**
	 * y maximal
	 */
	int yMax;

	/**
	 * Step between two values
	 */
	int yStep;

	/**
	 * Public constructor which initialize new {@link BarChart}
	 * 
	 * @param list
	 *            - list of {@link XYValue}
	 * @param xDesc
	 *            - description for x axis
	 * @param yDesc
	 *            - description for y axis
	 * @param yMin
	 *            - y minimal
	 * @param yMax
	 *            - y maximal
	 * @param yStep
	 *            - step for y
	 * 
	 * @throws NullPointerException
	 *             - if list,xDesc or yDesc are <code>null</code>
	 */
	public BarChart(List<XYValue> list, String xDesc, String yDesc, int yMin, int yMax, int yStep) {
		this.list = Objects.requireNonNull(list);
		this.xDesc = Objects.requireNonNull(xDesc);
		this.yDesc = Objects.requireNonNull(yDesc);
		this.yMin = yMin;
		this.yMax = yMax;
		this.yStep = check(yMin, yStep, yMax);
	}

	/**
	 * Method calculates new y step. If given y step is not between
	 * <code>ymin</code> and <code>ymax</code> then new y step for y is for one
	 * bigger than y min
	 * 
	 * @param yMin
	 *            - y minimal
	 * @param yStep
	 *            - y step
	 * @param yMax
	 *            - y maximal
	 * @return new y step
	 */
	private int check(int yMin, int yStep, int yMax) {
		return yStep >= yMin && yStep <= yMax ? yStep : yMin + 1;
	}

}
