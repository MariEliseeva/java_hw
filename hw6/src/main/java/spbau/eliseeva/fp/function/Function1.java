package spbau.eliseeva.fp.function;

/**
 * This interface is a function of one argument.
 * Classes, which implements it, should override method apply -
 * application of needed function to the value.
 * The interface have a default method compose -
 * composition of two functions.
 * @param <T> argument type
 * @param <K> returning value type
 */
public interface Function1<T, K> {

    /**
     * Takes the given value and returns the result of function application.
     * @param value argument
     * @return function value from the given argument
     */
    K apply(T value);

    /**
     * Makes new function, which is a composition of given and current functions.
     * @param g given function, takes arguments of type K
     * @param <V> result of given (and resulted) function application
     * @return composition of g and a function we call method from
     */
    default <V> Function1<T, V> compose(Function1<K, V> g) {
        return value -> g.apply(apply(value));
    }
}
