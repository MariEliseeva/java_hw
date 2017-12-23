package spbau.eliseeva.mock;

/**
 * Interface stack -- data structure where you can add elements
 * in the end and get or remove elements from the end.
 * @param <E> type of elements in stack
 */
public interface Stack<E> {
    /**
     * Checks if stack is empty or not.
     * @return true is stack is empty, false if not
     */
    boolean isEmpty();

    /**
     * Adds new element to the stack.
     * @param element element to add
     */
    void push(E element);

    /** Deletes last element from stack */
    void pop();

    /**
     * Returns last element in stack
     * @return last element
     */
    E top();

    /** Makes the stack empty*/
    void clear();
}
