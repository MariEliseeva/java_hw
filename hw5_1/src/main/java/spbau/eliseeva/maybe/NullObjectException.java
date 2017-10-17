package spbau.eliseeva.maybe;

/**
 * The class is a special exception for Maybe class.
 * The exception thrown when we try to call get from nothing.
 */
public class NullObjectException extends Exception {
    public NullObjectException() {
        super("Object doesn't exist.");
    }
}
