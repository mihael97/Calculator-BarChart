package hr.fer.zemris.java.gui.calc.Calculator.interfaces;

import java.util.function.DoubleBinaryOperator;

/**
 * Interface which contains all method that calculator must have
 * 
 * @author Mihael
 *
 */
public interface CalcModel {
	/**
	 * Method adds value listener to list
	 * 
	 * @param l
	 *            - value listener
	 */
	void addCalcValueListener(CalcValueListener l);

	/**
	 * Method removes value listener from list
	 * 
	 * @param l
	 *            - value listener
	 */
	void removeCalcValueListener(CalcValueListener l);

	/**
	 * String representation of stored value
	 * 
	 * @return stored value like {@link String}
	 */
	String toString();

	/**
	 * Method returns stored value
	 * 
	 * @return stored value
	 */
	double getValue();

	/**
	 * Method sets stored value
	 * 
	 * @param value
	 *            - future stored value
	 */
	void setValue(double value);

	/**
	 * Method sets stored value to <code>null</code>
	 */
	void clear();

	/**
	 * Method sets all values to <code>null</code>
	 */
	void clearAll();

	/**
	 * Method changes result sign
	 */
	void swapSign();

	/**
	 * Method inserts decimal point on the end of string
	 */
	void insertDecimalPoint();

	/**
	 * Method inserts digit at the end of number
	 * 
	 * @param digit
	 *            - digit we want to insert
	 */
	void insertDigit(int digit);

	/**
	 * Method check if active operand is different than <code>null</code>
	 * 
	 * @return true if it is different,otherwise false
	 */
	boolean isActiveOperandSet();

	/**
	 * Method return active operand
	 * 
	 * @return active operand
	 */
	double getActiveOperand();

	/**
	 * Method sets active operand
	 * 
	 * @param activeOperand
	 *            - next active operand
	 */
	void setActiveOperand(double activeOperand);

	/**
	 * Method sets active operand to <code>null</code>
	 */
	void clearActiveOperand();

	/**
	 * Method return pending operator
	 * 
	 * @return pending operator
	 */
	DoubleBinaryOperator getPendingBinaryOperation();

	/**
	 * Method sets pending operator
	 * 
	 * @param op
	 *            - next pending operator
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
}