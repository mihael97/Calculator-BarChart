package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class which extends {@link JFrame} where {@link BarChartComponent} is printed
 * 
 * @author Mihael
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor
	 * 
	 * @param path
	 *            - path to file
	 */
	public BarChartDemo(String path) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		setLocation(250, 250);
		setTitle("BarChar");
		initGUI(path);
	}

	/**
	 * Method initializes new graphical interface
	 * 
	 * @param path
	 *            - path to file
	 */
	private void initGUI(String path) {
		getContentPane().setLayout(new BorderLayout());
		BarChartComponent comp = new BarChartComponent(readFile(path));
		comp.setBounds(600, 600, 200, 500);
		add(comp, BorderLayout.CENTER);
		JLabel pathLabel = new JLabel(path, (int) CENTER_ALIGNMENT);
		pathLabel.setAlignmentX(CENTER_ALIGNMENT);
		add(pathLabel, BorderLayout.PAGE_START);
	}

	/**
	 * Method reads informations from file and makes new {@link BarChart}
	 * 
	 * @param path
	 *            - path to file
	 * @return new {@link BarChart}
	 */
	private BarChart readFile(String path) {
		try {

			List<XYValue> context = new ArrayList<>();
			String xDesc;
			String yDesc;
			int yMax;
			int yMin;
			int yStep;

			List<String> list = Files.readAllLines(Paths.get(path));

			if (list.size() != 6) {
				throw new IllegalArgumentException("File must have 6 rows!");
			}

			xDesc = list.get(0);
			yDesc = list.get(1);

			String[] array = list.get(2).split("\\s");

			for (String string : array) {
				String[] values = string.split(",");

				if (values.length != 2) {
					System.err.println("Number of arguments must be 2 but it is " + values.length);
				}

				context.add(new XYValue(Integer.parseInt(values[0]), Integer.parseInt(values[1])));
			}

			yMin = Integer.parseInt(list.get(3));
			yMax = Integer.parseInt(list.get(4));
			yStep = Integer.parseInt(list.get(5));

			return new BarChart(context, xDesc, yDesc, yMin, yMax, yStep);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Main program
	 * 
	 * @param args
	 *            - first argument is {@link Path} to file
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				new BarChartDemo(args[0]).setVisible(true);
			} catch (IndexOutOfBoundsException e) {
				System.err.println("There is not any argument!");
			}
		});
	}
}
