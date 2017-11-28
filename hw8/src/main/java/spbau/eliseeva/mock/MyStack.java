package spbau.eliseeva.mock;

/**
 * The class in implementation of stack -- data structure
 * where you can add elements in the end and get or remove
 * elements from the end. Realisation in similar to one-directed list --
 * nodes are elements of the stack, head is top. If you want to add --
 * new element is head, if remove -- head goes to the next element.
 * @param <E> type of elements in stack
 */
public class MyStack <E> implements Stack<E> {
    /** Top of the stack*/
    private Node head = null;

    /**
     * Checks if stack is empty or not -- head exists or not.
     * @return true is stack is empty, false if not
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Adds new element to the stack. Creates new node, pointing on
     * the head and makes this new node head.
     * @param element element to add
     */
    public void push(E element) {
        Node node = new Node(element);
        node.next = head;
        head = node;
    }

    /** Deletes last element from stack. Just go from head to the next. */
    public void pop() {
        head = head.next;
    }

    /**
     * Returns last element in stack -- head value.
     * @return last element
     */
    public E top() {
        return head.value;
    }

    /** Makes the stack empty -- head is null now*/
    public void clear() {
        head = null;
    }

    /** One element in the stack */
    private class Node {
        private Node next = null;
        private E value;
        private Node(E newValue) {
            value = newValue;
        }
    }
}