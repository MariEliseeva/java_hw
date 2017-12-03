package spbau.eliseeva.fp.collections;

import org.jetbrains.annotations.NotNull;
import spbau.eliseeva.fp.function.Function1;
import spbau.eliseeva.fp.function.Function2;
import spbau.eliseeva.fp.function.Predicate;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The class with a lot of static methods for working with iterable collections.
 */
public class Collections {
    /**
     * Apply the given function to all elements and returns a list of results.
     * @param function function to apply
     * @param array collection to work at
     * @param <T> type of elements in collection
     * @param <K> type of results
     * @return linked list of results
     */
    public static @NotNull <T, K> List<K> map(@NotNull Function1<? super T, ? extends K> function,
                       @NotNull Iterable<T> array) {
        LinkedList<K> result = new LinkedList<>();
        for (T element : array) {
            result.add(function.apply(element));
        }
        return result;
    }

    /**
     * Copy element to the list if the predicate is true.
     * @param predicate predicate to check
     * @param array collection to work at
     * @param <K> type of elements if the collection
     * @return linked list with teh results
     */
    public static @NotNull <K> List<K> filter(@NotNull Predicate<? super K> predicate,
                                              @NotNull Iterable<K> array) {
        LinkedList<K> result = new LinkedList<>();
        for (K element : array) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }

    /**
     * Copy elements one by one while the predicate is true.
     * @param predicate predicate to check
     * @param array collection to work at
     * @param <K> type of elements if the collection
     * @return linked list with teh results
     */
    public static @NotNull <K> List<K> takeWhile(@NotNull Predicate<? super K> predicate,
                                                 @NotNull Iterable<K> array) {
        LinkedList<K> result = new LinkedList<>();
        Iterator<K> iterator = array.iterator();
        K element = iterator.next();
        while (iterator.hasNext() && predicate.apply(element)) {
            result.add(element);
            element = iterator.next();
        }
        if (predicate.apply(element)) {
            result.add(element);
        }
        return result;
    }

    /**
     * Copy elements one by one while the predicate is false.
     * @param predicate predicate to check
     * @param array collection to work at
     * @param <K> type of elements if the collection
     * @return linked list with teh results
     */
    public static @NotNull <K> List<K> takeUnless(@NotNull Predicate<? super K> predicate,
                                                  @NotNull Iterable<K> array) {
       return takeWhile(predicate.not(), array);
    }

    /**
     * Apply function to the previous result and next element in the collection,
     * going from left to right.
     * @param function function to apply
     * @param value started value
     * @param array collection to work at
     * @param <T> type of element in collection
     * @param <V> type of function's result
     * @return last result of application
     */
    public static @NotNull <T, V> V foldl(@NotNull Function2<? super V, ? super T, ? extends V> function,
                                          @NotNull V value, @NotNull Iterable<T> array) {
        return foldl(function, value, array.iterator());
    }

    private static @NotNull <T, V> V foldl(@NotNull Function2<? super V, ? super T, ? extends V> function,
                                  @NotNull V value, @NotNull Iterator<T> iterator ) {
        if (iterator.hasNext()) {
            return foldl(function, function.apply(value, iterator.next()), iterator);
        } else {
            return value;
        }
    }

    /**
     * Apply function to the previous result and next element in the collection,
     * going from right to left
     * @param function function to apply
     * @param value started value
     * @param array collection to work at
     * @param <V> type of element in collection
     * @param <T> type of function's result
     * @return last result of application
     */
    public static @NotNull <T, V> T foldr(@NotNull Function2<? super V, ? super T, ? extends T> function,
                                          T value, @NotNull Iterable<V> array) {
        return foldr(function, value, array.iterator());
    }

    private static @NotNull <T, V> T foldr(@NotNull Function2<? super V, ? super T, ? extends T> function,
                                          T value, @NotNull Iterator<V> iterator ) {
        if (iterator.hasNext()) {
            return function.apply(iterator.next(), foldr(function, value, iterator));
        } else {
            return value;
        }
    }
}
