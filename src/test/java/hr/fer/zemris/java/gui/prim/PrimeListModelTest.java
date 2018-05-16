package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class PrimeListModelTest {
	@Test
	public void firstTest() {
		PrimListModel model = new PrimListModel();

		for (int i = 0; i <= 5; i++) {
			model.next();
		}

		List<Integer> list = new ArrayList<>();
		for (int i = 0, size = model.getSize(); i < size; i++) {
			list.add(model.getElementAt(i));
		}

		assertEquals(Integer.valueOf(1), list.get(0));
		assertEquals(Integer.valueOf(3), list.get(1));
		assertEquals(Integer.valueOf(5), list.get(2));
		assertEquals(Integer.valueOf(7), list.get(3));
		assertEquals(Integer.valueOf(11), list.get(4));
		assertEquals(Integer.valueOf(13), list.get(5));
		assertEquals(Integer.valueOf(17), list.get(6));
	}

	@Test
	public void notGeneratedElement() {
		PrimListModel model = new PrimListModel();

		for (int i = 0; i <= 5; i++) {
			model.next();
		}

		assertNull(model.getElementAt(7));
		assertEquals(7, model.getSize());
	}
}
