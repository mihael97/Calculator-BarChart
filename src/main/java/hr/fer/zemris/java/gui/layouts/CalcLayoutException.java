package hr.fer.zemris.java.gui.layouts;

/**
 * Exception which extends {@link RuntimeException} and can be thrown when
 * exception happens in {@link CalcLayout}
 * 
 * @author Mihael
 *
 */
public class CalcLayoutException extends RuntimeException {
	/**
	 * serialVerisonUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor accepts description of exception
	 * 
	 * @param desc
	 *            - exception description
	 */
	public CalcLayoutException(String desc) {
		super(desc);
	}
}
