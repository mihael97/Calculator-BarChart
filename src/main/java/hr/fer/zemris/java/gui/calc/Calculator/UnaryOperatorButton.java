package hr.fer.zemris.java.gui.calc.Calculator;

import java.util.Objects;
import java.util.function.UnaryOperator;

import javax.swing.JButton;

/**
 * Class implements {@link JButton} which is used for unary operations
 * 
 * @author Mihael
 *
 */
public class UnaryOperatorButton extends JButton {
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
	private UnaryOperator<Double> operator;
	/**
	 * Inverse operation
	 */
	private UnaryOperator<Double> inverse;

	/**
	 * Public constructor which creates new button
	 * 
	 * @param button
	 *            - name
	 * @param operator
	 *            - operator
	 * @param inverse
	 *            - Inverse operator
	 */
	public UnaryOperatorButton(String button, UnaryOperator<Double> operator, UnaryOperator<Double> inverse) {
		name = Objects.requireNonNull(button);
		super.setText(name);
		this.operator = operator;
		this.inverse = inverse;
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
	public UnaryOperator<Double> getOperator() {
		return operator;
	}

	/**
	 * Method returns inverse operation
	 * 
	 * @return inverse operation
	 */
	public UnaryOperator<Double> getInverse() {
		return inverse;
	}

}
