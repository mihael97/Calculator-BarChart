package hr.fer.zemris.java.gui.layouts;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Main  extends JFrame{

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Main().setVisible(true);
		});
	}
	
	public Main() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}

	private void initGUI() {
		JPanel p = new JPanel(new Layout2(3));
		p.add(new JLabel("x"), new RCPosition(1,1));
		p.add(new JLabel("y"), new RCPosition(2,3));
		p.add(new JLabel("z"), new RCPosition(2,7));
		p.add(new JLabel("w"), new RCPosition(4,2));
		p.add(new JLabel("a"), new RCPosition(4,5));
		p.add(new JLabel("b"), new RCPosition(4,7));		
	}

}
