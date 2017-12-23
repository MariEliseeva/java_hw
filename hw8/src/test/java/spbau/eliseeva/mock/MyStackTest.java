package spbau.eliseeva.mock;

import org.junit.Test;

import static org.junit.Assert.*;

/** Tests methods of MyStack class.*/
public class MyStackTest {
    /** Pushes and pops elements, checking is size is still correct*/
    @Test
    public void isEmptyTest() {
        MyStack<Integer> stack = new MyStack<>();
        assertEquals(true, stack.isEmpty());
        stack.push(12);
        assertEquals(false, stack.isEmpty());
        stack.pop();
        assertEquals(true, stack.isEmpty());
    }

    /** Pushes elements and checks if they are on the top now */
    @Test
    public void pushTopTest() {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(1);
        assertEquals(new Integer(1), stack.top());
        stack.push(2);
        assertEquals(new Integer(2), stack.top());
        stack.push(3);
        assertEquals(new Integer(3), stack.top());
    }

    /** Pops elements and checks if the previous are on the top now */
    @Test
    public void popTopTest() {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.pop();
        assertEquals(new Integer(3), stack.top());
        stack.pop();
        assertEquals(new Integer(2), stack.top());
        stack.pop();
        assertEquals(new Integer(1), stack.top());
    }

    /** Clears stack and checks its size*/
    @Test
    public void clearTest() {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.clear();
        assertEquals(true, stack.isEmpty());
    }

}