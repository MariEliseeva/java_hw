package spbau.eliseeva.lazy;

import java.util.function.Supplier;

/**
 * Creates two types of Lazy objects: one very simple, looking like usual Singleton,
 * other -- for multi-threaded calculation.
 * @param <T> type of the expression calculated inside the Lazy objects
 */
public class LazyFactory<T> {
    /**
     * Creates simple one-threaded Lazy object.
     * @param supplier supplier to use in the Lazy object
     * @param <T> type of the expression calculated inside the Lazy objects
     * @return one-threaded Lazy object
     */
    public static <T> Lazy<T> createEasyLazy(Supplier<T> supplier) {
        return new EasyLazy<>(supplier);
    }

    /**
     * Creates multi-threaded Lazy object, saving one-time calling of the supplier.
     * @param supplier supplier to use in the Lazy object
     * @param <T> type of the expression calculated inside the Lazy objects
     * @return multi-threaded Lazy object
     */
    public static <T> Lazy<T> createHardLazy(Supplier<T> supplier) {
        return new HardLazy<>(supplier);
    }

    /**
     * The class is a one-threaded Lazy implementation.
     * @param <T> type of the expression calculated inside
     */
    private static class EasyLazy<T> implements Lazy<T> {
        /**
         * supplier to create returned objects, should be called once
         */
        private Supplier<T> supplier;

        /**
         * The result of the supplier calling.
         */
        private T value;

        /**
         * Creates an object by the given supplier.
         * @param supplier supplier to create an object from
         */
        EasyLazy(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        /**
         * Calculate the expression if it is the first call,
         * only gives the answer in other calls
         * @return answer for the saved inside expression
         */
        @Override
        public T get() {
            if (supplier != null) {
                value = supplier.get();
                supplier = null;
            }
            return value;
        }
    }

    /**
     * The class is a multi-threaded Lazy implementation.
     * @param <T> type of the expression calculated inside
     */
    private static class HardLazy<T> implements Lazy<T> {
        /**
         * supplier to create returned objects, should be called once
         */
        private Supplier <T> supplier;

        /**
         * The result of the supplier calling.
         */
        private T value;

        /**
         * Creates an object by the given supplier.
         * @param supplier supplier to create an object from
         */
        HardLazy(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        /**
         * Calculate the expression if it is the first call,
         * only gives the answer in other calls
         * @return answer for the saved inside expression
         */
        @Override
        public T get() {
            synchronized (this) {
                if (supplier != null) {
                    value = supplier.get();
                    supplier = null;
                }
            }
            return value;
        }
    }
}
