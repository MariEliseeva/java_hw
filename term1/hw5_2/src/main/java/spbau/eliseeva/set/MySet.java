package spbau.eliseeva.set;

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
    private boolean contains(T t, Node curNode) {
        return curNode != null && (t.equals(curNode.value) ||
                contains(t, curNode.left) || contains(t, curNode.right));
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
     */
    private void add(T t, Node curNode) {
        if (t.compareTo(curNode.value) < 0) {
            if (curNode.left == null) {
                curNode.left = new Node(t);
            } else {
                add(t, curNode.left);
            }
        } else {
            if (curNode.right == null) {
                curNode.right = new Node(t);
            } else {
                add(t, curNode.right);
            }
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
        if (root == null) {
            root = new Node(t);
        } else {
            add(t, root);
        }
        size++;
        return true;
    }
}
