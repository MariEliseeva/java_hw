package spbau.eliseeva.maybe;

import java.util.function.Function;

/**
 * The class is a container of one object - Maybe.
 * If an object's value exists, the container saves the value,
 * if not - the container saves null. To create new object
 * used methods just and nothing - for existing values
 * and for not existing. There is also isPresent method
 * to find out if the object's value exists or not.
 * @param <T> type of object's value
 */
public class Maybe <T> {
    /** The value of the object. Null if nothing.*/
    private final T object;

    private Maybe(T t) {
        object = t;
    }

    /**
     * Creates a new object by given value.
     * Calls private constructor inside.
     * @param t value to save
     * @param <T> type of object's value
     * @return new Maybe object - just
     */
    public static <T> Maybe<T> just(T t) {
        return new Maybe<>(t);
    }

    /**
     * Creates an object with non-existent value.
     * Calls private constructor inside.
     * @param <T> type of object's value (which should if be, if exists)
     * @return new Maybe object - nothing
     */
    public static <T> Maybe<T> nothing() {
        return new Maybe<>(null);
    }

    /**
     * Returns object's value if it exists. If not - throws a special exception.
     * @return object's value
     * @throws NullObjectException
     */
    public T get() throws NullObjectException {
        if (object != null) {
            return object;
        }
        throw new NullObjectException();
    }

    /**
     * Check if the value of object exists or not.
     * @return true if the value exists, false if not
     */
    public boolean isPresent() {
        return object != null;
    }

    /**
     * Returns new Maybe object with the value, appeared as a result of
     * applying the given function. If there were not value - returns nothing.
     * @param mapper function to apply
     * @param <U> type of new Maybe object
     * @return new Maybe object
     */
    public <U> Maybe<U> map(Function<T, U> mapper) {
        return isPresent() ? just(mapper.apply(object)) : nothing();
    }
}
