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
     * Tests deserialization.
     * Serialize and then deserialize trie.
     * Checks if the size is still the same and the same elements are in.
     * @throws IOException thrown if problems with reading or writing to stream
     */
    @Test
    public void serializeAndDeserializeTest() throws IOException {
        Trie trie = new Trie();
        trie.add("element1");
        trie.add("element2");
        trie.add("eleMent1");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        trie.serialize(out);
        byte[] byteArray = out.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
        trie.deserialize(in);
        assertEquals(3, trie.size());
        assertEquals(true, trie.contains("element1"));
        assertEquals(true, trie.contains("element2"));
        assertEquals(true, trie.contains("eleMent1"));
        assertEquals(false, trie.contains("eleMENT"));
    }
}