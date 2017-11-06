package spbau.eliseeva.fp.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.*;

/** Tests the collection class's methods. */
public class CollectionsTest {
    /** Want to square all the elements. */
    @Test
    public void mapTest() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        LinkedList<Integer> expectedResult = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            arrayList.add(i);
            expectedResult.add(i * i);
        }
        assertEquals(expectedResult, Collections.map(value -> value * value, arrayList));
    }

    /** Choses elements, which are divided by 17.*/
    @Test
    public void filter() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        LinkedList<Integer> expectedResult = new LinkedList<>();
        for (int i = 0; i < 52; i++) {
            arrayList.add(i);
        }
        expectedResult.add(0);
        expectedResult.add(17);
        expectedResult.add(34);
        expectedResult.add(51);
        assertEquals(expectedResult, Collections.filter(value -> (value % 17) == 0, arrayList));
    }

    /** Takes all numbers less 100.*/
    @Test
    public void takeWhile() {
        ArrayList<String> arrayList = new ArrayList<>();
        LinkedList<String> expectedResult = new LinkedList<>();
        for (int i = 0; i < 1203; i++) {
            arrayList.add(String.valueOf(i));
            if (String.valueOf(i).length() < 3) {
                expectedResult.add(String.valueOf(i));
            }
        }
        assertEquals(expectedResult, Collections.takeWhile(value -> value.length() < 3, arrayList));
    }

    /** Takes strings while not appeared string from 4 'a' letters.*/
    @Test
    public void takeUnless() {
        LinkedList<String> linkedList = new LinkedList<>();
        LinkedList<String> expectedResult = new LinkedList<>();
        linkedList.add("abcD");
        linkedList.add("AAaa");
        linkedList.add("AcdE");
        linkedList.add("123a");
        expectedResult.add("abcD");
        assertEquals(expectedResult, Collections.takeUnless(value -> value.toUpperCase().equals("AAAA"), linkedList));
    }

    /** Just sum of first 179 numbers.*/
    @Test
    public void foldl() throws Exception {
        LinkedList<Integer> linkedList = new LinkedList<>();
        Integer expectedResult = 0;
        for (int i = 0; i < 179; i++) {
            linkedList.add(i);
            expectedResult += i;
        }
        assertEquals(expectedResult, Collections.foldl((value1, value2) -> value1 + value2, 0,linkedList));
    }

    /** Counts cum of numerals in all numbers. */
    @Test
    public void foldr() throws Exception {
        LinkedList<Integer> linkedList = new LinkedList<>();
        Integer expectedResult = 0;
        for (int i = 0; i < 25; i++) {
            linkedList.add(111);
            expectedResult += 3;
        }
        assertEquals(expectedResult, Collections.foldr((value1, value2) -> value2 +
                value1 % 10 + value1 / 10 % 10 + value1 / 100, 0,linkedList));
    }

}