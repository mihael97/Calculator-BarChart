package hr.fer.zemris.java.gui.calc.Calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Public class that presents calculator with basic functionality. It supports
 * operations for:<br>
 * addition,subtraction,divide,multiplication,and trigonometric function
 * cosine,sine,tangent and cotangent with inverse functions
 * 
 * @author Mihael
 *
 */
public class Calculator extends JFrame {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * JLabel that shows current result
	 */
	private static JLabel result;
	/**
	 * Reference to object with implemented operations
	 */
	private static CalcModelImpl functions;
	/**
	 * Stack where we put result we want to save(memory)
	 */
	Stack<String> context;

	private static JCheckBox inv;

	/**
	 * Defined constructor
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(500, 500);
		setTitle("Calculator");
		context = new Stack<>();
		initGUI();
	}

	/**
	 * Method creates frame with all components
	 */
	private void initGUI() {
		try {
			JPanel panel = new JPanel(new CalcLayout(3));

			firstRow(panel);
			secondRow(panel);
			thirdRow(panel);
			forthRow(panel);
			fifthRow(panel);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Method that makes fifth row in frame
	 * 
	 * @param panel
	 *            - panel where we save our components
	 */
	private void fifthRow(JPanel panel) {
		JButton x = new JButton("x^n");
		panel.add(x, new RCPosition(5, 1));

		JButton ctg = new JButton("ctg");
		panel.add(ctg, new RCPosition(5, 2));

		JButton bt0 = new JButton("0");
		bt0.addActionListener(new NumberWork());
		panel.add(bt0, new RCPosition(5, 3));

		JButton plusMinus = new JButton("+/-");
		panel.add(plusMinus, new RCPosition(5, 4));

		JButton dot = new JButton(".");
		dot.addActionListener(e -> {
			functions.insertDecimalPoint();
			result.setText(result.getText() + ".");
		});
		panel.add(dot, new RCPosition(5, 5));

		JButton plus = new JButton("+");
		panel.add(plus, new RCPosition(5, 6));

		inv = new JCheckBox("Inv");
		inv.addActionListener(e -> {

		});
		panel.add(inv, new RCPosition(5, 7));
	}

	/**
	 * Method that makes forth row in frame
	 * 
	 * @param panel
	 *            - panel where we save our components
	 */
	private void forthRow(JPanel panel) {
		JButton ln = new JButton("ln");
		panel.add(ln, new RCPosition(4, 1));

		JButton tan = new JButton("tan");
		panel.add(tan, new RCPosition(4, 2));

		JButton bt1 = new JButton("1");
		bt1.addActionListener(new NumberWork());
		panel.add(bt1, new RCPosition(4, 3));

		JButton bt2 = new JButton("2");
		bt2.addActionListener(new NumberWork());
		panel.add(bt2, new RCPosition(4, 4));

		JButton bt3 = new JButton("3");
		bt3.addActionListener(new NumberWork());
		panel.add(bt3, new RCPosition(4, 5));

		JButton sub = new JButton("-");
		panel.add(sub, new RCPosition(4, 6));

		JButton pop = new JButton("pop");
		pop.addActionListener(e -> {
			if (context.size() == 0) {
				throw new IllegalArgumentException("Pop cannot be done because stack is empty!");
			} else {
				result.setText(context.pop());
				functions.setValue(Double.parseDouble(result.getText()));
			}
		});
		panel.add(pop, new RCPosition(4, 7));
	}

	/**
	 * Method that makes third row in frame
	 * 
	 * @param panel
	 *            - panel where we save our components
	 */
	private void thirdRow(JPanel panel) {
		JButton log = new JButton("1/x");
		panel.add(log, new RCPosition(3, 1));

		JButton sin = new JButton("sin");
		panel.add(sin, new RCPosition(3, 2));

		JButton bt4 = new JButton("4");
		bt4.addActionListener(new NumberWork());
		panel.add(bt4, new RCPosition(3, 3));

		JButton bt5 = new JButton("5");
		bt5.addActionListener(new NumberWork());
		panel.add(bt5, new RCPosition(3, 4));

		JButton bt6 = new JButton("6");
		bt6.addActionListener(new NumberWork());
		panel.add(bt6, new RCPosition(3, 5));

		JButton multiply = new JButton("*");
		panel.add(multiply, new RCPosition(3, 6));

		JButton push = new JButton("push");
		push.addActionListener(e -> {
			context.push(result.getText());
		});
		panel.add(push, new RCPosition(3, 7));
	}

	/**
	 * Method that makes second row in frame
	 * 
	 * @param panel
	 *            - panel where we save our components
	 */
	private void secondRow(JPanel panel) {
		JButton inverse = new JButton("1/x");
		panel.add(inverse, new RCPosition(2, 1));

		JButton sin = new JButton("sin");
		panel.add(sin, new RCPosition(2, 2));

		JButton bt7 = new JButton("7");
		bt7.addActionListener(new NumberWork());
		panel.add(bt7, new RCPosition(2, 3));

		JButton bt8 = new JButton("8");
		bt8.addActionListener(new NumberWork());
		panel.add(bt8, new RCPosition(2, 4));

		JButton bt9 = new JButton("9");
		bt9.addActionListener(new NumberWork());
		panel.add(bt9, new RCPosition(2, 5));

		JButton divide = new JButton("/");
		panel.add(divide, new RCPosition(2, 6));

		JButton res = new JButton("res");
		res.addActionListener(e -> {
			functions.clearAll();
		});
		panel.add(res, new RCPosition(2, 7));
	}

	/**
	 * Method that makes first row in frame
	 * 
	 * @param panel
	 *            - panel where we save our components
	 */
	private void firstRow(JPanel panel) {
		result = new JLabel("");
		result.setEnabled(false);
		panel.add(result, new RCPosition(1, 1));

		JButton equal = new JButton("=");
		equal.addActionListener(e -> {

		});

		JButton clr = new JButton("clr");
		clr.addActionListener(e -> {
			functions.clear();
		});
		panel.add(clr, new RCPosition(1, 7));
	}

	/**
	 * Main program where we start our GUI
	 * 
	 * @param args
	 *            - not in use
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}

	/**
	 * Private class that presents action when we press number button
	 * 
	 * @author Mihael
	 *
	 */
	private static class NumberWork implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			int digit = Integer.parseInt(((JButton) event.getSource()).getName());
			functions.insertDigit(digit);
			result.setText(result.getText() + digit);
		}
	}

