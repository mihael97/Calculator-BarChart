package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Layout2 implements LayoutManager2 {
	/**
	 * Razmak između redaka i stupaca.
	 */
	private int gap;

	/** Fiksan broj redaka. **/
	private static final int ROWS = 5;

	/** Fiksan broj stupaca. **/
	private static final int COLS = 7;

	/** Maksimalan broj komponenti. **/
	private static final int MAX_COMPONENTS = 31;

	/** Singleton za element na poziciji(1,1). **/
	private static final RCPosition FIRST_COMPONENT = new RCPosition(1, 1);

	/**
	 * Mapa koja pamti komponente i njihove pozicije.
	 */
	private Map<Component, RCPosition> table;

	/**
	 * Set koji pamti ograničenja koja već postoje.
	 */
	private Set<RCPosition> setConstraints;

	/**
	 * Konstruktor.
	 */
	public Layout2() {
		this(0);
	}

	/**
	 * Konstruktor.
	 * @param gap željeni razmak između redaka i stupaca
	 */
	public Layout2(int gap) {
		this.gap = gap;
		table = new HashMap<>();
		setConstraints = new HashSet<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition constraint;
		if (constraints instanceof String) {
			String value = (String) constraints;
			try {
				constraint = new RCPosition(Integer.parseInt((value.split(",")[0])),
						Integer.parseInt(value.split(",")[1]));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Illegal string format.");
			}
		} else if (constraints instanceof RCPosition) {
			constraint = (RCPosition) constraints;
		} else {
			throw new IllegalArgumentException("Ilegal type of constraints");
		}
		if (table.size() == MAX_COMPONENTS) {
			throw new IllegalArgumentException("Layout is full");
		}
		if (!checkConstraint(constraint)) {
			throw new IllegalArgumentException("Illegal constraints value");
		}
		if (!setConstraints.add(constraint)) {
			throw new IllegalArgumentException("Contraint already exists");
		}
		table.put(comp, constraint);
	}

	private boolean checkConstraint(RCPosition constraint) {
		if (constraint.getRow() < 1 || constraint.getRow() > ROWS) {
			return false;
		}
		if (constraint.getColumn() < 1 || constraint.getColumn() > COLS) {
			return false;
		}
		if (constraint.getRow() == 1 && constraint.getColumn() >= 2 && constraint.getColumn() <= 5) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		RCPosition pos = table.get(comp);
		setConstraints.remove(pos);
		table.remove(comp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		int w = 0;
		int h = 0;
		for (Map.Entry<Component, RCPosition> entry : table.entrySet()) {
			Component c = entry.getKey();
			RCPosition pos = entry.getValue();
			if (c.getPreferredSize() != null) {
				h = Math.max(h, c.getPreferredSize().height);
				if (pos.equals(FIRST_COMPONENT)) { continue; }
				w = Math.max(w, c.getPreferredSize().width);
			}
		}
		return new Dimension(
				parent.getInsets().left + parent.getInsets().right + COLS * w + (COLS - 1) * gap,
				parent.getInsets().top + parent.getInsets().bottom + ROWS * h + (ROWS - 1) * gap
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		int w = 0;
		int h = 0;
		for (Map.Entry<Component, RCPosition> entry : table.entrySet()) {
			Component c = entry.getKey();
			RCPosition pos = entry.getValue();
			if (c.getMinimumSize() != null) {
				h = Math.max(h, c.getMinimumSize().height);
				if (pos.equals(FIRST_COMPONENT)) { continue; }
				w = Math.max(w, c.getMinimumSize().width);
			}
		}
		return new Dimension(
				parent.getInsets().left + parent.getInsets().right + COLS * w + (COLS - 1) * gap,
				parent.getInsets().top + parent.getInsets().bottom + ROWS * h + (ROWS - 1) * gap
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension maximumLayoutSize(Container parent) {
		int w = 0;
		int h = 0;
		for (Map.Entry<Component, RCPosition> entry : table.entrySet()) {
			Component c = entry.getKey();
			RCPosition pos = entry.getValue();
			if (c.getMaximumSize() != null) {
				h = Math.max(h, c.getMaximumSize().height);
				if (pos.equals(FIRST_COMPONENT)) { continue; }
				w = Math.max(w, c.getMaximumSize().width);
			}
		}
		return new Dimension(
				parent.getInsets().left + parent.getInsets().right + COLS * w + (COLS - 1) * gap,
				parent.getInsets().top + parent.getInsets().bottom + ROWS * h + (ROWS - 1) * gap
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			/**
			 * Odredi visinu i širinu za komponente.
			 */
			Dimension dim = getInfo();

			double ratioX = (double) parent.getWidth() / preferredLayoutSize(parent).getWidth();
			double ratioY = (double) parent.getHeight() / preferredLayoutSize(parent).getHeight();

			dim.setSize(ratioX * dim.getWidth(),  ratioY * dim.getHeight());

			/**
			 * Postavi komponente na odgovarajuće pozicije i korigiraj njihovu veličinu.
			 */
			for (Map.Entry<Component, RCPosition> entry : table.entrySet()) {
				setComponentPositionAndSize(entry.getKey(), entry.getValue(), dim);
			}
		}
	}

	/**
	 * Metoda koja postavlja visinu i širinu komponente CalcLayouta.
	 * @param component komponenta kojoj se postavljaju dimenzije.
	 * @param position pozicija komponente.
	 * @param dim dimenzije na koje se postavlja najmanja komponenta.
	 */
	private void setComponentPositionAndSize(Component component, RCPosition position, Dimension dim) {
		if (position.equals(FIRST_COMPONENT)) {
			component.setBounds(0, 0, 5 * dim.width + 4 * gap, dim.height);
			return;
		}
		component.setBounds(
				(position.getColumn() - 1) * (dim.width + gap),
				(position.getRow() - 1) * (dim.height + gap),
				dim.width,
				dim.height
		);
	}

	/**
	 * Metoda koja izračunava visinu i širinu za stupce i retke na temelju maksimuma
	 * preferiranih vrijednosti.
	 *
	 * @return visina i širina za stupce i retke.
	 */
	private Dimension getInfo() {
		int w = 0;
		int h = 0;
		for (Map.Entry<Component, RCPosition> entry : table.entrySet()) {
			Component c = entry.getKey();
			RCPosition pos = entry.getValue();
			if (c.getPreferredSize() != null) {
				h = Math.max(h, c.getPreferredSize().height);
				if (pos.equals(FIRST_COMPONENT)) { continue; }
				w = Math.max(w, c.getPreferredSize().width);
			}
		}
		return new Dimension(w, h);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidateLayout(Container target) {
	}

}
