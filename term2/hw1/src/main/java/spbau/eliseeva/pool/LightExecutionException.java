package spbau.eliseeva.pool;

/** This class is a special exception for the LightFuture interface*/
public class LightExecutionException extends Exception {
    private Exception exception;
    /** Does nothing, only thrown in needed situations. */
    public LightExecutionException(Exception e) {
        exception = e;
    }

    /** Returns the exception thrown before.*/
    @Override
    public Exception getCause() {
        return exception;
    }
}