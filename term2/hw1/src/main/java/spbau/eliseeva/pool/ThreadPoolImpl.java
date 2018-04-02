package spbau.eliseeva.pool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class is a realisation of the ThreadPool. It creates
 * several threads inside and execute given tasks (made from the suppliers)
 * using multithreading.
 * @param <R> type returned by given to the ThreadPool suppliers
 */
public class ThreadPoolImpl<R> {
    /** The threads for running the tasks.*/
    private final Thread[] threads;

    /**
     * Queue with the tasks to do.
     * They are waiting for the free thread.
     */
    private final Queue<ThreadPoolTask> waitingTasks;

    /**
     * Creates a ThreadPool with needed threads.
     * Use Runnable interface implementation to execute
     * all the tasks, that will be given, with all of the threads.
     * @param threadNumber wanted number of threads
     */
    public ThreadPoolImpl(int threadNumber) {
        threads = new Thread[threadNumber];
        waitingTasks = new LinkedList<>();
        Runnable threadRunner = () -> {
            while (!Thread.interrupted()) {
                if (!waitingTasks.isEmpty()) {
                    synchronized (waitingTasks) {
                        if (!waitingTasks.isEmpty()) {
                            waitingTasks.element().run();
                            waitingTasks.remove();
                        }
                    }
                }
            }
        };
        for (int i = 0; i < threadNumber; i++) {
            threads[i] = new Thread(threadRunner);
            threads[i].start();
        }
    }

    /**
     * Returns all existing threads.
     * @return threads from the pool.
     */
    public Thread[] getThreads() {
        return threads;
    }

    /**
     * Add a task to be done.
     * @param task supplier to call
     * @return LightFuture object, which implements needed task.
     */
    public LightFuture<R> addTask(Supplier<R> task) {
        ThreadPoolTask threadPoolTask = new ThreadPoolTask(task);
        synchronized (waitingTasks) {
            waitingTasks.add(threadPoolTask);
        }
        return threadPoolTask;
    }


    /** Interrupts all the threads. */
    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    /**
     * Checks if all tasks are completed.
     * @return true if all task are completed
     */
    public boolean allReady() {
        synchronized (waitingTasks) {
            return waitingTasks.isEmpty();
        }
    }

    /** Implementation of the LightFuture interface for the ThreadPool.*/
    private class ThreadPoolTask implements LightFuture<R> {
        /** Task to do.*/
        private Supplier<R> task;

        /** Is the task completed or not.*/
        private boolean isReady;

        /** Answer to return.*/
        private R result;

        /** Was it thrown an exception or not.*/
        private boolean isException;

        /** An exception that was thrown.*/
        private Exception exception;

        /**
         * Creates a task by given Supplier.
         * @param task supplier to run inside.
         */
        ThreadPoolTask(Supplier<R> task) {
            this.task = task;
            isReady = false;
            isException = false;
        }

        /**
         * Checks if the task completed or not.
         * @return true if the task completed
         */
        @Override
        public boolean isReady() {
            return isReady;
        }

        /** Calls the supplier, fixing a result.*/
        private void run() {
            if (!isReady) {
                synchronized (this) {
                    if (!isReady) {
                        isReady = true;
                        try {
                            result = task.get();
                        } catch (Exception e) {
                            isException = true;
                            exception = e;
                        }
                    }
                    notifyAll();
                }
            }
        }

        /**
         * Wait for the supplier to be called and return the result
         * @return result of the supplier calling
         * @throws LightExecutionException thrown if supplier did something wrong
         */
        @Override
        public R get() throws LightExecutionException {
           try {
               if (!isReady) {
                   synchronized (this) {
                       while (!isReady) {
                           wait();
                       }
                   }
               }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            if (isException) {
                throw new LightExecutionException(exception);
            }

            return result;
        }

        /**
         * Creates one more task, which takes existing
         * and apply a given function to the result.
         * @param function function to apply to the result
         * @return LightFuture object, implementing the new task
         */
        @Override
        public LightFuture<R> thenApply(Function<R, R> function) {
            return ThreadPoolImpl.this.addTask(() -> {
                try {
                    return function.apply(ThreadPoolTask.this.get());
                } catch (LightExecutionException e) {
                    System.out.println(e.getMessage());
                }
                return null;
            });
        }
    }
}
