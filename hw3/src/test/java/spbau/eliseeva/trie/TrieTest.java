package spbau.eliseeva.trie;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/** This class tests the Trie class. */
public class TrieTest {
    /**
     * Tests add method, checks 3 different variants.
     * First and third -- adding new string, second -- adding existing one.
     */
    @Test
    public void addTest() {
        Trie trie = new Trie();
        assertEquals(true, trie.add("element1"));
        assertEquals(false, trie.add("element1"));
        assertEquals(true, trie.add("eleMent1"));
    }

    /**
     * Tests contains method, checks 3 different variants.
     * First and second strings are in the Trie, third is not.
     */
    @Test
    public void containsTest() {
        Trie trie = new Trie();
        trie.add("element");
        trie.add("eleMent");
        assertEquals(true, trie.contains("eleMent"));
        assertEquals(true, trie.contains("element"));
        assertEquals(false, trie.contains("eleMenT"));
    }

    /**
     * Tests remove method, checks 3 different variants.
     * First and second existed in the Trie,
     * third one -- removing a string, which was already removed.
     */
    @Test
    public void removeTest() {
        Trie trie = new Trie();
        trie.add("element1");
        trie.add("element2");
        trie.add("eleMent1");
        assertEquals(true, trie.remove("element2"));
        assertEquals(true, trie.remove("element1"));
        assertEquals(false, trie.remove("element1"));
    }

    /**
     * Tests size method, checks 4 different variants.
     * Firstly size is 0, then one string appears, then 2 more strings,
     * then one of the strings is removing.
     */
    @Test
    public void sizeTest() {
        Trie trie = new Trie();
        assertEquals(0, trie.size());
        trie.add("element1");
        assertEquals(1, trie.size());
        trie.add("element2");
        trie.add("eleMent1");
        assertEquals(3, trie.size());
        trie.remove("element1");
        assertEquals(2, trie.size());
    }

    /**
     * Tests howManyStartsWithPrefix method, checks 3 different variants.
     * All three elements start with "ele", but only 2 start with "elem"
     * and nothing starts with "eleME".
     */
    @Test
    public void howManyStartsWithPrefixTest() {
        Trie trie = new Trie();
        trie.add("element1");
        trie.add("element2");
        trie.add("eleMent1");
        assertEquals(3, trie.howManyStartsWithPrefix("ele"));
        assertEquals(2, trie.howManyStartsWithPrefix("elem"));
        assertEquals(0, trie.howManyStartsWithPrefix("eleME"));
    }

    /**
     * Tests serialization.
     * Serialize trie and compare it to expected byte array.
     * @throws IOException thrown if problems with reading or writing to stream
     */
    @Test
    public void serializeTest() throws IOException {
        Trie trie = new Trie();
        trie.add("el1");
        trie.add("el2");
        trie.add("eL1");
        trie.add("eL");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        trie.serialize(out);
        byte[] byteArray = out.toByteArray();
        byte[] expectedArray = {'f', 'a', 'l', 's', 'e', '*', '4', '*', 'e', '*',
                'f', 'a', 'l', 's', 'e', '*', '4', '*', 'l', '*',
                'f', 'a', 'l', 's', 'e', '*', '2', '*', '1', '*',
                't', 'r', 'u', 'e', '*', '0', '#', '2', '*',
                't', 'r', 'u', 'e', '*', '0', '#', '#', 'L', '*',
                't', 'r', 'u', 'e', '*', '1', '*', '1', '*',
                't', 'r', 'u', 'e', '*', '0', '#', '#', '#', '#'};
        assertArrayEquals(expectedArray, byteArray);
    }

    /**
     * Tests deserialization.
     * Deserialize trie, written in a byte array.
     * Checks if the size is same as needed and the same elements are in.
     * @throws IOException thrown if problems with reading or writing to stream
     */
    @Test
    public void deserializeTest() throws IOException {
        byte[] inArray = {'f', 'a', 'l', 's', 'e', '*', '3', '*', 'c', '*',
                'f', 'a', 'l', 's', 'e', '*', '2', '*', 'a', '*',
                'f', 'a', 'l', 's', 'e', '*', '2', '*', 't', '*',
                't', 'r', 'u', 'e', '*', '0', '#', 'r', '*',
                't', 'r', 'u', 'e', '*', '0', '#', '#', '#', 'a', '*',
                't', 'r', 'u', 'e', '*', '0', '#', '#'};
        ByteArrayInputStream in = new ByteArrayInputStream(inArray);
        Trie trie = new Trie();
        trie.deserialize(in);
        assertEquals(3, trie.size());
        assertEquals(true, trie.contains("cat"));
        assertEquals(true, trie.contains("car"));
        assertEquals(true, trie.contains("a"));
        assertEquals(false, trie.contains("ac"));
    }
}