package spbau.eliseeva.fp.function;

import org.junit.Test;

import static org.junit.Assert.*;

/** The class tests predicate interface. */
public class PredicateTest {
    /**
     * Creates two predicate and checks if they
     * are correctly connecting with 'or'.
     */
    @Test
    public void orTest() {
        class firstPredicate implements Predicate<Integer> {
            @Override
            public Boolean apply(Integer value) {
                return (value % 2 == 0);
            }
        }

        class secondPredicate implements Predicate<Integer> {
            @Override
            public Boolean apply(Integer value) {
                return (value % 3 == 0);
            }
        }
        assertEquals(true, new firstPredicate().or(new secondPredicate()).apply(216));
        assertEquals(true, new firstPredicate().or(new secondPredicate()).apply(256));
    }

    /**
     * Creates two predicate and checks if they
     * are correctly connecting with 'and'.
     */
    @Test
    public void andTest() {
        class firstPredicate implements Predicate<Integer> {
            @Override
            public Boolean apply(Integer value) {
                return (value % 2 == 0);
            }
        }

        class secondPredicate implements Predicate<Integer> {
            @Override
            public Boolean apply(Integer value) {
                return (value % 3 == 0);
            }
        }
        assertEquals(true, new firstPredicate().and(new secondPredicate()).apply(216));
        assertEquals(false, new firstPredicate().and(new secondPredicate()).apply(256));
    }

    /** Creates the predicate and apply 'not' to it. */
    @Test
    public void notTest() {
        class MyPredicate implements Predicate<String> {

            @Override
            public Boolean apply(String value) {
                return (value.length() == 5);
            }
        }
        assertEquals(true, new MyPredicate().not().apply("ABCD"));
        assertEquals(false, new MyPredicate().not().apply("ABCED"));
    }

    /** Checks always-true predicate is really always true.*/
    @Test
    public void ALWAYS_TRUETest() {
        class MyPredicate implements Predicate<String> {
            @Override
            public Boolean apply(String value) {
                return false;
            }
        }
        assertEquals(true, new MyPredicate().ALWAYS_TRUE().apply("125"));
        assertEquals(true, new MyPredicate().ALWAYS_TRUE().apply("A"));
    }

    /** Checks always-false predicate is really always false.*/
    @Test
    public void ALWAYS_FALSETest() {
        class MyPredicate implements Predicate<Integer> {
            @Override
            public Boolean apply(Integer value) {
                return (value == 7);
            }
        }
        assertEquals(false, new MyPredicate().ALWAYS_FALSE().apply(125));
        assertEquals(false, new MyPredicate().ALWAYS_FALSE().apply(7));
    }
}