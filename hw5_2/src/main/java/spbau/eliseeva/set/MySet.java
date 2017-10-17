package spbau.eliseeva.set;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * This class is a generic container for elements of current type.
 * Each element exists only in one copy and can't be repeated twice or more.
 * The class uses not balanced binary search tree inside. The tree consists of Nodes.
 * There are three operations - to add an element, to check if the element is in container
 * and to get the size of the container.
 * @param <T> type of values inside the container
 */
public class MySet<T extends Comparable<T>> {
    /**
     * Node is one element in the binary tree, used inside of the class.
     * Node consists of value, left child and right child.
     */
    private class Node {
        /** Node value.*/
        private T value = null;

        /** Left child, null if doesn't exist.*/
        private Node left = null;

        /** Right child, null if doesn't exist.*/
        private Node right = null;

        /** Constructor. Takes left and right children and a value,makes a Node of them.*/
        Node (Node newLeft, Node newRight, T newValue) {
            left = newLeft;
            right = newRight;
            value = newValue;
        }

        /** Constructor for Node without left and right children.*/
        Node (T newValue) {
            value = newValue;
        }
    }

    /** The root of binary search tree.*/
    private Node root = null;

    /** Number of elements in the container.*/
    private int size = 0;

    /**
     * Returns number of elements in the container.
     * @return number of elements
     */
    public int size() {
        return size;
    }

    /**
     * Checks if an element is in curNode or in it's subtree.
     * If node doesn't exist returns false, if exists -
     * compares the value with given and recursively looking in children.
     * @param t value to find
     * @param curNode Node to search in
     * @return true if an element is in Node or in it's subtree, false if not
     */
    @Contract("_, null -> false")
    private boolean contains(T t, Node curNode) {
        if (curNode == null) {
            return false;
        }
        return t.equals(curNode.value) ||
                contains(t, curNode.left) || contains(t, curNode.right);
    }

    /**
     * Finds out if a value is in the root or in it's subtree.
     * @param t value to find
     * @return true if tha value is found, false if not.
     */
    public boolean contains(T t) {
        return contains(t, root);
    }

    /**
     * Add an element in curNode's subtree, saving the BST invariant.
     * If and element is less that a value in curNode - it is added in left child,
     * if greater - in right child.
     * @param t value to add
     * @param curNode node which subtree we are adding the value to
     * @return new subtree's root
     */
    @NotNull
    private Node add(T t, Node curNode) {
        if (curNode == null) {
            return new Node(t);
        }
        if (t.compareTo(curNode.value) < 0) {
            return new Node (add(t, curNode.left), curNode.right, curNode.value);
        } else {
            return new Node (curNode.left, add(t, curNode.right), curNode.value);
        }
    }

    /**
     * Adds an element in the container, if it wasn't there yet.
     * @param t value to add
     * @return true if successfully added, false if already exists
     */
    public boolean add(T t) {
        if (contains(t)) {
            return false;
        }
        root = add(t, root);
        size++;
        return true;
    }
}
