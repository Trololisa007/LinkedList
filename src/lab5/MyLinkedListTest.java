package lab5;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ConcurrentModificationException;
import java.util.ListIterator;

import org.junit.jupiter.api.Test;

class MyLinkedListTest {

    @Test
    void testSize() {
        MyLinkedList<Integer> test = new MyLinkedList<Integer>();
        assertEquals(test.size(), 0, "Size after construction");
        test.add(0, 5);
        assertEquals(test.size(), 1, "Size after add");
    }

    @Test
    void testAddE() {
        MyLinkedList<Integer> arr = new MyLinkedList<Integer>();
        for (int i = 0; i < 21; i++) {
            arr.add(i);
        }
        for (int i = 0; i < 21; i++) {
            assertEquals(arr.get(i), i, "failed for i = " + i);
        }
    }

    @Test
    void testAddIntEFront() {
        MyLinkedList<Integer> arr = new MyLinkedList<Integer>();
        for (int i = 0; i < 21; i++) {
            arr.add(0, i);
        }
        for (int i = 0; i < 21; i++) {
            assertEquals(arr.get(i), 20 - i, "failed for i = " + i);
        }
    }

    @Test
    void testAddIntEMid() {
        MyLinkedList<Integer> arr = new MyLinkedList<Integer>();
        Integer[] data = new Integer[21];
        for (int i = 0; i < 10; i++) {
            data[i] = 2 * (i) + 1;
        }
        for (int i = 0; i < 11; i++) {
            data[10 + i] = 20 - 2 * i;
        }

        for (int i = 0; i < 21; i++) {
            arr.add(arr.size() / 2, i);
        }
        for (int i = 1; i < 21; i++) {
            assertEquals(data[i], arr.get(i));
        }
    }

    @Test
    void testAddOutOfBounds() {
        MyLinkedList<Integer> arr = new MyLinkedList<Integer>();
        assertThrows(IndexOutOfBoundsException.class, () -> arr.add(3, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> arr.add(-3, 4));
    }

    @Test
    void testGetOutOfBounds() {
        MyLinkedList<Integer> arr = new MyLinkedList<Integer>();
        assertThrows(IndexOutOfBoundsException.class, () -> arr.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> arr.get(-3));
    }

    @Test
    void testSet() {
        MyLinkedList<Integer> arr = new MyLinkedList<Integer>();
        MyLinkedList<Integer> temp = new MyLinkedList<Integer>();
        for (int i = 0; i < 21; i++) {
            arr.add(i);
        }
        for (int i = 20; i > -1; i--) {
            temp.add(arr.get(i));
        }
        for (int i = 0; i < 21; i++) {
            arr.set(i, temp.get(i));
        }

        for (int i = 0; i < 21; i++) {
            assertEquals(20 - i, arr.get(i));
        }

        assertThrows(IndexOutOfBoundsException.class, () -> arr.set(21, 3));
        assertThrows(IndexOutOfBoundsException.class, () -> arr.set(-1, 3));
    }

    @Test
    void testRemove() {
        MyLinkedList<Integer> arr = new MyLinkedList<Integer>();
        MyLinkedList<Integer> temp = new MyLinkedList<Integer>();
        for (int i = 0; i < 21; i++) {
            arr.add(i);
        }
        for (int i = 0; i < 10; i++) {
            temp.add(arr.remove(i));
        }
        for (int i = 0; i < temp.size(); i++) {
            assertEquals(arr.get(i), i * 2 + 1, "arr");
            assertEquals(temp.get(i), i * 2, "temp");
        }
    }

    @Test
    void testIsEmpty() {
        MyLinkedList<Integer> arr = new MyLinkedList<Integer>();
        assertTrue(arr.isEmpty());
        arr.add(2);
        assertFalse(arr.isEmpty());
        arr.remove(0);
        assertTrue(arr.isEmpty());
    }

    @Test
    void testClear() {
        MyLinkedList<Integer> arr = new MyLinkedList<Integer>();
        for (int i = 0; i < 5; i++) {
            arr.add(i);
        }
        arr.clear();
        assertEquals(0, arr.size());
        assertThrows(IndexOutOfBoundsException.class, () -> arr.get(0));
    }

    @Test
    void testMyListIterator() {
        MyLinkedList<Integer> arr = new MyLinkedList<Integer>();
        for (int i = 0; i < 21; i++) {

        }
        ListIterator<Integer> test = arr.listIterator();
        int w = 0;
        while (test.hasNext()) {
            assertEquals(w, test.next());
            w++;
        }
        w--;
        while (test.hasPrevious()) {
            assertEquals(w, test.previous());
            w--;
        }
        arr.add(4);
        assertThrows(ConcurrentModificationException.class, () -> test.next());
    }
}
