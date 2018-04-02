package spbau.eliseeva.pool;

import java.util.function.Function;

/**
 * This interface is an object with a task inside it,
 * which we can wait to be ready, get its result and create new
 * task with special method.
 * @param <R> type of the returned answer
 */
public interface LightFuture<R> {
    /**
     * Checks if the task completed or not.
     * @return true if the task completed
     */
    boolean isReady();

    /**
     * Wait for the supplier to be called and return the result
     * @return result of the supplier calling
     * @throws LightExecutionException thrown if supplier did something wrong
     */
    R get() throws LightExecutionException;

    /**
     * Creates one more task, which takes existing
     * and apply a given function to the result.
     * @param function function to apply to the result
     * @return LightFuture object, implementing the new task
     */
    LightFuture<R> thenApply(Function<R, R> function);
}
