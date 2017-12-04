package spbau.eliseeva.treeset;

import java.util.*;

/**
 * The class realizes MyTreeSet interface -- container,
 * which collects it's element in a tree, saving their order.
 * @param <E> type of elements in container
 */
public class TreeSet<E> extends AbstractSet<E> implements MyTreeSet<E> {
    /** Binary tree to collect data.*/
    private BinaryTree<E> binaryTree;

    /**
     * Empty constructor, we should compare elements
     * in an usual way.
     */
    public TreeSet() {
        binaryTree = new BinaryTree<>();
    }

    /**
     * Constructor with a given comparator,
     * which should be used to compare elements.
     * @param comparator comparator -- how to compare elements
     */
    public TreeSet(Comparator<? super E> comparator) {
        binaryTree = new BinaryTree<>(comparator);
    }

    /**
     * Checks if an element is in a container or not.
     * @param o element to find if it is in or not
     * @return found or not
     */
    @Override
    public boolean contains(Object o) {
        return binaryTree.contains (o);
    }

    /**
     * Adds an element in a tree.
     * @param e element to add
     * @return true if an element was added, false if already exists
     */
    public boolean add(E e) {
        return binaryTree.add(e);
    }

    /**
     * Removes element from the tree.
     * @param e element to remove
     * @return true if element was removed, false if doesn't exist
     */
    @Override
    public boolean remove(Object e) {
        return binaryTree.remove(e);
    }

    /**
     * Returns iterator of the TreeSet
     * @return TreeSet iterator
     */
    @Override
    public Iterator<E> iterator() {
        return binaryTree.iterator();
    }

    /**
     * Returns number of elements in the container
     * @return number of elements in the container
     */
    @Override
    public int size() {
        return binaryTree.size();
    }

    /**
     * Returns iterator of the TreeSet, which goes from the end to the begin.
     * @return iterator, going from the end to the begin
     */
    @Override
    public Iterator<E> descendingIterator() {
        return binaryTree.descendingIterator();
    }

    /**
     * Returns the set, which is the opposite of current --
     * all left subtrees are right now and all right are left.
     * @return set, which is the opposite of current.
     */
    @Override
    public MyTreeSet<E> descendingSet() {
        return new DescendingSet();
    }

    /**
     * Gives the "smallest" element from the container
     * @return the "smallest" element from the container
     */
    @Override
    public E first() {
        return binaryTree.first();
    }

    /**
     * Gives the "biggest" element from the container
     * @return the "biggest" element from the container
     */
    @Override
    public E last() {
        return binaryTree.last();
    }

    /**
     * Return the greatest element which is less then given.
     * @param e element to find lower for
     * @return the greatest element which is less then given
     */

    @Override
    public E lower(E e) {
        return binaryTree.lower(e);
    }

    /**
     * Return the greatest element which is less or equal to given.
     * @param e element to find floor for
     * @return the greatest element which is less or equal to given
     */
    @Override
    public E floor(E e) {
        return binaryTree.floor(e);
    }

    /**
     * Return the least element which is greater or equal to given.
     * @param e element to find ceiling for
     * @return the least element which is greater or equal to given
     */
    @Override
    public E ceiling(E e) {
        return binaryTree.ceiling(e);
    }


    /**
     * Return the least element which is greater than given.
     * @param e element to find ceiling for
     * @return the least element which is greater than given
     */
    @Override
    public E higher(E e) {
        return binaryTree.higher(e);
    }

    /**
     * TreeSet which is the opposite of current - all
     * left subtrees are right and all right - left.
     * All the methods have the opposite meaning, like all
     * comparator's results are with "-" sigh.
     */
    private class DescendingSet extends AbstractSet<E> implements MyTreeSet<E> {
        @Override
        public Iterator<E> iterator() {
            return TreeSet.this.descendingIterator();
        }

        @Override
        public int size() {
            return TreeSet.this.size();
        }

        @Override
        public Iterator<E> descendingIterator() {
            return TreeSet.this.iterator();
        }

        @Override
        public MyTreeSet<E> descendingSet() {
            return TreeSet.this;
        }

        @Override
        public E first() {
            return TreeSet.this.last();
        }

        @Override
        public E last() {
            return TreeSet.this.first();
        }

        @Override
        public E lower(E e) {
            return TreeSet.this.higher(e);
        }

        @Override
        public E floor(E e) {
            return TreeSet.this.ceiling(e);
        }

        @Override
        public E ceiling(E e) {
            return TreeSet.this.floor(e);
        }

        @Override
        public E higher(E e) {
            return TreeSet.this.lower(e);
        }
    }
}