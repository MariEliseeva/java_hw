package spbau.eliseeva.treeset;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests TreeSet class's methods.*/
public class TreeSetTest {
    /** Add number and checks that the TreeSet really contains them. */
    @Test
    public void containsTest() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(1);
        treeSet.add(15);
        assertEquals(true, treeSet.contains(15));
        assertEquals(false, treeSet.contains(25));
    }

    /** Add number to the TreeSet, checks if they are added or already been. */
    @Test
    public void addTest() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        assertEquals(true, treeSet.add(17));
        assertEquals(true, treeSet.add(4));
        assertEquals(false, treeSet.add(4));
    }

    /** Add numbers and remove them, checks if were removed or didn't exist. */
    @Test
    public void removeTest() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(17);
        treeSet.add(15);
        treeSet.add(125);
        treeSet.add(4);
        treeSet.add(16);
        assertEquals(true, treeSet.remove(15));
        assertEquals(true, treeSet.remove(17));
        assertEquals(false, treeSet.remove(111));
    }

    /** Iterate through the TreeSet, checks if the order is correct. */
    @Test
    public void iteratorTest() {
        TreeSet<Integer> treeSet = new TreeSet<>((integer, t1) -> -1);
        treeSet.add(17);
        treeSet.add(15);
        treeSet.add(125);
        treeSet.add(4);
        treeSet.add(16);
        Iterator<Integer> iterator = treeSet.iterator();
        assertEquals(true, iterator.hasNext());
        assertEquals(new Integer(16), iterator.next());
        assertEquals(new Integer(4), iterator.next());
        assertEquals(new Integer(125), iterator.next());
    }

    /** Checks if size if correct after adding and removing elements.*/
    @Test
    public void sizeTest() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        assertEquals(0, treeSet.size());
        treeSet.add(17);
        assertEquals(1, treeSet.size());
        treeSet.add(15);
        treeSet.remove(15);
        assertEquals(1, treeSet.size());
        TreeSet<Integer> treeSet2 = new TreeSet<>((integer, t1) -> 0);
        treeSet2.add(17);
        assertEquals(1,treeSet2.size());
        treeSet2.add(135);
        assertEquals(1,treeSet2.size());
        treeSet2.add(128);
        assertEquals(1,treeSet2.size());
    }

    /**
     * Iterate from the end to the begin, using descending iterator,
     * checks that the order is correct.
     */
    @Test
    public void descendingIteratorTest() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(17);
        treeSet.add(15);
        treeSet.add(125);
        treeSet.add(4);
        treeSet.add(16);
        Iterator<Integer> iterator = treeSet.descendingIterator();
        assertEquals(true, iterator.hasNext());
        assertEquals(new Integer(125), iterator.next());
        assertEquals(new Integer(17), iterator.next());
        assertEquals(new Integer(16), iterator.next());
    }

    /** Checks that the smallest element is really the first one.*/
    @Test
    public void firstTest() {
        TreeSet<Integer> treeSet = new TreeSet<>((integer, t1) -> 1);
        treeSet.add(17);
        treeSet.add(135);
        treeSet.add(128);
        assertEquals(new Integer(17), treeSet.first());
    }

    /** Checks that the greatest element is really the last one.*/
    @Test
    public void lastTest() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(17);
        treeSet.add(135);
        treeSet.add(128);
        assertEquals(new Integer(135), treeSet.last());
    }

    /** Checks that lower for the given value is correct.*/
    @Test
    public void lowerTest() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(17);
        treeSet.add(135);
        treeSet.add(128);
        treeSet.add(15);
        treeSet.add(125);
        treeSet.add(4);
        assertEquals(new Integer(125), treeSet.lower(128));
    }

    /** Checks that floor for the given value is correct.*/
    @Test
    public void floorTest() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(17);
        treeSet.add(135);
        treeSet.add(128);
        treeSet.add(15);
        treeSet.add(125);
        treeSet.add(4);
        assertEquals(new Integer(128), treeSet.floor(128));
    }

    /** Checks that ceiling for the given value is correct.*/
    @Test
    public void ceilingTest() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(17);
        treeSet.add(13);
        treeSet.add(8);
        treeSet.add(115);
        treeSet.add(14);
        treeSet.add(42);
        assertEquals(new Integer(42), treeSet.ceiling(42));
    }

    /** Checks that higher for the given value is correct.*/
    @Test
    public void higherTest() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(17);
        treeSet.add(13);
        treeSet.add(8);
        treeSet.add(115);
        treeSet.add(14);
        treeSet.add(42);
        assertEquals(new Integer(115), treeSet.higher(42));
    }

    /** Tests some of the previous methods for descending set.*/
    @Test
    public void descendingSetTest() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        MyTreeSet<Integer> dTreeSet = treeSet.descendingSet();
        treeSet.add(17);
        treeSet.add(13);
        treeSet.add(19);
        treeSet.add(4);
        treeSet.add(16);
        treeSet.add(15);
        assertEquals(new Integer(4), dTreeSet.last());
        assertEquals(new Integer(19), dTreeSet.first());
        assertEquals(6, dTreeSet.size());
        assertEquals(new Integer(13), dTreeSet.higher(15));
        assertEquals(new Integer(19), dTreeSet.lower(17));
        assertEquals(new Integer(15), dTreeSet.floor(15));
        assertEquals(new Integer(17), dTreeSet.ceiling(17));
        Iterator<Integer> iterator = dTreeSet.iterator();
        assertEquals(new Integer(19), iterator.next());
        assertEquals(new Integer(17), iterator.next());
        assertEquals(new Integer(16), iterator.next());
    }
}