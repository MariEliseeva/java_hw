package spbau.eliseeva.set;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests MySet class - container with non-repeating elements.
 */
public class MySetTest {

    /**
     * Adds elements to the container and checking
     * if they were added or already existed.
     * @throws Exception thrown if test is not ok
     */
    @Test
    public void add() throws Exception {
        MySet<Integer> set = new MySet<>();
        assertEquals(true, set.add(3));
        assertEquals(true, set.add(17));
        assertEquals(false, set.add(17));
        MySet<String> set2 = new MySet<>();
        assertEquals(true, set2.add("string1"));
        assertEquals(false, set2.add("string1"));
    }

    /**
     * Checks the size of an empty container or a container with two elements.
     * @throws Exception thrown if test is not ok
     */
    @Test
    public void size() throws Exception {
        MySet<String> set2 = new MySet<>();
        assertEquals(0, set2.size());
        set2.add("string1");
        set2.add("string2");
        assertEquals(2, set2.size());
        set2.add("string2");
        assertEquals(2, set2.size());
    }

    /**
     * Adds elements in the container and checks if they are there or not.
     * @throws Exception thrown if test is not ok
     */
    @Test
    public void contains() throws Exception {
        MySet<Integer> set = new MySet<>();
        set.add(3);
        set.add(17);
        set.add(2);
        set.add(26);
        set.add(31);
        set.add(1);
        assertEquals(true, set.contains(3));
        assertEquals(false, set.contains(24));
        assertEquals(true, set.contains(17));
    }
}