package spbau.eliseeva.fp.function;

import org.jetbrains.annotations.NotNull;

/**
 * This interface is a function of two arguments.
 * All classes, which implements it, should override
 * method apply - application of function to arguments.
 * The interface has 4 default methods - composition
 * with one-argument function, binding one of arguments and currying.
 * @param <T> type of first argument
 * @param <V> type of second argument
 * @param <K> type of the resul of function application
 */
public interface Function2<T, V, K> {
    /**
     * Applies function to it's arguments and returns the result.
     * @param value1 value of the first argument
     * @param value2 value of the second argument
     * @return the result of function application
     */
    K apply(T value1, V value2);

    /**
     * Takes one-argument function and returns new function -
     * composition of current and given one-argument.
     * @param g given function
     * @param <P> type of returning value (of given and of the resulted function)
     * @return composition of current and given functions
     */
    default @NotNull <P> Function2<T, V, P> compose(@NotNull Function1<? super K, ? extends P> g) {
        return (value1, value2) -> g.apply(apply(value1, value2));
    }

    /**
     * Make one-argument function by putting
     * the given value instead of the first
     * argument of the current two-argument function.
     * @param value1 value which is put on the first argument's place
     * @return resulted function
     */
    default @NotNull Function1<V, K> bind1(T value1) {
        return value2 -> apply(value1, value2);
    }

    /**
     * Make one-argument function by putting
     * the given value instead of the second
     * argument of the current two-argument function.
     * @param value2 value which is put on the second argument's place
     * @return resulted function
     */
    default @NotNull Function1<T, K> bind2(V value2) {
        return value1 -> apply(value1, value2);
    }

    /**
     * Make from the current function a function of one argument
     * which return-value is also a function.
     * @return function, which returns function
     */
    default @NotNull Function1<T, Function1<V, K>> curry() {
        return this::bind1;
    }
}
