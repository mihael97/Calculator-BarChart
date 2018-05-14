package hr.fer.zemris.java.gui.charts;

/**
 * Class represents informations(columns) with values stored in
 * {@link BarChartComponent}
 * 
 * @author Mihael
 * 
 */
public class XYValue {
	/**
	 * Ordinal number of information in list
	 */
	int x;
	/**
	 * Value
	 */
	int y;

	/**
	 * Public constructor which makes new structure with ordinal number and value
	 * 
	 * @param x
	 *            - ordinal number in list
	 * @param y
	 *            - value
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Method returns ordinal number of element in list
	 * 
	 * @return ordinal number of element
	 */
	public int getX() {
		return x;
	}

	/**
	 * Method returns value information
	 * 
	 * @return value information
	 */
	public int getY() {
		return y;
	}

}
