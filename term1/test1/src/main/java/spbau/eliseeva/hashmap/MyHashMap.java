package spbau.eliseeva.hashmap;

import java.util.*;

public class MyHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

    private LinkedList<K> keys = new LinkedList<>();
    private LinkedList<V> values = new LinkedList<>();

    private int size = 0;

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new MyEntrySet(keys, values);
    }

    public boolean containsKey(Object key) {
        return keys.contains((K) key);
    }

    public boolean containsValue(Object value) {
        return values.contains((V) value);
    }

    public V get(Object key) {
        return values.find(keys.findVal((K) key));
    }

    public V Put(Object key, Object value) {
        V returnValue = null;
        if (containsKey(key)) {
            returnValue = get(key);
            values.removeKey(keys.removeValue((K)key));
        }
        values.pushBack((V) value);
        keys.pushBack((K) keys);

        return returnValue;
    }

    public static class MyEntry<K, V> implements Entry {
        K key;
        V value;
        MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(Object newValue) {
            V oldValue = value;
            value = (V) newValue;
            return oldValue;
        }
    }

    public static class MyEntrySet<K, V> extends AbstractSet<MyEntry<K, V>> {
        int size = 0;
        LinkedList<K> keys;
        LinkedList<V> values;

        MyEntrySet(LinkedList<K> keys, LinkedList<V> values) {
            this.keys = keys;
            this.values = values;
        }
        @Override
        public Iterator<MyEntry<K, V>> iterator() {
            return new MyIterator(keys, values);
        }

        @Override
        public int size() {
            return size;
        }

        public static class MyIterator<K, V> implements Iterator<MyEntry<K, V>> {

            LinkedList<K> keys;
            LinkedList<V> values;
            int currentKey = 0;

            public MyIterator(LinkedList<K> keys, LinkedList<V> values) {
                this.keys = keys;
                this.values = values;
            }

            @Override
            public boolean hasNext() {
                return currentKey != keys.size();
            }

            @Override
            public MyEntry<K, V> next() {
                currentKey++;
                return new MyEntry<>(keys.find(currentKey - 1), values.find(currentKey - 1));
            }
        }
    }
}