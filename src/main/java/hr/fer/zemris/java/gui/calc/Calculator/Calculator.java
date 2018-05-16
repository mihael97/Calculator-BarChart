package hr.fer.zemris.java.gui.calc.Calculator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.Calculator.interfaces.CalcModel;
import hr.fer.zemris.java.gui.calc.Calculator.interfaces.CalcValueListener;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Public class that presents calculator with basic functionality. It supports
 * operations for:<br>
 * addition,subtraction,division,multiplication,and trigonometric function
 * cosine,sine,tangent and cotangent with inverse functions
 * 
 * @author Mihael
 *
 */
public class Calculator extends JFrame implements CalcValueListener {

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

	/**
	 * CheckBox which is on when we want to perform inverse operation
	 */
	private static JCheckBox inv;

	/**
	 * Boolean flag which is use for symbolizing if we need to clean our result
	 * screen after binary operation result
	 */
	private static boolean flag = false;

	/**
	 * Defined constructor
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(250, 250);
		setSize(500, 500);
		setTitle("Calculator");
		context = new Stack<>();
		functions = new CalcModelImpl();
		functions.addCalcValueListener(this);
		initGUI();
	}

	/**
	 * Method creates frame with all components
	 */
	private void initGUI() {
		JPanel panel = new JPanel(new CalcLayout(3));

		firstRow(panel);
		secondRow(panel);
		thirdRow(panel);
		forthRow(panel);
		fifthRow(panel);

		add(panel);
	}

