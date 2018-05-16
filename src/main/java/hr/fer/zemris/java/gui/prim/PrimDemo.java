package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * JFame that contains two lists with generated prime numbers. It has button
 * 'next' which action is generating new prime number
 * 
 * @author Mihael
 *
 */
public class PrimDemo extends JFrame implements ListDataListener {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Main program from where we start our frame
	 * 
	 * @param args
	 *            - not in use
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
		setSize(500, 500);
		initGUI();
	}

	/**
	 * Method makes GUI
	 */
	private void initGUI() {
		setLayout(new BorderLayout());
		PrimListModel model = new PrimListModel();

		JList<Integer> left = new JList<>(model);
		left.setSize(left.getPreferredSize());
		JList<Integer> right = new JList<>(model);

		JButton next = new JButton("Next");
		next.addActionListener(e -> {
			model.next();
		});
		add(next, BorderLayout.PAGE_END);
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(1, 2));
		center.add(new JScrollPane(left));
		center.add(new JScrollPane(right));
		add(center, BorderLayout.CENTER);
	}

	/**
	 * Method is called when content is changed
	 * 
	 * @param event
	 *            - event about change
	 */
	@Override
	public void contentsChanged(ListDataEvent event) {
		repaint();
	}

	/**
	 * Method is called when some interval is added
	 * 
	 * @param event
	 *            - event about change
	 */
	@Override
	public void intervalAdded(ListDataEvent event) {
		repaint();
	}

	/**
	 * Method is called when some interval is removed
	 * 
	 * @param event
	 *            - event about change
	 */
	@Override
	public void intervalRemoved(ListDataEvent event) {
		repaint();
	}
}
