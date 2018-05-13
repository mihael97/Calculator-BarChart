package hr.fer.zemris.java.gui.layouts;

import static org.junit.Assert.assertEquals;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Ignore;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class CalcLayoutTest {
	
	@Test(expected = CalcLayoutException.class)
	public void underRowLimit() {
		JPanel panel = new JPanel(new CalcLayout());
		panel.add(new JLabel("Mihael"), new RCPosition(-1, 4));
	}
	
	@Test(expected = CalcLayoutException.class)
	public void overRowLimit() {
		JPanel panel = new JPanel(new CalcLayout());
		panel.add(new JLabel("Mihael"), new RCPosition(6, 4));
	}
	
	@Test(expected = CalcLayoutException.class)
	public void underColumnLimit() {
		JPanel panel = new JPanel(new CalcLayout());
		panel.add(new JLabel("Mihael"), new RCPosition(2, -2));
	}
	
	@Test(expected = CalcLayoutException.class)
	public void overColumnLimit() {
		JPanel panel = new JPanel(new CalcLayout());
		panel.add(new JLabel("Mihael"), new RCPosition(2, 8));
	}
	
	@Test
	public void validPosition() {
		JPanel panel = new JPanel(new CalcLayout());
		panel.add(new JLabel("Mihael"), new RCPosition(1, 1));
	}
	
	@Test(expected=CalcLayoutException.class)
	public void insideFirstElement() {
		JPanel panel = new JPanel(new CalcLayout());
		panel.add(new JLabel("Mihael"), new RCPosition(1, 3));
	}
	
	@Test(expected=CalcLayoutException.class)
	public void moreComponentsOnSamePosition() {
		JPanel panel = new JPanel(new CalcLayout());
		panel.add(new JLabel("Mihael"), new RCPosition(1, 1));
		panel.add(new JLabel("Ivan"), new RCPosition(1, 1));
	}
	
	@Ignore
	@Test
	public void prefferedSizeFirst() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(158, dim.height);

		assertEquals(152, dim.width);

	}
	
	@Test
	public void prefferedSizeSecond() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		//assertEquals(158, dim.height);

		assertEquals(152, dim.width);

	}
}