	/**
	 * Method that makes fifth row in frame
	 * 
	 * @param panel
	 *            - panel where we save our components
	 */
	private void fifthRow(JPanel panel) {
		try {
			BinaryOperatorButton x = new BinaryOperatorButton("x^n", (a, b) -> (Math.pow(a, b)));
			x.addActionListener(new BinaryOperationWork());
			panel.add(x, new RCPosition(5, 1));

			UnaryOperatorButton ctg = new UnaryOperatorButton("ctg", (a) -> (1 / Math.tan(a)),
					(a) -> (1.0 / 1.0 / Math.tan(a)));
			ctg.addActionListener(new UnaryOperationWork());
			panel.add(ctg, new RCPosition(5, 2));

			JButton bt0 = new JButton("0");
			bt0.addActionListener(new NumberWork());
			panel.add(bt0, new RCPosition(5, 3));

			JButton plusMinus = new JButton("+/-");
			plusMinus.addActionListener(new UnaryOperationWork());
			panel.add(plusMinus, new RCPosition(5, 4));

			JButton dot = new JButton(".");
			dot.addActionListener(e -> {
				functions.insertDecimalPoint();
				result.setText(result.getText() + ".");
			});
			panel.add(dot, new RCPosition(5, 5));

			BinaryOperatorButton plus = new BinaryOperatorButton("+", (a, b) -> (a + b));
			plus.addActionListener(new BinaryOperationWork());
			panel.add(plus, new RCPosition(5, 6));

			inv = new JCheckBox("Inv");
			panel.add(inv, new RCPosition(5, 7));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);

		}
	}

	/**
	 * Method that makes forth row in frame
	 * 
	 * @param panel
	 *            - panel where we save our components
	 */
	private void forthRow(JPanel panel) {
		UnaryOperatorButton ln = new UnaryOperatorButton("ln", (a) -> (Math.log(a)), (a) -> (Math.pow(Math.E, a)));
		ln.addActionListener(new UnaryOperationWork());
		panel.add(ln, new RCPosition(4, 1));

		UnaryOperatorButton tan = new UnaryOperatorButton("tan", (a) -> (Math.tan(a)), (a) -> (Math.atan(a)));
		tan.addActionListener(new UnaryOperationWork());
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

		BinaryOperatorButton sub = new BinaryOperatorButton("-", (a, b) -> (a - b));
		sub.addActionListener(new BinaryOperationWork());
		panel.add(sub, new RCPosition(4, 6));

		JButton pop = new JButton("pop");
		pop.addActionListener(e -> {
			if (context.size() == 0) {
				errorException("Pop cannot be done because stack is empty!");
			} else {
				functions.setValue(Double.parseDouble(context.pop()));
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
		UnaryOperatorButton log = new UnaryOperatorButton("log", (a) -> (Math.log10(a)), (a) -> (Math.pow(10, a)));
		log.addActionListener(new UnaryOperationWork());
		panel.add(log, new RCPosition(3, 1));

		UnaryOperatorButton cos = new UnaryOperatorButton("cos", (a) -> (Math.cos(a)), (a) -> (Math.acos(a)));
		cos.addActionListener(new UnaryOperationWork());
		panel.add(cos, new RCPosition(3, 2));

		JButton bt4 = new JButton("4");
		bt4.addActionListener(new NumberWork());
		panel.add(bt4, new RCPosition(3, 3));

		JButton bt5 = new JButton("5");
		bt5.addActionListener(new NumberWork());
		panel.add(bt5, new RCPosition(3, 4));

		JButton bt6 = new JButton("6");
		bt6.addActionListener(new NumberWork());
		panel.add(bt6, new RCPosition(3, 5));

		BinaryOperatorButton multiply = new BinaryOperatorButton("*", (a, b) -> (a * b));
		multiply.addActionListener(new BinaryOperationWork());
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
		UnaryOperatorButton inverse = new UnaryOperatorButton("1/x", null, null);
		inverse.addActionListener(new UnaryOperationWork());
		panel.add(inverse, new RCPosition(2, 1));

		UnaryOperatorButton sin = new UnaryOperatorButton("sin", (a) -> (Math.sin(a)), (a) -> (Math.asin(a)));
		sin.addActionListener(new UnaryOperationWork());
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

		BinaryOperatorButton divide = new BinaryOperatorButton("/", (a, b) -> (a / b));
		divide.addActionListener(new BinaryOperationWork());
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
		result.setText(functions.toString());
		result.setEnabled(false);
		result.setBackground(Color.BLUE);
		panel.add(result, new RCPosition(1, 1));

		UnaryOperatorButton equal = new UnaryOperatorButton("=", null, null);
		equal.addActionListener(new UnaryOperationWork());
		panel.add(equal, new RCPosition(1, 6));

		JButton clr = new JButton("clr");
		clr.addActionListener(e -> {
			functions.clear();
			result.setText(String.valueOf(functions.getValue()));
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

		/**
		 * Action where we append result screen with new digit
		 * 
		 * @param event
		 *            - action event
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			int digit = Integer.parseInt(((JButton) event.getSource()).getText());
			if (flag) {
				result.setText("");
				flag = false;
			}
			functions.insertDigit(digit);
			// result.setText(result.getText() + digit);
		}
	}

	/**
	 * Class present operations which require two arguments
	 * 
	 * @author Mihael
	 *
	 */
	private static class BinaryOperationWork implements ActionListener {

		/**
		 * Action which calculates new result
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			try {

				functions.setPendingBinaryOperation((((BinaryOperatorButton) event.getSource()).getOperator()));

				flag = true;
				// result.setText(String.valueOf(functions.getActiveOperand()));
			} catch (Exception e) {
				errorException(e.getMessage());
			}
		}
	}

	/**
	 * Private class that present operations with one argument
	 * 
	 * @author Mihael
	 *
	 */
	private static class UnaryOperationWork implements ActionListener {

		/**
		 * Action calculates new result
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				UnaryOperatorButton button = (UnaryOperatorButton) (event.getSource());
				double value = Double.parseDouble(result.getText());
				String operation = button.getName();

				switch (operation) {
				case "1/x":
					if (value == 0) {
						throw new IllegalArgumentException("Number cannot be divided with zero!");
					} else if (inv.isSelected()) {
						throw new IllegalArgumentException("1/x doesn't have invers operation!");
					}

					result.setText(String.valueOf(1 / value));
					break;
				case "=":
					try {
						functions.calculateOperation();
					} catch (Exception e) {
						result.setText("0");
					}
					break;
				case "+/-":
					functions.swapSign();
					result.setText(String.valueOf(functions.getValue()));
					break;
				default:
					functions.setValue(
							inv.isSelected() ? button.getInverse().apply(value) : button.getOperator().apply(value));
				}
				if (operation.equals("=")) {
					result.setText(String.valueOf(functions.getActiveOperand()));
				}
			} catch (Exception e) {
				errorException(e.getMessage());
			}
		}
	}

	/**
	 * When exception is caught,method prints exception description message
	 * 
	 * @param message
	 *            - exception message
	 */
	private static void errorException(String message) {
		JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Method changes result display with new value
	 */
	@Override
	public void valueChanged(CalcModel model) {
		result.setText(String.valueOf(model.getValue()));
	}
}
