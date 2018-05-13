package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * List model that implements prime number generator
 * 
 * @author Mihael
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/**
	 * Already generated prime numbers
	 */
	ArrayList<Integer> primeNumbers;
	/**
	 * List of listeners
	 */
	ArrayList<ListDataListener> listeners;

	/**
	 * Default constructor
	 */
	public PrimListModel() {
		primeNumbers = new ArrayList<>();
		primeNumbers.add(1);
		listeners = new ArrayList<>();
	}

	/**
	 * Method adds listener in list of all listeners
	 * 
	 * @param listener
	 *            - listener we want to add
	 */
	@Override
	public void addListDataListener(ListDataListener listener) {
		listeners.add(listener);
	}

	/**
	 * Method returns generated number at given index. If argument is greater than
	 * amount of generated prime numbers,method will return <code>null</code>
	 * 
	 * @param index
	 *            - position of prime number we want
	 * @return prime number at given index
	 */
	@Override
	public Integer getElementAt(int index) {
		return index >= primeNumbers.size() ? null : primeNumbers.get(index);
	}

	/**
	 * Method returns number of already generated numbers
	 * 
	 * @return number of generated numbers
	 */
	@Override
	public int getSize() {
		return primeNumbers.size();
	}

	/**
	 * Method removes listeners from listener list
	 * 
	 * @param l
	 *            - listener we want to remove
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	/**
	 * Method generates next prime number and after that calls every listener with
	 * information that interval is added
	 */
	public void next() {
		int lastGenerated = primeNumbers.get(primeNumbers.size() - 1);

		while (true) {
			lastGenerated++;
			boolean prime = false;

			for (int j = 2; j < Math.sqrt(lastGenerated) + 1; j++) {
				if (lastGenerated % j == 0) {
					prime = true;
					break;
				}
			}

			if (!prime) {
				primeNumbers.add(lastGenerated);
				break;
			}

		}

		ListDataEvent event = new ListDataEvent(primeNumbers, ListDataEvent.INTERVAL_ADDED, primeNumbers.size() - 1,
				primeNumbers.size() - 1);

		for (ListDataListener listener : listeners) {
			listener.contentsChanged(event);
		}

	}
}
