package spbau.eliseeva.treeset;

import java.util.*;

/**
 * The class realizes MyTreeSet interface -- container,
 * which collects it's element in a tree, saving their order.
 * @param <E> type of elements in container
 */
public class TreeSet<E> extends AbstractSet<E> implements MyTreeSet<E> {
    /** Number of elements if the container. */
    private int size = 0;

    /** Given comparator to compare elements with, null if was not given.*/
    private Comparator<? super E> comparator;

    /**
     * Root of the tree. In left subtree -- elements
     * with smaller value, in right -- with bigger.
     */
    private Node root = null;

    /**
     * Empty constructor, we should compare elements
     * in an usual way.
     */
    public TreeSet() {
    }

    /**
     * Constructor with a given comparator,
     * which should be used to compare elements.
     * @param comparator comparator -- how to compare elements
     */
    public TreeSet(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    /**
     * Compares to elements with comparator or in a usual way.
     * @param element1 first element
     * @param element2 second element
     * @return number < 0 if first is less, > 0 if greater, == 0 if elements are equals.
     */
    private int compare(E element1, E element2) {
        if (comparator != null) {
            return comparator.compare(element1, element2);
        }
        return ((Comparable<E>) element1).compareTo(element2);
    }

    /**
     * Checks if an element is in a container or not.
     * @param e element to find if it is in or not
     * @param curNode node in which subtree we are searching
     * @return found or not
     */
    private boolean contains(E e, Node curNode) {
        return curNode != null && (compare(e, curNode.value) == 0 ||
                (compare(e, curNode.value) < 0 ?
                        contains(e, curNode.left) : contains(e, curNode.right)));
    }

    /**
     * Checks if an element is in a container or not,
     * using private recursive function inside.
     * @param o element to find if it is in or not
     * @return found or not
     */
    @Override
    public boolean contains(Object o) {
        return contains((E)o, root);
    }

    /**
     * Adds an element in a tree. If an element is
     * less -- add to left subtree, otherwise -- to right.
     * @param e element to add
     * @param curNode node in which subtree we are adding to.
     */
    private void add(E e, Node curNode) {
        if (compare(e, curNode.value) < 0) {
            if (curNode.left == null) {
                curNode.left = new Node(e);
                curNode.left.parent = curNode;
            } else {
                add(e, curNode.left);
            }
        } else {
            if (curNode.right == null) {
                curNode.right = new Node(e);
                curNode.right.parent = curNode;
            } else {
                add(e, curNode.right);
            }
        }
    }

    /**
     * Adds an element in a tree, using private function inside.
     * @param e element to add
     * @return true if an element was added, false if already exists
     */
    public boolean add(E e) {
        if (contains(e)) {
            return false;
        }
        if (root == null) {
            root = new Node(e);
        } else {
            add(e, root);
        }
        size++;
        return true;
    }

    /**
     * Remove an element from subtree of curNode.
     * If an element is less -- remove recursively from left part,
     * if greater -- from right. If equals -- removing current node:
     * we should find the less element if the right subtree, remove it
     * and make it current node with left son -- same, right -- with
     * deleted smallest element.
     * @param e element to remove
     * @param curNode node in which subtree we are removing
     */
    private void remove(E e, Node curNode) {
        if (compare(e, curNode.value) < 0) {
            remove(e, curNode.left);
        } else if (compare(e, curNode.value) > 0) {
            remove(e, curNode.right);
        } else {
            Node newCurNode;
            if (curNode.left == null) {
                newCurNode = curNode.right;
            } else if (curNode.right == null) {
                newCurNode = curNode.left;
            } else {
                Node leftNode = curNode.right;
                newCurNode = curNode;
                while (leftNode.left != null) {
                    leftNode = leftNode.left;
                }
                remove(leftNode.value, newCurNode.right);
                newCurNode.value = leftNode.value;
            }

            if (curNode.parent == null) {
                root = newCurNode;
                return;
            }

            if (curNode.parent.left == curNode) {
                curNode.parent.left = newCurNode;
            } else {
                curNode.parent.right = newCurNode;
            }
        }
    }

    /**
     * Removes element from the tree? using private function.
     * @param e element to remove
     * @return true if element was removed, false if doesn't exist
     */
    @Override
    public boolean remove(Object e) {
        if (!contains(e)) {
            return false;
        }
        remove((E) e, root);
        size--;
        return true;
    }

    /**
     * Returns iterator of the TreeSet
     * @return TreeSet iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new MyTreeSetIterator();
    }

    /**
     * Returns number of elements in the container
     * @return number of elements in the container
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns iterator of the TreeSet, which goes from the end to the begin.
     * @return iterator, going from the end to the begin
     */
    @Override
    public Iterator<E> descendingIterator() {
        return new MyTreeSetDescendingIterator();
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
        if (root == null) {
            return null;
        }
        Node currentNode = root;
        while(currentNode.left != null) {
            currentNode = currentNode.left;
        }
        return currentNode.value;
    }

    /**
     * Gives the "biggest" element from the container
     * @return the "biggest" element from the container
     */
    @Override
    public E last() {
        if (root == null) {
            return null;
        }
        Node currentNode = root;
        while(currentNode.right != null) {
            currentNode = currentNode.right;
        }
        return currentNode.value;
    }

    /**
     * Return the greatest element which is less than given.
     * If a given element is less or equal to the current node -
     * goes to left and search there. Else search in right, if not found -
     * returns current node's value.
     * @param curNode node in which subtree we are looking at
     * @param e element to find lower for
     * @return the greatest element which is less than given
     */
    private E lower(Node curNode, E e) {
        if ((compare(e, curNode.value) <= 0)) {
            if (curNode.left == null) {
                return null;
            }
            return lower(curNode.left, e);
        } else {
            if (curNode.right == null) {
                return curNode.value;
            }
            E result = lower(curNode.right, e);
            if (result == null) {
                return curNode.value;
            }
            return result;
        }
    }

    /**
     * Return the greatest element which is less then given.
     * @param e element to find lower for
     * @return the greatest element which is less then given
     */

    @Override
    public E lower(E e) {
        if (root == null) {
            return null;
        }
        return lower(root, e);
    }

    /**
     * Return the greatest element which is less or equal to given.
     * If a given element is less than the current node -
     * goes to left and search there. Else search in right, if not found -
     * returns current node's value.
     * @param curNode node in which subtree we are looking at
     * @param e element to find floor for
     * @return the greatest element which is less or equal to given
     */
    private E floor(Node curNode, E e) {
        if ((compare(e, curNode.value) < 0)) {
            if (curNode.left == null) {
                return null;
            }
            return floor(curNode.left, e);
        } else {
            if (curNode.value == e) {
                return e;
            }
            if (curNode.right == null) {
                return curNode.value;
            }
            E result = floor(curNode.right, e);
            if (result == null) {
                return curNode.value;
            }
            return result;
        }
    }

    /**
     * Return the greatest element which is less or equal to given.
     * @param e element to find floor for
     * @return the greatest element which is less or equal to given
     */
    @Override
    public E floor(E e) {
        if (root == null) {
            return null;
        }
        return floor(root, e);
    }

    /**
     * Return the least element which is greater or equal to given.
     * If a given element is greater than the current node -
     * goes to right and search there. Else search in left, if not found -
     * returns current node's value.
     * @param curNode node in which subtree we are looking at
     * @param e element to find ceiling for
     * @return the least element which is greater or equal to given
     */
    private E ceiling(Node curNode, E e) {
        if ((compare(e, curNode.value) > 0)) {
            if (curNode.right == null) {
                return null;
            }
            return floor(curNode.right, e);
        } else {
            if (curNode.value == e) {
                return e;
            }
            if (curNode.left == null) {
                return curNode.value;
            }
            E result = floor(curNode.left, e);
            if (result == null) {
                return curNode.value;
            }
            return result;
        }
    }


    /**
     * Return the least element which is greater or equal to given.
     * @param e element to find ceiling for
     * @return the least element which is greater or equal to given
     */
    @Override
    public E ceiling(E e) {
        if (root == null) {
            return null;
        }
        return ceiling(root, e);
    }

    /**
     * Return the least element which is greater than given.
     * If a given element is greater than the current node -
     * goes to right and search there. Else search in left, if not found -
     * returns current node's value.
     * @param curNode node in which subtree we are looking at
     * @param e element to find ceiling for
     * @return the least element which is greater than given
     */
    private E higher(Node curNode, E e) {
        if ((compare(e, curNode.value) >= 0)) {
            if (curNode.right == null) {
                return null;
            }
            return higher(curNode.right, e);
        } else {
            if (curNode.left == null) {
                if (compare(curNode.value, e) == 0) {
                    return null;
                }
                return curNode.value;
            }
            E result = higher(curNode.left, e);
            if (result == null) {
                if (compare(curNode.value, e) == 0) {
                    return null;
                }
                return curNode.value;
            }
            return result;
        }
    }

    /**
     * Return the least element which is greater than given.
     * @param e element to find ceiling for
     * @return the least element which is greater than given
     */
    @Override
    public E higher(E e) {
        if (root == null) {
            return null;
        }
        return higher(root, e);
    }

    /** One element of the tree.*/
    private class Node {
        /** value of the element */
        private E value;

        /** left son */
        private Node left = null;

        /** right son */
        private Node right = null;

        /** parent of the node, null for root */
        private Node parent = null;

        /** creates a node from a value */
        private Node(E value) {
            this.value = value;
        }
    }

    /** Iterator of TreeSet going from the begin to the end.*/
    private class MyTreeSetIterator implements Iterator<E> {
        private Node currentNode;

        /** First element to iterate from if element in the leftest branch*/
        MyTreeSetIterator() {
            if (TreeSet.this.root == null) {
                currentNode = null;
                return;
            }
            currentNode = root;
            while(currentNode.left != null) {
                currentNode = currentNode.left;
            }
        }

        /**
         * Checks if there is next element of not.
         * @return true if there is next element, false if not
         */
        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        /**
         * Returns next element and move the iterator.
         * @return next element
         */
        @Override
        public E next() {
            if (!hasNext()) {
                return null;
            }
            E returnValue = currentNode.value;
            currentNode = findClose(currentNode);
            return returnValue;
        }

        /**
         * Finds the next in order node to currentNode
         * @param currentNode node to find next to
         * @return node, next to given
         */
        private Node findClose(Node currentNode) {
            if (currentNode.right != null) {
                currentNode = currentNode.right;
                while (currentNode.left != null) {
                    currentNode = currentNode.left;
                }
            } else {
                currentNode = currentNode.parent;
            }
            return currentNode;
        }
    }

    /** Iterator of TreeSet going from the end to the begin.*/
    private class MyTreeSetDescendingIterator implements Iterator<E> {
        private Node currentNode;

        MyTreeSetDescendingIterator() {
            if (TreeSet.this.root == null) {
                currentNode = null;
                return;
            }
            currentNode = root;
            while(currentNode.right != null) {
                currentNode = currentNode.right;
            }
        }

        /**
         * Checks if there is next element of not.
         * @return true if there is next element, false if not
         */
        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        /**
         * Returns next element and move the iterator.
         * @return next element
         */
        @Override
        public E next() {
            if (!hasNext()) {
                return null;
            }
            E returnValue = currentNode.value;
            currentNode = findClose(currentNode);
            return returnValue;
        }

        /**
         * Finds the next in order node to currentNode
         * @param currentNode node to find next to
         * @return node, next to given
         */
        private Node findClose(Node currentNode) {
            if (currentNode.left != null) {
                currentNode = currentNode.left;
                while (currentNode.right != null) {
                    currentNode = currentNode.right;
                }
            } else {
                currentNode = currentNode.parent;
            }
            return currentNode;
        }
    }

    /**
     * TreeSet which is the opposite of current - all
     * left subtrees are right and all right - left.
     * All the method have the opposite meaning, like all
     * comparator's results are with "-" sigh.
     */
    private class DescendingSet extends AbstractSet<E> implements MyTreeSet<E> {
        @Override
        public Iterator<E> iterator() {
            return TreeSet.this.descendingIterator();
        }

        @Override
        public int size() {
            return TreeSet.this.size;
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