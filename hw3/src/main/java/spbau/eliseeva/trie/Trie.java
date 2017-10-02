package spbau.eliseeva.trie;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class represents Trie data structure, which is used for keeping strings.
 * Consists of inner elements -- Vertexes, each of them have pointers to next
 * and a mark -- if it is the end of some string or not.
 * The Trie exactly is a root-Vertex.
 */
public class Trie implements Serializable {
    /**
     * Inner class Vertex.
     * Pointers to next Vertices are kept in a HashMap.
     * Key is a letter on an Edge between Vertices.
     */
    private class Vertex {
        /**
         * Marks that the Vertex is the end of a string.
         * Used for finding if a string is in Trie or not.
         */
        private boolean isEnd = false;

        /**
         * How many words are beginning with the prefix,
         *  which ends in the Vertex
         */
        private int counter = 0;

        /** HashMap with keys -- Edges from the Vertex, values -- next Vertices */
        private HashMap<Character, Vertex> next = new HashMap<>();
    }

    /** Root of the Trie, Vertex which we start all operations from */
    private Vertex root = new Vertex();

    /** Number of words in the Trie */
    private int size = 0;

    /**
     * Finds if a string is in the Trie or not.
     * Goes from the root by given edges - symbols in string.
     * If no edge ot isEnd is not true - no such element in Trie.
     * @param element string to be found
     * @return true if found, false if not
     */
    public boolean contains(String element) {
        Vertex currentVertex = root;
        boolean ifContains = true;
        for (int i = 0; i < element.length(); i++) {
            if (currentVertex.next.containsKey(element.charAt(i))) {
                currentVertex = currentVertex.next.get(element.charAt(i));
            } else {
                ifContains = false;
                break;
            }
        }
        if (ifContains) ifContains = currentVertex.isEnd;
        return ifContains;
    }

    /**
     * Adds string to the Trie.
     * Goes from the root by given edges, if no edge -- creates one.
     * @param element string to be put
     * @return true if the string is new, false if it was in the Trie before
     */
    public boolean add(String element) {
        if (contains(element)) return false;
        size++;
        Vertex currentVertex = root;
        Vertex newVertex;
        for (int i = 0; i < element.length(); i++) {
            currentVertex.counter++;
            if (currentVertex.next.containsKey(element.charAt(i))) {
                currentVertex = currentVertex.next.get(element.charAt(i));
            } else {
                newVertex = new Vertex();
                currentVertex.next.put(element.charAt(i), newVertex);
                currentVertex = newVertex;
            }
        }
        currentVertex.isEnd = true;
        return true;
    }

    /**
     * Removes string from the Trie.
     * Goes from the root and if no more words started
     * with one of next Vertices -- removes if from the HashMap.
     * Set isEnd value false.
     * @param element string to be removed.
     * @return true if the string was removed, false if the string was not in the Trie.
     */
    public boolean remove(String element) {
        if (!contains(element)) return false;
        size--;
        Vertex currentVertex = root;
        Vertex oldVertex;
        for (int i = 0; i < element.length(); i++) {
            currentVertex.counter--;
            oldVertex = currentVertex.next.get(element.charAt(i));
            if (oldVertex.counter == 1) {
                currentVertex.next.remove(element.charAt(i));
                return true;
            }
            currentVertex = oldVertex;
        }
        currentVertex.counter--;
        currentVertex.isEnd = false;
        return true;
    }

    /** Tells how many strings are in the Trie.
     * @return the size
     */
    public int size() {
        return size;
    }

    /**
     * Tells how many strings starts from given prefix.
     * Goes from the root, when come to a Vertex
     * with the last symbol of the prefix -- returns counter,
     * how many strings begin from current Vertex.
     * @param prefix prefix
     * @return number of strings, started with given prefix
     */
    public int howManyStartsWithPrefix(String prefix) {
        Vertex currentVertex = root;
        for (int i = 0; i < prefix.length(); i++) {
            if (currentVertex.next.containsKey(prefix.charAt(i))) {
                currentVertex = currentVertex.next.get(prefix.charAt(i));
            } else {
                return 0;
            }
        }
        return (currentVertex.counter);
    }
}
