package hr.fer.zemris.java.gui.calc.Calculator;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.Calculator.interfaces.CalcModel;
import hr.fer.zemris.java.gui.calc.Calculator.interfaces.CalcValueListener;

/**
 * Class represents calculator model
 * 
 * @author Mihael
 *
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * List contains all listeners
	 */
	private ArrayList<CalcValueListener> listeners;
	/**
	 * Current active result
	 */
	private String stored;
	/**
	 * Current active operand
	 */
	private Double activeOperand = null;
	/**
	 * Stores current operation
	 */
	private DoubleBinaryOperator pendingOperation = null;

	/**
	 * Default constructor
	 */
	public CalcModelImpl() {
		listeners = new ArrayList<>();
	}

	/**
	 * Method adds new listener
	 * 
	 * @param l
	 *            - listener we want to register
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	/**
	 * Method removes listener from listener list
	 * 
	 * @param l
	 *            - listener we want to remove
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	/**
	 * Method returns current stored value. If value doesn't exit,return 0.0
	 * 
	 * @return current stored value
	 */
	@Override
	public double getValue() {
		return stored == null ? Double.valueOf(0) : Double.parseDouble(stored);
	}

	/**
	 * Method sets current stored value
	 * 
	 * @param value
	 *            - value we want to store
	 */
	@Override
	public void setValue(double value) {
		stored = String.valueOf(value);

		callListeners();
	}

	/**
	 * Method deletes current stored value
	 */
	@Override
	public void clear() {
		stored = null;
		
		callListeners();
	}

	/**
	 * Method clears all variables
	 */
	@Override
	public void clearAll() {
		stored = null;
		activeOperand = null;
		pendingOperation = null;
		
		callListeners();
	}

	/**
	 * Method changes sign of stored value(if it exists)
	 */
	@Override
	public void swapSign() {
		if (stored != null) {
			if (stored.charAt(0) == '-') {
				stored = new String(stored.substring(1));
			} else {
				stored = new String("-" + stored);
			}

			callListeners();
		}
	}

	/**
	 * Method inserts decimal point on the end of stored value
	 */
	@Override
	public void insertDecimalPoint() {
		if (stored != null && stored.charAt(stored.length() - 1) != '.' && !stored.contains(".")) {
			stored = new String(stored + ".");

			callListeners();
		} else if (stored == null) {
			stored = "0.";

			callListeners();
		}
	}

	/**
	 * Method inserts digit on the end of stored value
	 */
	@Override
	public void insertDigit(int digit) {

		if (stored == null) {
			stored = "0";
			callListeners();
		}

		if (stored != null && Double.parseDouble(stored + digit) < Double.MAX_VALUE) {
			Double.parseDouble(stored + digit);

			if (!(digit == 0 && stored.equals("0"))) {
				if (stored.equals("0")) {
					stored = "";
				}
				stored = new String(stored + Integer.valueOf(digit));
			} else {
				stored = "0";
			}

			callListeners();
		}

	}

	/**
	 * Method checks if current operand is set
	 * 
	 * @return true if it is set,otherwise false
	 */
	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	/**
	 * Method returns current active operand
	 * 
	 * @return current active operand
	 * @throws IllegalStateException
	 *             - if stored value is <code>null</code>
	 */
	@Override
	public double getActiveOperand() {
		if (activeOperand == null) {
			throw new IllegalStateException("There is not active operand!");
		}

		return activeOperand;
	}

	/**
	 * Method sets active operand
	 * 
	 * @param activeOperand
	 *            - operand we want to store
	 */
	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = Double.valueOf(activeOperand);
		
		if(pendingOperation!=null) {
			calculateOperation();
		}
		
	}

	/**
	 * Method clears active operand
	 */
	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	/**
	 * Method returns pending operator
	 * 
	 * @return current pending operator
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	/**
	 * Method sets pending operator <br>
	 * 
	 * @param op
	 *            - operator we want to store
	 * @throws NullPointerException
	 *             - if argument is <code>null</code>
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		if (pendingOperation != null && stored != null && activeOperand != null) {
			calculateOperation();

			callListeners();
		} else if (stored != null && activeOperand == null) {
			activeOperand = Double.parseDouble(stored);
			stored = null;

			callListeners();
		}

		pendingOperation = Objects.requireNonNull(op, "Binary Operator can't be null!");
	}

	/**
	 * Method calculates new active operand. It takes operation from
	 * <code>pendingOperation </code> with first argument from
	 * <code>activeOperand</code> and second from <code>stored</code> value
	 */
	public void calculateOperation() {
		if (pendingOperation == null || stored == null || activeOperand == null) {
			throw new IllegalArgumentException("Pending operator,stored value of active operand doesn't exist");
		}

		activeOperand = pendingOperation.applyAsDouble(activeOperand, Double.parseDouble(stored));
		stored = null;
	}

	/**
	 * Method returns string representation of stored value. If stored value is
	 * <code>null</code> result will be 0
	 */
	@Override
	public String toString() {
		if (stored == null)
			return "0";
		return stored.toString();
	}

	/**
	 * Method contacts every listener that value is changed
	 */
	private void callListeners() {
		for (CalcValueListener listener : listeners) {
			listener.valueChanged(this);
		}
	}

}
