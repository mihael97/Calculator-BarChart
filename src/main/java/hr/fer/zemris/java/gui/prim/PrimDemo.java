package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimDemo extends JFrame implements ListDataListener {
	/**
	 * Main program from where we start
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}

	/**
	 * Default constructor
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(500, 500);
		setSize(200, 200);
		initGUI();
	}

	/**
	 * Method makes GUI
	 */
	private void initGUI() {
		setLayout(new BorderLayout());
		PrimListModel model = new PrimListModel();

		JList<Integer> left = new JList<>(model);
		JList<Integer> right = new JList<>(model);

		JButton next = new JButton("Next");
		next.addActionListener(e -> {
			model.next();
		});
		add(next, BorderLayout.PAGE_END);
		JPanel center = new JPanel();
		center.add(left);
		center.add(right);
		JScrollPane scrollPane = new JScrollPane(center);
		add(scrollPane, BorderLayout.CENTER);
	}

	@Override
	public void contentsChanged(ListDataEvent arg0) {
		repaint();
	}

	@Override
	public void intervalAdded(ListDataEvent arg0) {
		repaint();
	}

	@Override
	public void intervalRemoved(ListDataEvent arg0) {
		repaint();
	}
}
