package spbau.eliseeva.lazy;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The class tests the LazyFactory -- class for creating
 * two types of the Lazy objects.
 */
public class LazyFactoryTest {
    /**
     * We need to know how many times did we call the supplier in our tests.
     */
    private int supplierCalls;

    /**
     * Checks if the supplier works correct even two times without breaking.
     */
    @Test
    public void TestCallSupplier() {
        Lazy<String> lazy = LazyFactory.createSimpleLazy(() -> "AAA");
        assertEquals("AAA", lazy.get());
        assertEquals("AAA", lazy.get());
    }

    /**
     * Checks if everything works as expected with null-returning supplier.
     */
    @Test
    public void TestNullReturningSupplier() {
        Lazy<String> lazy = LazyFactory.createSimpleLazy(() -> null);
        assertNull(lazy.get());
    }

    /**
     * Checks if null-supplier do not break everything.
     */
    @Test
    public void TestNullSupplier() {
        Lazy<String> lazy = LazyFactory.createSimpleLazy(null);
        assertNull(lazy.get());
    }

    /**
     * Checks if the creation of the object do not call the supplier
     * without the "get" calls.
     */
    @Test
    public void TestNoSupplierCalls() {
        supplierCalls = 0;
        LazyFactory.createSimpleLazy(() -> {
            supplierCalls++;
            return "AAA";
        });
        assertEquals(0, supplierCalls);
    }

    /**
     * Checks that the supplier is called only once.
     */
    @Test
    public void TestManySupplierCalls() {
        supplierCalls = 0;
        Lazy<String> lazy = LazyFactory.createSimpleLazy(() -> {
            supplierCalls++;
            return "AAA";
        });
        for (int i = 0; i < 100; i++) {
            lazy.get();
        }
        assertEquals(1, supplierCalls);
    }

    /**
     * Checks if the supplier is called once even from different threads
     * and with sleeping inside of the supplier.
     * @throws InterruptedException thrown when something with threads goes wrong
     */
    @Test
    public void TestCallOnceFromManyThreads() throws InterruptedException {
        Lazy<String> lazy = LazyFactory.createMultiThreadedLazy(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            supplierCalls++;
            return "ABC";
        });
        Thread[] threads = new Thread[100];
        String[] results = new String[100];
        for (int i = 0; i < threads.length; i++) {
            final int index = i;
            threads[i] = new Thread(() -> results[index] = lazy.get());
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        assertEquals(1, supplierCalls);
        for (String result : results) {
            assertEquals("ABC", result);
        }
    }
}