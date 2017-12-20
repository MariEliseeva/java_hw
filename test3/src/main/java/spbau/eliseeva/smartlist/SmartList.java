package spbau.eliseeva.smartlist;

import java.lang.reflect.Array;
import java.util.*;

/**
 * The class is an ordered container for elements, which is optimized for
 * collection with less than 5 elements. If you want to collect more
 * -- it is used just ArrayList inside.
 * @param <E> type of elements in the container
 */
public class SmartList<E> extends AbstractList<E> implements List<E>  {
    /** Number of elements in the container */
    private int size = 0;

    /** Data which we are saving.
     *  It can be null, element of type E, array with 5 elements
     *  or ArrayList
     */
    private Object data = null;

    /** Creates container without elements. */
    public SmartList() {
    }

    /**
     * Creates container and adds all the element from the given collection/
     * @param collection elements to add to SmartList
     */
    public SmartList(Collection<? extends E> collection) {
        for (E elem : collection) {
            add(elem);
        }
    }

    /**
     * Element by number.
     * @param i index
     * @return element of this index
     */
    @Override
    public E get(int i) {
        if (i >= size) {
            throw new NoSuchElementException();
        }
        if (size == 1) {
            return (E) data;
        } else if (size >= 2 && size <= 5) {
            E[] array = (E[]) data;
            return array[i];
        } else {
            ArrayList<E> arrayList = (ArrayList<E>) data;
            return arrayList.get(i);
        }
    }

    /**
     * Change value of element by given index
     * @param i index to change
     * @param e value to set
     * @return previous value of this index
     */
    @Override
    public Object set(int i, Object e) {
        Object res;
        if (i >= size || i < 0) {
            throw new NoSuchElementException();
        } else if (size == 1) {
            res = data;
            data = e;
        } else if (size >= 2 && size <= 5) {
            E[] array = (E[]) data;
            res = array[i];
            array[i] = (E) e;
        } else {
            ArrayList<E> arrayList = (ArrayList<E>) data;
            return arrayList.set(i, (E) e);
        }
        return res;
    }

    /**
     * Adds element in the end of the list.
     * @param elem element to add
     * @return true if already have the element (but still add one more copy), false if not.
     */
    @Override
    public boolean add(E elem) {
        boolean ans =  !contains(elem);
        if (size == 0) {
            data = elem;
        } else if (size == 1) {
            E[] array = (E[]) Array.newInstance(elem.getClass(), 5);
            array[0] = (E) data;
            array[1] = elem;
            data = array;
        } else if (size >= 2 && size <= 4) {
            E[] array = (E[]) data;
            array[size] = elem;
            data = array;
        } else if (size == 5) {
            ArrayList<E> arrayList = new ArrayList<>();
            E[] array = (E[]) data;
            arrayList.addAll(Arrays.asList(array));
            arrayList.add(elem);
            data = arrayList;
        } else {
            ArrayList<E> arrayList = (ArrayList<E>) data;
            arrayList.add(elem);
            data = arrayList;
        }
        size++;
        return ans;
    }

    /**
     * Checks if an element is in the given list
     * @param e element to find
     * @return true if found
     */
    @Override
    public boolean contains(Object e){
        if (size == 0) {
            return false;
        } else if (size == 1) {
            return data.equals(e);
        } else if (size >= 2 && size <= 5) {
            E[] array = (E[]) data;
            for (int i = 0; i < size; i++) {
                if (array[i].equals(e)) {
                    return true;
                }
            }
            return false;
        } else {
            ArrayList<E> arrayList = (ArrayList<E>) data;
            return arrayList.contains(e);
        }
    }

    /**
     * Delete element by index
     * @param i index of element to remove
     * @return removed element
     */
    @Override
    public E remove(int i) {
        if (i >= size) {
            throw new NoSuchElementException();
        }
        E res;
        if (size == 1) {
            res = (E) data;
            data = null;
        } else if (size == 2) {
            E[] array = (E[]) data;
            res = array[i];
            if (i == 0) {
                data = array[1];
            } else {
                data = array[0];
            }
        } else if (size >= 3 && size <= 5) {
            E[] array = (E[]) data;
            res = array[i];
            for (int j = i; j < 4; j++) {
                array[j] = array[j + 1];
            }
            array[4] = null;
        } else if (size == 6) {
            ArrayList<E> arrayList = (ArrayList<E>) data;
            res = arrayList.remove(i);
            E[] array = (E[]) Array.newInstance(arrayList.get(0).getClass(), 5);
            for (int j = 0; j < 5; j++) {
                array[j] = arrayList.get(j);
            }
            data = array;
        } else {
            ArrayList<E> arrayList = (ArrayList<E>) data;
            res = arrayList.remove(i);
            data = arrayList;
        }
        size--;
        return res;
    }

    /**
     * Returns number of elements in the collection
     * @return number of elements in the collection
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns an iterator of the list.
     * @return iterator of the list.
     */
    @Override
    public Iterator<E> iterator() {
        return new MySmartListIterator();
    }

    /**
     * Iterator inside smart list. If > 5 elements -- ArrayList iteration,
     * else --written realisation for an array.
     */
    private class MySmartListIterator implements Iterator<E> {
        /** Current position of the iterator */
        private int curNumber;

        /** Removed element before iterator or not.*/
        private boolean ifRemoved = true;

        /**
         * Tells if there are more elements after the position of the iterator
         * @return true if there are more elements after the position of the iterator
         */
        @Override
        public boolean hasNext() {
            if (size == 0) {
                return false;
            } else if (size >= 1 && size <= 5) {
                return (curNumber < size);
            } else {
                return ((ArrayList<E>) data).iterator().hasNext();
            }
        }

        /**
         * Goes to next position.
         * @return element we have come through
         */
        @Override
        public E next() {
            curNumber++;
            if (size == 0) {
                throw new NoSuchElementException();
            } else if (size >= 1 && size <= 5) {
                ifRemoved = false;
                return get(curNumber - 1);
            } else {
                return ((ArrayList<E>) data).iterator().next();
            }
        }

        /** Removes element before the current position*/
        @Override
        public void remove() {
            if (size <= 5) {
                if (ifRemoved) {
                    throw new IllegalStateException();
                } else {
                    SmartList.this.remove(curNumber - 1);
                    curNumber--;
                    ifRemoved = true;
                }
            } else {
                ((ArrayList<E>) data).iterator().remove();
            }
        }
    }
}