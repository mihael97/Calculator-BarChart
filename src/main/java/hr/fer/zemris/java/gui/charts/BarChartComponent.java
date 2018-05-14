package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Objects;
import javax.swing.JComponent;

import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Class implements graphical representation of {@link BarChart}
 * 
 * @author Mihael
 *
 */
public class BarChartComponent extends JComponent {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constant space from end of screen
	 */
	private int FROM_END = 50;

	/**
	 * Constant space from axis
	 */
	private final int FROM_AXIS = 10;

	/**
	 * Reference to top left corner of system
	 */
	private RCPosition topLeft;

	/**
	 * Reference to bottom right corner
	 */
	private RCPosition bottomRight;

	/**
	 * Reference to bottom left corner
	 */
	private RCPosition bottomLeft;

	/**
	 * Space between end of axis line and end of graph
	 */
	private final int FROM_AXIS_END = 10;

	/**
	 * Reference to {@link BarChart}
	 */
	BarChart chart;

	/**
	 * Constructor accepts {@link BarChart},source of informations for drawing
	 * 
	 * @param barChart
	 *            - reference to {@link BarChart}
	 * @throws NullPointerException
	 *             - if reference to {@link BarChart} is <code>null</code>
	 */
	public BarChartComponent(BarChart barChart) {
		this.chart = Objects.requireNonNull(barChart);
	}

	/**
	 * Method paints whole component and draws graph with informations given in
	 * reference to {@link BarChart}
	 * 
	 * @param graphic
	 *            - graphic
	 */
	@Override
	protected void paintComponent(Graphics graphic) {
		Graphics2D graphics2d = (Graphics2D) graphic.create();

		initializePositions(graphics2d);
		drawSystem(graphics2d);
		drawGrid(graphics2d);
		drawAxis(graphics2d);
		setText(graphics2d);
		drawArrow(graphics2d);
	}

	/**
	 * Method draws arrow on the end of axis
	 * 
	 * @param graphics2d
	 *            -graphics
	 */
	private void drawArrow(Graphics2D graphics2d) {
		graphics2d.setColor(Color.YELLOW);
		// on x axis
		graphics2d.drawLine(bottomRight.getColumn(), bottomRight.getRow(), bottomRight.getColumn() - 5,
				bottomRight.getRow() - 5);
		graphics2d.drawLine(bottomRight.getColumn(), bottomRight.getRow(), bottomRight.getColumn() - 5,
				bottomRight.getRow() + 5);

		// on y axis
		graphics2d.drawLine(topLeft.getColumn(), topLeft.getRow(), topLeft.getColumn() - 5, topLeft.getRow() + 5);
		graphics2d.drawLine(topLeft.getColumn(), topLeft.getRow(), topLeft.getColumn() + 5, topLeft.getRow() + 5);
	}

	/**
	 * Method sets axis name for every axis(x and y)
	 * 
	 * @param graphics2d
	 *            - graphic
	 */
	private void setText(Graphics2D graphics2d) {
		FontMetrics font = graphics2d.getFontMetrics();
		graphics2d.setColor(Color.BLACK);

		// for x axis
		int startPoint = bottomLeft.getColumn() + (bottomRight.getColumn() - bottomLeft.getColumn()) / 2
				- font.stringWidth(chart.getxDesc()) / 2;

		graphics2d.drawString(chart.getxDesc(), startPoint, getHeight() - FROM_END / 2);

		// for y axis
		startPoint = bottomLeft.getRow() + (bottomLeft.getRow() - topLeft.getRow()) / 2
				+ font.stringWidth(chart.getyDesc()) / 2;

		AffineTransform at = new AffineTransform();
		AffineTransform trans = graphics2d.getTransform();
		at.rotate(-Math.PI / 2);
		graphics2d.setTransform(at);
		graphics2d.drawString(chart.getyDesc(), -startPoint / 2, FROM_END);

		graphics2d.setTransform(trans);
	}

	/**
	 * Method initialize positions of screen corners
	 * 
	 * @param graphics2d
	 *            - graphics
	 */
	private void initializePositions(Graphics2D graphics2d) {
		int maxY = findMax(graphics2d, true);
		int maxX = findMax(graphics2d, false);

		if (getInsets().left != 0)
			FROM_END = getInsets().left;

		bottomLeft = new RCPosition(getHeight() - FROM_END - FROM_AXIS - maxX, FROM_END + FROM_AXIS + maxY);
		bottomRight = new RCPosition(getHeight() - FROM_END - FROM_AXIS - maxX, getWidth() - FROM_END);
		topLeft = new RCPosition(FROM_END / 2 + 10, FROM_END + FROM_AXIS + maxY);
	}

