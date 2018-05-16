package hr.fer.zemris.java.gui.calc.Calculator;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

/**
 * Class implements {@link JButton} and presents button for binary operation
 * 
 * @author Mihael
 *
 */
public class BinaryOperatorButton extends JButton {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Button name
	 */
	private String name;
	/**
	 * Operation
	 */
	private DoubleBinaryOperator operator;

	/**
	 * Constructor which creates new button
	 * 
	 * @param button
	 *            - button name
	 * @param operator
	 *            - operator
	 */
	public BinaryOperatorButton(String button, DoubleBinaryOperator operator) {
		name = Objects.requireNonNull(button);
		super.setText(name);
		this.operator = Objects.requireNonNull(operator);
	}

	/**
	 * Method returns button name
	 * 
	 * @return button name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method returns operation
	 * 
	 * @return operation
	 */
	public DoubleBinaryOperator getOperator() {
		return operator;
	}

}
