package spbau.eliseeva.hashmap;

public class LinkedList <T> {
    private class Node <T>{
        private Node next = null;
        private Node previous = null;
        private int key;
        private T value;

        private Node(T newValue, int newKey) {
            value = newValue;
            key = newKey;
        }

        private Node() {
        }
    }

    private Node head;

    private Node tail;

    private int size = 0;

    public int size() {
        return size;
    }

    public LinkedList() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.previous = head;
    }

    public void pushBack( T newValue) {
        Node node = new Node(newValue, size);
        node.previous = tail.previous;
        node.next = tail;
        node.previous.next = node;
        node.next.previous = node;
        size++;
    }

    public void removeFront() {
        head.next.next.previous = head;
        head.next = head.next.next;
        size--;
    }

    public T getFrontValue() {
        return (T)head.next.value;
    }

    public int getFrontKey() {
        return head.next.key;
    }

    public boolean contains(T findValue) {
        for (Node node = head.next; node != tail; node = node.next) {
            if (node.value.equals(findValue)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsKey(int findKey) {
        for (Node node = head.next; node != tail; node = node.next) {
            if (node.key == findKey) {
                return true;
            }
        }
        return false;
    }


    public void removeKey(int key) {
        for (Node node = head.next; node != tail; node = node.next) {
            if (node.key == key) {
                size--;
                node.next.previous = node.previous;
                node.previous.next = node.next;
            }
        }
    }

    public int removeValue(T value) {
        for (Node node = head.next; node != tail; node = node.next) {
            if (node.value.equals(value)) {
                size--;
                int returnKey = node.key;
                node.next.previous = node.previous;
                node.previous.next = node.next;
                return returnKey;
            }
        }
        return -1;
    }

    public T find(int findKey) {
        for (Node node = head.next; node != tail; node = node.next) {
            if (node.key == findKey) {
                return (T)node.value;
            }
        }
        return null;
    }

    public int findVal(T findValue) {
        for (Node node = head.next; node != tail; node = node.next) {
            if (node.value.equals(findValue)) {
                return node.key;
            }
        }
        return -1;
    }

    /** Удаляет все элементы списка. */
    public void clear() {
        head.next = tail;
        tail.previous = head;
        size = 0;
    }
}