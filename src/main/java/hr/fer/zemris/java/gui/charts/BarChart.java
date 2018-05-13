package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;

public class BarChart {
	List<XYValue> list;
	String xDesc, yDesc;
	int yMin, yMax, yStep;

	public BarChart(List<XYValue> list, String xDesc, String yDesc, int yMin, int yMax, int yStep) {
		this.list = Objects.requireNonNull(list);
		this.xDesc = Objects.requireNonNull(xDesc);
		this.yDesc = Objects.requireNonNull(yDesc);
		this.yMin = yMin;
		this.yMax = yMax;
		this.yStep = check(yMin, yStep, yMax);
	}

	private int check(int yMin, int yStep, int yMax) {
		return yStep >= yMin && yStep <= yMax ? yStep : yMin + 1;
	}

}
