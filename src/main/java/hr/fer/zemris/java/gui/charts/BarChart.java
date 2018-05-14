package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;

/**
 * Class contains variables for describing some main informations about
 * {@link BarChartComponent}
 * 
 * @author Mihael
 *
 */
public class BarChart {
	/**
	 * List of {@link XYValue} elements
	 */
	private List<XYValue> list;
	/**
	 * Description for x axis
	 */
	private String xDesc;
	/**
	 * Description for y axis
	 */
	private String yDesc;
	/**
	 * minimal y
	 */
	private int yMin;
	/**
	 * y maximal
	 */
	private int yMax;

	/**
	 * Step between two values
	 */
	private int yStep;

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
	 * <code>yMin</code> and <code>yMax</code> then new y step is for one bigger
	 * than <code>yMin</code>
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

	/**
	 * Method return list of {@link XYValue}
	 * 
	 * @return list of {@link XYValue}
	 */
	public List<XYValue> getList() {
		return list;
	}

	/**
	 * Method returns name of x axis
	 * 
	 * @return x axis name
	 */
	public String getxDesc() {
		return xDesc;
	}

	/**
	 * Method returns name o y axis
	 * 
	 * @return y axis name
	 */
	public String getyDesc() {
		return yDesc;
	}

	/**
	 * Method returns <code>yMin</code>
	 * 
	 * @return minimal y
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Method return <code>yMax</code>
	 * 
	 * @return maximal y
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Method returns <code>yStep</code>,space between to values on y axis
	 * 
	 * @return difference between two values on y axis
	 */
	public int getyStep() {
		return yStep;
	}
}
