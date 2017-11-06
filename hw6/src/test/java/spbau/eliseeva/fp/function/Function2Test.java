package spbau.eliseeva.fp.function;

import org.junit.Test;

import static org.junit.Assert.*;

/** Tests all default methods of Function2 interface. */
public class Function2Test {
    /**
     * First function increase the integer value on 11,
     * second return the difference between string length.
     * Checks that compositions if correct.
     */
    @Test
    public void composeTest() {
        class MyFunction1 implements Function1<Integer, Integer> {
            @Override
            public Integer apply(Integer value) {
                return value + 11;
            }
        }

        class MyFunction2 implements Function2<String, String, Integer> {
            @Override
            public Integer apply(String value1, String value2) {
                return value1.length() - value2.length();
            }
        }
        assertEquals(new Integer(10), new MyFunction2().compose(new MyFunction1()).apply("AAbC", "12345"));
        assertEquals(new Integer(11), new MyFunction2().compose(new MyFunction1()).apply("", ""));
    }

    /**
     * The function takes integer x and y, return 5x-3y.
     * Check that we can replace first argument with a constant value and
     * have a correct one-argument function.
     */
    @Test
    public void bind1Test() {
        class MyFunction2 implements Function2<Integer, Integer, Integer> {
            @Override
            public Integer apply(Integer value1, Integer value2) {
                return value1 * 5 - value2 * 3;
            }
        }
        assertEquals(new Integer(-6), new MyFunction2().bind1(0).apply(2));
        assertEquals(new Integer(7), new MyFunction2().bind1(2).apply(1));
    }

    /** Same checking, as in previous test, but for another argument. */
    @Test
    public void bind2Test() {
        class MyFunction2 implements Function2<Integer, Integer, Integer> {
            @Override
            public Integer apply(Integer value1, Integer value2) {
                return value1 * 5 - value2 * 3;
            }
        }
        assertEquals(new Integer(10), new MyFunction2().bind2(0).apply(2));
        assertEquals(new Integer(-1), new MyFunction2().bind2(2).apply(1));
    }

    /**
     * Checks if we can make a function, returning function -
     * apply to value, and because the result must be function again -
     * apply to another value.
     */
    @Test
    public void curryTest() {
        class MyFunction2 implements Function2<Integer, String, Integer> {
            @Override
            public Integer apply(Integer value1, String value2) {
                return value1 * 2 - value2.length();
            }
        }
        assertEquals(new Integer(47), new MyFunction2().curry().apply("aaa").apply(25));
        assertEquals(new Integer(25), new MyFunction2().curry().apply("aaccbbdde").apply(17));
    }

}