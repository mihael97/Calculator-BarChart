package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Objects;
import javax.swing.JComponent;

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
	 * Length of yAxes
	 */
	private int yAxes;

	/**
	 * Length of xAxes
	 */
	private int xAxes;

	/**
	 * Reference to bottom left corner
	 */
	private Point bottomLeft;

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
		drawHorizontalGrid(graphics2d); // first we need to draw horizontal lines because bars will cover them
		drawBar(graphics2d);
		drawVerticalGrid(graphics2d); // we are drawing vertical lines like column separators
		setText(graphics2d);
		drawArrow(graphics2d);
	}

	/**
	 * Method draws vertical lines which separates columns and prints legend on x
	 * axis
	 * 
	 * @param graphics2d
	 *            - graphic
	 */
	private void drawVerticalGrid(Graphics2D graphics2d) {
		// x axis
		graphics2d.setColor(Color.GRAY);
		List<XYValue> list = chart.getList();
		FontMetrics font = graphics2d.getFontMetrics();

		int offset = (int) Math.floor((xAxes - FROM_AXIS) / list.size());
		int position = bottomLeft.x + 1;

		// double length = (yAxes);
		for (XYValue value : list) {
			// column name
			graphics2d.setColor(Color.GRAY);
			int middle = position + offset / 2 - font.stringWidth(String.valueOf(value.x));
			graphics2d.drawString(String.valueOf(String.valueOf(value.x)), middle, bottomLeft.y + FROM_AXIS);
			graphics2d.drawLine(position, (int) (bottomLeft.y - yAxes), position, bottomLeft.y);
			position += offset;
		}
	}

	/**
	 * Method draws horizontal lines where every line represents one value on y
	 * axis. Values on y axis are between <code>yMax</code> and <code>yMin</code>
	 * where difference between every value s <code>yStep</code>
	 * 
	 * @param graphics2d
	 *            - graphic
	 */
	private void drawHorizontalGrid(Graphics2D graphics2d) {
		// y axis
		FontMetrics font = graphics2d.getFontMetrics();
		graphics2d.setColor(Color.GRAY);
		int offset = (int) Math.floor(
				(yAxes - 2 - FROM_AXIS_END) / (((findMaxValue(graphics2d, true) - findMinValue()) / chart.getyStep())));

		int index = chart.getyMin();

		double limit2 = bottomLeft.x + xAxes - FROM_AXIS_END;

		for (int i = bottomLeft.y, limit = (int) (bottomLeft.y - yAxes); i >= limit; i -= offset) {
			graphics2d.setColor(Color.GRAY);
			if (i != bottomLeft.y) {
				graphics2d.drawLine(bottomLeft.x, i, (int) limit2, i);
			}

			graphics2d.drawString(String.valueOf(index),
					bottomLeft.x - font.stringWidth(String.valueOf(index)) - FROM_AXIS, i);

			index += chart.getyStep();
		}

	}

	/**
	 * Method draws arrow on the end of axis
	 * 
	 * @param graphics2d
	 *            -graphics
	 */
	private void drawArrow(Graphics2D graphics2d) {
		graphics2d.setColor(Color.GRAY);

		Point bottomRight = new Point(bottomLeft.x + xAxes, bottomLeft.y);
		Point topLeft = new Point(bottomLeft.x, bottomLeft.y - yAxes);

		// on x axis
		graphics2d.drawLine(bottomRight.x, bottomRight.y, bottomRight.x - 5, bottomRight.y - 5);
		graphics2d.drawLine(bottomRight.x, bottomRight.y, bottomRight.x - 5, bottomRight.y + 5);

		// on y axis
		graphics2d.drawLine(topLeft.x, topLeft.y, topLeft.x - 5, topLeft.y + 5);
		graphics2d.drawLine(topLeft.x, topLeft.y, topLeft.x + 5, topLeft.y + 5);
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
		int startPoint = bottomLeft.x + xAxes / 2 - font.stringWidth(chart.getxDesc()) / 2;

		graphics2d.drawString(chart.getxDesc(), startPoint, getHeight() - FROM_END / 2);

		// for y axis
		startPoint = bottomLeft.y + yAxes / 2 + font.stringWidth(chart.getyDesc()) / 2;

		AffineTransform at = new AffineTransform();
		AffineTransform trans = graphics2d.getTransform();
		at.rotate(-Math.PI / 2);
		graphics2d.setTransform(at);
		graphics2d.drawString(chart.getyDesc(), -startPoint / 2, FROM_END);

		graphics2d.setTransform(trans);
	}

	/**
	 * Method initialize positions of screen corners and length of axis
	 * 
	 * @param graphics2d
	 *            - graphics
	 */
	private void initializePositions(Graphics2D graphics2d) {
		int maxY = findMax(graphics2d, true);
		int maxX = findMax(graphics2d, false);

		Insets insets = getInsets();

		bottomLeft = new Point(FROM_END + FROM_AXIS + maxY, getHeight() - FROM_END - FROM_AXIS - maxX);

		yAxes = getHeight() - FROM_AXIS - FROM_END - FROM_END - insets.top - insets.bottom;
		xAxes = getWidth() - 2 * FROM_END - FROM_AXIS - insets.left - insets.right;
	}

	/**
	 * Method calculates and prints values on graph. Value is calculated with
	 * formula <code>value=Y/maxY*length</code> where <code>value</code> is column
	 * height in pixel,Y is y value of current {@link XYValue},<code>maxY</code> is
	 * maximal value on y axes and length is length of y axes in pixels
	 * 
	 * @param graphics2d
	 *            - graphics
	 */
	private void drawBar(Graphics2D graphics2d) {
		graphics2d.getFontMetrics();

		// x axis and bars
		List<XYValue> list = chart.getList();

		int offset = (int) Math.floor((xAxes - FROM_AXIS) / list.size());
		int position = bottomLeft.x + 1;

		int length = yAxes - FROM_AXIS_END;
		for (XYValue value : list) {
			double height = (double) value.getY() / chart.getyMax() * length;

			graphics2d.setColor(Color.ORANGE);

			Rectangle rec = new Rectangle(position, (int) (bottomLeft.y - height), (int) offset, (int) height);

			graphics2d.fill(rec);

			position += offset;
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
		graphics2d.setColor(Color.GRAY);

		graphics2d.drawLine(bottomLeft.x, bottomLeft.y, bottomLeft.x + xAxes, bottomLeft.y);
		graphics2d.drawLine(bottomLeft.x, bottomLeft.y, bottomLeft.x, bottomLeft.y - yAxes);
	}

	/**
	 * Method returns minimal value on y axis
	 * 
	 * @return minimal value on y axis
	 */
	private int findMinValue() {
		int min = 0;

		for (XYValue value : chart.getList()) {
			min = Math.min(min, value.y);
		}

		return min;
	}

}
