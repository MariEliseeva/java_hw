package spbau.eliseeva.pool;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class tests ThreadPool implementation and implementation of
 * LightFuture interface realised inside the ThreadPool.
 */
public class ThreadPoolTest {
    /**
     * Simply creates one-threaded pool and execute tasks with it.
     * @throws LightExecutionException thrown if suppliers are bad
     */
    @Test
    public void TestOneThreadTasks() throws LightExecutionException {
        ThreadPoolImpl<Integer> threadPool = new ThreadPoolImpl<>(1);
        LightFuture<Integer> task1 = threadPool.addTask(() -> 1);
        LightFuture<Integer> task2 = threadPool.addTask(() -> 2);
        LightFuture<Integer> task4 = task2.thenApply(x -> 2 * x);
        assertEquals(1, (Object)task1.get());
        assertEquals(2, (Object)task2.get());
        assertEquals(4, (Object)task4.get());
    }

    /**
     * Do same tasks, but with three threads.
     * @throws LightExecutionException thrown if suppliers are bad
     * @throws InterruptedException thrown if problems with threads
     */
    @Test
    public void TestThreeThreadsTasks() throws LightExecutionException, InterruptedException {
       ThreadPoolImpl<Integer> threadPool = new ThreadPoolImpl<>(3);
        LightFuture<Integer> task1 = threadPool.addTask(() -> 1);
        LightFuture<Integer> task2 = threadPool.addTask(() -> 2);
        LightFuture<Integer> task4 = task2.thenApply(x -> 2 * x);
        while (!threadPool.allReady()) {
            Thread.sleep(100);
        }
        assertEquals(1, (Object)task1.get());
        assertEquals(2, (Object)task2.get());
        assertEquals(4, (Object)task4.get());
    }

    /**
     * Tests if thenApply calls really work in correct order.
     * @throws InterruptedException thrown if problems with threads
     * @throws LightExecutionException thrown if suppliers are bad
     */
    @Test
    public void TestThenApply() throws InterruptedException, LightExecutionException {
        ThreadPoolImpl<String> threadPool = new ThreadPoolImpl<>(100);
        ArrayList<String> tasksOrder = new ArrayList<>();
        ArrayList<LightFuture<String>> tasksAdd = new ArrayList<>();
        tasksAdd.add(threadPool.addTask(() -> "1"));
        for (int i = 0; i < 100; i++) {
            tasksAdd.add(tasksAdd.get(tasksAdd.size() - 1).thenApply((x -> {
                tasksOrder.add(x + "1");
                return x + "1";
            })));
        }
        while (!threadPool.allReady()) {
            Thread.sleep(100);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(tasksAdd.get(i + 1).get(), tasksOrder.get(i));
        }
    }

    /** Tests isReady method, using "never-ending" supplier inside.*/
    @Test
    public void TestIsReady() {
        ThreadPoolImpl<String> threadPool = new ThreadPoolImpl<>(100);
        LightFuture<String> task = threadPool.addTask(() -> {
            while (true);
        });
        assertFalse(task.isReady());

    }

    /**
     * Checks if for the bad supplier the right exception is called.
     * @throws LightExecutionException always thrown, because supplier is bad.
     */
    @Test(expected = LightExecutionException.class)
    public void TestException() throws LightExecutionException {
        ThreadPoolImpl<String> threadPool = new ThreadPoolImpl<>(100);
        LightFuture<String> task = threadPool.addTask(() -> {
            throw new RuntimeException();
        });
        task.get();
    }

    /** Checks if the threads exists.*/
    @Test
    public void aliveTest() {
        ThreadPoolImpl<String> threadPool = new ThreadPoolImpl<>(100);
        for (Thread thread : threadPool.getThreads()) {
            assertTrue(thread.isAlive());
        }
    }

    /**
     * Checks if the threads does not work when interrupted.
     * @throws InterruptedException thrown if problems with threads
     */
    @Test
    public void shutdownTest() throws InterruptedException {
        ThreadPoolImpl<String> threadPool = new ThreadPoolImpl<>(100);
        threadPool.shutdown();
        Thread.sleep(10000);
        for (Thread thread : threadPool.getThreads()) {
            assertFalse(thread.isAlive());
        }
    }
}