	/**
	 * Method draws grid inside graph
	 * 
	 * @param graphics2d
	 *            - graphic
	 */
	private void drawGrid(Graphics2D graphics2d) {
		graphics2d.setColor(Color.GREEN);

		int offset = (int) Math.floor((bottomLeft.getRow() - topLeft.getRow() - FROM_AXIS_END)
				/ ((findMaxValue(graphics2d, true) / chart.getyStep()))) / chart.getyStep();
		int index = bottomLeft.getRow() - chart.getyStep() * offset;

		for (int i = chart.getyStep(), limit = findMaxValue(graphics2d, true); i <= limit; i += chart.getyStep()) {
			graphics2d.drawLine(bottomLeft.getColumn(), index, bottomRight.getColumn() - FROM_AXIS_END, index);
			index -= chart.getyStep() * offset;
		}

	}

	/**
	 * Method draws axis lines and values of graph
	 * 
	 * @param graphics2d
	 *            - graphics
	 */
	private void drawAxis(Graphics2D graphics2d) {
		// y axis
		FontMetrics font = graphics2d.getFontMetrics();
		int index = bottomLeft.getRow() + font.stringWidth("0") / 2 + 2;
		graphics2d.setColor(Color.RED);
		int offset = (int) Math.floor(
				(bottomLeft.getRow() - topLeft.getRow() - 10) / ((findMaxValue(graphics2d, true) / chart.getyStep())));
		for (int i = chart.getyMin(), limit = chart.getyMax(), step = chart.getyStep(); i <= limit; i += step) {
			graphics2d.drawString(String.valueOf(i),
					bottomLeft.getColumn() - font.stringWidth(String.valueOf(i)) - FROM_AXIS, index);
			index -= offset;
		}

		// x axis
		int yOffset = (int) Math.ceil(offset / chart.getyStep());

		offset = (int) Math
				.floor((bottomRight.getColumn() - bottomLeft.getColumn() - FROM_AXIS) / chart.getList().size());
		index = bottomLeft.getColumn() + 2;

		List<XYValue> list = chart.getList();

		for (XYValue value : list) {
			Rectangle rec = new Rectangle(index, bottomLeft.getRow() - yOffset * value.getY(), offset,
					yOffset * value.getY());
			rec.setBounds(index - 1, bottomLeft.getRow() - yOffset * value.getY() - 1, offset - 1,
					yOffset * value.getY() - 1);

			graphics2d.fill(rec);

			// column name
			int middle = index + offset / 2 - font.stringWidth(String.valueOf(value.x));
			graphics2d.drawString(String.valueOf(String.valueOf(value.x)), middle, bottomLeft.getRow() + FROM_AXIS);
			index += offset;
		}

	}

	/**
	 * Method returns maximal pixel length of values on axis. If <code>way</code> is
	 * true,method will return maximal value for y axis,otherwise maximal value for
	 * x axis
	 * 
	 * @param graphics2d
	 *            - graphic
	 * @param way
	 *            - type of result we want
	 * @return maximal pixel length of values on axis
	 */
	private int findMax(Graphics2D graphics2d, boolean way) {
		int max = 0;

		FontMetrics font = graphics2d.getFontMetrics();

		for (XYValue value : chart.getList()) {

			if (way) {
				max = Math.max(max, font.stringWidth(String.valueOf(value.getY())));
			} else {
				max = Math.max(max, font.stringWidth(String.valueOf(value.getX())));
			}
		}

		return max;
	}

	/**
	 * Method returns value on axis. If <code>way</code> is true,method will return
	 * maximal value for y axis,otherwise maximal value for x axis
	 * 
	 * @param graphics2d
	 *            - graphic
	 * @param way
	 *            - type of result we want
	 * @return maximal value on axis
	 */
	private int findMaxValue(Graphics2D graphics2d, boolean way) {
		int max = 0;

		for (XYValue value : chart.getList()) {

			if (way) {
				max = Math.max(max, value.getY());
			} else {
				max = Math.max(max, value.getX());
			}
		}

		return max;
	}

	/**
	 * Method creates and draws coordinate system
	 * 
	 * @param graphics2d
	 *            - {@link Graphics2D}
	 */
	private void drawSystem(Graphics2D graphics2d) {
		graphics2d.setColor(Color.YELLOW);

		graphics2d.drawLine(bottomLeft.getColumn(), bottomLeft.getRow(), bottomRight.getColumn(), bottomRight.getRow());
		graphics2d.drawLine(bottomLeft.getColumn(), bottomLeft.getRow(), topLeft.getColumn(), topLeft.getRow());
	}

}
