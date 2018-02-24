package spbau.eliseeva.lazy;

/**
 * The interface represents lazy calculation. First call of the method "get"
 *  calculates the object, all other calls only return the answer.
 * @param <T> type of the expression calculated inside
 */
public interface Lazy<T> {
    /**
     * Calculate the expression if it is the first call,
     * only gives the answer in other calls
     * @return answer for the saved inside expression
     */
    T get();
}
