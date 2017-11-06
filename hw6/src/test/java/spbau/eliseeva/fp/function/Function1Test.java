package spbau.eliseeva.fp.function;

import org.junit.Test;

import static org.junit.Assert.*;

/** The class tests function1 interface - it's the only default method compose. */
public class Function1Test {
    /**
     * Makes two functions - returning (*3) integer argument
     * and (+2) to length of the string.
     * Checks that composition is correct.
     */
    @Test
    public void composeTest() {
        class firstFunction1 implements Function1<Integer, Integer> {
            @Override
            public Integer apply(Integer value) {
                return value * 3;
            }
        }

        class secondFunction1 implements Function1<String, Integer> {
            @Override
            public Integer apply(String value) {
                return value.length() + 2;
            }
        }
        assertEquals(new Integer(18), new secondFunction1().compose(new firstFunction1()).apply("AAbC"));
        assertEquals(new Integer(6), new secondFunction1().compose(new firstFunction1()).apply(""));
    }
}