package spbau.eliseeva.fp.function;

/**
 * This interface is predicate - one-argument function,
 * which returns true or false.
 * @param <K> type of the argument
 */
public interface Predicate<K> extends Function1<K, Boolean> {
    /**
     * Make new predicate as a result of current and given,
     * connected with 'or'.
     * @param predicate given predicate
     * @return resultede predicate
     */
    default Predicate<K> or(Predicate<K> predicate) {
        return value -> Predicate.this.apply(value) || predicate.apply(value);
    }

    /**
     * Make new predicate as a result of current and given,
     * connected with 'and'.
     * @param predicate given predicate
     * @return resultede predicate
     */
    default Predicate<K> and(Predicate<K> predicate) {
        return value -> Predicate.this.apply(value) && predicate.apply(value);
    }

    /**
     * Make new predicate as a result applying 'not' to current.
     * @return resulted predicate
     */
    default Predicate<K> not() {
        return value -> !apply(value);
    }

    /**
     * Makes the predicate, which is constantly true.
     * @return always true predicate
     */
    default Predicate<K> ALWAYS_TRUE() {
        return value -> true;
    }

    /**
     * Makes the predicate, which is constantly false.
     * @return always false predicate
     */
    default Predicate<K> ALWAYS_FALSE() {
        return value -> false;
    }
}
