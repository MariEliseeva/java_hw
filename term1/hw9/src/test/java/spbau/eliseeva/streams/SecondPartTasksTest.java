package spbau.eliseeva.streams;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static spbau.eliseeva.streams.SecondPartTasks.*;

/** Tests methods of SecondPartTasks class.*/
public class SecondPartTasksTest {
    /**
     * Method 'findQuotes' is given an ArrayList with names of three files.
     * The method tries to find lines which contains 'Function' char sequence.
     */
    @Test
    public void testFindQuotes() {
        ArrayList<String> expectedResult = new ArrayList<>();
        expectedResult.add("Function1 — функция одного аргумента (“f(x)”)");
        expectedResult.add("Function2 — функция от двух аргументов (“f(x, y)”)");
        expectedResult.add("Function1.compose() — композиция, принимает “Function1 g”, " +
                "возвращает “g(f(x))”, например, int x = f.compose(g).apply(239);");
        expectedResult.add("Function2.compose() — композиция, принимает “Function1 g”," +
                " возвращает “g(f(x, y))”");
        expectedResult.add("Function2.bind1() — bind первого аргумента, " +
                "принимает первый аргумент, возвращает “f(_, y)”");
        expectedResult.add("Function2.bind2() — bind второго аргумента," +
                " принимает второй аргумент, возвращает “f(x, _)”");
        expectedResult.add("Function2.curry() — каррирование, конвертация " +
                "в “Function1”. Например, f = (x, y) -> x + y, f(5) = x -> x + 5");
        ArrayList<String> paths = new ArrayList<>();
        paths.add("src/test/resources/testFindQuotes1");
        paths.add("src/test/resources/testFindQuotes2");
        paths.add("src/test/resources/testFindQuotes3");
        assertEquals(expectedResult, findQuotes(paths, "Function"));
    }

    /**
     * Generates a random situation 10 times and checks that the result
     * of the experiment is not far from expected.
     */
    @Test
    public void testPiDividedBy4() {
        for (int i = 0; i < 10; i++) {
            assertEquals(true, piDividedBy4() - 3.14 * 0.5 * 0.5 <= 0.02);
        }
    }

    /**
     * There are three authors with three string each one.
     * Checks that the author with longest sun of length of all string
     * is same as expected.
     */
    @Test
    public void testFindPrinter() {
        HashMap<String, List<String>> compositions = new HashMap<>();
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("aaaaaaaaaaaaaaaaaqqaaa");
        list1.add("aaaaaaaaaaaaaaaaaaaaaaa");
        list1.add("aaaaaaaaa");
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("bcaaacccbb");
        list2.add("aaaaaab");
        list2.add("ccccccccccc");
        ArrayList<String> list3 = new ArrayList<>();
        list3.add("bajhebfwelwwwwww");
        list3.add("asdfghgfdsadfgh");
        list3.add("ASDFGHFWERR44444444qqqqqqq");
        compositions.put("a1", list1);
        compositions.put("a2", list2);
        compositions.put("a3", list3);
        assertEquals("a3", findPrinter(compositions));
    }

    /** Checks that number of each product from all the shops is counted correctly.*/
    @Test
    public void testCalculateGlobalOrder() {
        ArrayList<Map<String, Integer>> orders = new ArrayList<>();
        HashMap<String, Integer> order1 = new HashMap<>();
        order1.put("i1", 7);
        order1.put("i2", 17);
        order1.put("i3", 2);
        order1.put("i4", 1);
        HashMap<String, Integer> order2 = new HashMap<>();
        order2.put("i1", 5);
        order2.put("i3", 3);
        order2.put("i3", 11);
        order2.put("i7", 25);
        HashMap<String, Integer> order3 = new HashMap<>();
        order3.put("i1", 10);
        order3.put("i5", 4);
        HashMap<String, Integer> globalOrder = new HashMap<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        globalOrder.put("i1", 22);
        globalOrder.put("i2", 17);
        globalOrder.put("i3", 13);
        globalOrder.put("i4", 1);
        globalOrder.put("i5", 4);
        globalOrder.put("i7", 25);
        assertEquals(globalOrder, calculateGlobalOrder(orders));
    }
}