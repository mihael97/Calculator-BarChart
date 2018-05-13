package hr.fer.zemris.java.gui.calc.Calculator.interfaces;

import hr.fer.zemris.java.gui.calc.Calculator.Calculator;

/**
 * Interface provides method for {@link Calculator} listener
 * 
 * @author Mihael
 *
 */
public interface CalcValueListener {
	/**
	 * Method is contacted when change is made in model
	 * 
	 * @param model
	 *            - reference to model where is changed value stored
	 */
	void valueChanged(CalcModel model);
}