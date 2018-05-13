package hr.fer.zemris.java.gui.layouts;

/**
 * Class presents structure with variables for indicates row number and column
 * number in layout
 * 
 * @author Mihael
 *
 */
public class RCPosition {
	/**
	 * Number of row
	 */
	private int row;
	/**
	 * Number of column
	 */
	private int column;

	/**
	 * Constructor accepts two arguments where first argument is row position and
	 * second is column position
	 * 
	 * @param row
	 *            -row number
	 * @param column
	 *            - column number
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Method returns number of row
	 * 
	 * @return row number
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Method return number of column
	 * 
	 * @return number of column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * {@link RCPosition} hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + row;
		result = prime * result + column;
		return result;
	}

	/**
	 * {@link RCPosition} equals method. Two {@link RCPosition} are equal is row
	 * number and column number are same in two items
	 * 
	 * @return true if they are equal,otherwise false
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj instanceof RCPosition) {
			RCPosition pom = (RCPosition) obj;

			if (pom.getRow() == row && pom.getColumn() == column) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Method returns String representation of {@link RCPosition} in format
	 * (<code>row</code>,<code>column</code>)
	 * 
	 * @return String representation of {@link RCPosition}
	 */
	@Override
	public String toString() {
		return "(" + row + "," + column + ")";
	}

}
