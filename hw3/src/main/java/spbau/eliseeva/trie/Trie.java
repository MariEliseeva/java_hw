package spbau.eliseeva.trie;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Class represents Trie data structure, which is used for keeping strings.
 * Consists of inner elements - Vertexes, each of them has pointers to next Vertices
 * and a mark - if it is the end of some string or not.
 * The Trie exactly is a root-Vertex.
 */
public class Trie {
    /**
     * Inner class Vertex.
     * Pointers to next Vertices are kept in a HashMap.
     * Key is a letter on an Edge between Vertices.
     */
    private class Vertex implements Serializable {
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

        /**
         * Writes string "true" or "false" -- terminal Vertex or not, then
         * writes string with counter, then for every Vertex in next
         * writes key and recursively all info about Vertex. Ends with '#',
         * divide writings by '*'.
         * @param writer Writer to write
         * @throws IOException thrown if writing problems
         */
        private void serialize(Writer writer) throws IOException {
            writer.write(String.valueOf(isEnd));
            writer.write("*");
            writer.write(String.valueOf(counter));
            if (next.entrySet().size() != 0) {
                writer.write("*");
            }
            for (Map.Entry<Character, Vertex> e : next.entrySet()) {
                writer.write(String.valueOf(e.getKey()));
                writer.write("*");
                e.getValue().serialize(writer);
            }
            writer.write("#");
        }

        /**
         * Reads until first '*' and gets ifEnd field,
         * reads until second '*' and gets counter field,
         * then read until '#' all info about Vertices
         * @param reader Reader to read from
         * @throws IOException thrown if reading problem
         */
        private void deserialize(Reader reader) throws IOException {
            String isEnd = "";
            int c;
            for(;;) {
                c = reader.read();
                if (c == '*') {
                    break;
                }
                isEnd += (char)c;
            }
            this.isEnd = Boolean.parseBoolean(isEnd);
            String counter = "";
            for(;;) {
                c = reader.read();
                if (c == '*' || c == '#') {
                    break;
                }
                counter += (char)c;
            }
            this.counter = Integer.parseInt(counter);
            if (c == '#') {
                return;
            }
            for(;;) {
                c = reader.read();
                if (c == '#') {
                    return;
                }
                reader.read();
                next.put((char) c, new Vertex());
                next.get((char) c).deserialize(reader);
            }
        }
    }

    /** Root of the Trie, Vertex which we start all operations from */
    private Vertex root = new Vertex();

    /**
     * Finds if a string is in the Trie or not.
     * Works for the length of word, which is being found. (Because HashMap is O(1))
     * Goes from the root by given edges - symbols in string.
     * If no edge ot isEnd is not true - no such element in Trie.
     * @param element string to be found
     * @return true if found, false if not
     */
    public boolean contains(String element) {
        Vertex currentVertex = root;
        boolean isContained = true;
        for (int i = 0; i < element.length(); i++) {
            if (currentVertex.next.containsKey(element.charAt(i))) {
                currentVertex = currentVertex.next.get(element.charAt(i));
            } else {
                isContained = false;
                break;
            }
        }
        return isContained && currentVertex.isEnd;
    }

    /**
     * Adds string to the Trie.
     * Works for the length of the added word. (Because HashMap is O(1))
     * Goes from the root by given edges, if no edge -- creates one.
     * @param element string to be put
     * @return true if the string is new, false if it was in the Trie before
     */
    public boolean add(String element) {
        if (contains(element)) {
            return false;
        }
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
     * Works for the length of the word, which is being removed. (Because HashMap is O(1))
     * Goes from the root and if no more words started
     * with one of next Vertices -- removes if from the HashMap.
     * Set isEnd value false.
     * @param element string to be removed.
     * @return true if the string was removed, false if the string was not in the Trie.
     */
    public boolean remove(String element) {
        if (!contains(element)) {
            return false;
        }
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
     *  Works for O(1).
     * @return the size
     */
    public int size() {
        return root.counter;
    }

    /**
     * Tells how many strings starts from given prefix.
     * Works for the length of the prefix. (Because HashMap is O(1))
     * Goes from the root, when come to a Vertex
     * with the last symbol of the prefix -- returns counter,
     * how many strings begin from current Vertex.
     * @param prefix prefix to be found
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
        return currentVertex.counter;
    }

    /**
     * Serialize Trie  <=> serialize its root, because
     * all other vertices are serializing recursively.
     * @param out stream to write.
     * @throws IOException thrown if had problems with writing to OutputStream
     */
    public void serialize(OutputStream out) throws IOException {
        Writer writer = new OutputStreamWriter(out);
        root.serialize(writer);
        writer.close();
    }

    /**
     * Deserialize Trie  <=> deserialize its root, because
     * all other vertices are deserializing recursively.
     * @param in stream, which the Trie is taken from.
     * @throws IOException thrown if had problems with reading from InputStream
     */
    public void deserialize(InputStream in) throws IOException {
        Reader reader = new InputStreamReader(in);
        root.deserialize(reader);
        reader.close();
    }
}