	private static class BinaryOperationWork implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String operator = ((JButton) event.getSource()).getName();

			switch (operator) {
			case "/":
				functions.setPendingBinaryOperation(new DoubleBinaryOperator() {

					@Override
					public double applyAsDouble(double arg0, double arg1) {
						return (double) (arg0 / arg1);
					}
				});
				break;
			case "*":
				functions.setPendingBinaryOperation(new DoubleBinaryOperator() {

					@Override
					public double applyAsDouble(double arg0, double arg1) {
						return (double) (arg0 * arg1);
					}
				});
				break;
			case "-":
				functions.setPendingBinaryOperation(new DoubleBinaryOperator() {

					@Override
					public double applyAsDouble(double arg0, double arg1) {
						return arg0 - arg1;
					}
				});
				break;
			case "+":
				functions.setPendingBinaryOperation(new DoubleBinaryOperator() {

					@Override
					public double applyAsDouble(double arg0, double arg1) {
						return arg0 + arg1;
					}
				});
				break;
			default:
				throw new IllegalArgumentException("Operation \'" + operator + "\' is unsupported!");
			}

			result.setText(String.valueOf(functions.getValue()));
		}

	}

	private static class UnaryOperationWork implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String operation = ((JButton) arg0.getSource()).getName();
			double value = Double.parseDouble(result.getText());

			switch (operation) {
			case "1/x":
				if (value == 0) {
					throw new IllegalArgumentException("Number cannot be divided with zero!");
				}

				result.setText(String.valueOf(1 / value));
				break;
			case "sin":
				if (inv.isSelected()) {
					result.setText(String.valueOf(Math.asin(value)));
				} else {
					result.setText(String.valueOf(Math.sin(value)));
				}
				break;
			case "cos":
				if (inv.isSelected()) {
					result.setText(String.valueOf(Math.acos(value)));
				} else {
					result.setText(String.valueOf(Math.cos(value)));
				}
				break;
			case "tan":
				if (inv.isSelected()) {
					result.setText(String.valueOf(Math.atan(value)));
				} else {
					result.setText(String.valueOf(Math.tan(value)));
				}
				break;
			case "ctg":
				if (inv.isSelected()) {
					result.setText(String.valueOf(1.0 / (1.0 / Math.tan(value))));
				} else {
					result.setText(String.valueOf(1.0 / Math.tan(value)));
				}
				break;
			case "log":
				if (inv.isSelected()) {
					result.setText(String.valueOf(Math.pow(10, value)));
				} else {
					result.setText(String.valueOf(Math.log10(value)));
				}
				break;
			case "ln":
				if (inv.isSelected()) {
					result.setText(String.valueOf(Math.pow(Math.E, value)));
				} else {
					result.setText(String.valueOf(Math.log(value)));
				}
				break;
			case "x^n":
				if (inv.isSelected()) {
					result.setText(String.valueOf(Math.pow(Math.E, value)));
				} else {
					result.setText(String.valueOf(Math.log(value)));
				}
				break;
			case "=":
				try {
					functions.calculateOperation();
					result.setText(String.valueOf(functions.getActiveOperand()));
				} catch (Exception e) {
					result.setText("0");
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported operation!");
			}

			functions.setValue(Double.parseDouble(result.getText()));

		}

	}
}