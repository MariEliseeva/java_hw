package spbau.eliseeva.trie;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/** This class tests the Trie class. */
public class TrieTest {
    /**
     * Tests add method, checks 3 different variants.
     * First and third -- adding new string, second -- adding existing one.
     * @throws Exception
     */
    @Test
    public void addTest() throws Exception {
        Trie trie = new Trie();
        assertEquals(true, trie.add("element1"));
        assertEquals(false, trie.add("element1"));
        assertEquals(true, trie.add("eleMent1"));
    }

    /**
     * Tests contains method, checks 3 different variants.
     * First and second strings are in the Trie, third is not.
     * @throws Exception
     */
    @Test
    public void containsTest() throws Exception {
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
     * @throws Exception
     */
    @Test
    public void removeTest() throws Exception {
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
     * @throws Exception
     */
    @Test
    public void sizeTest() throws Exception {
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
     * @throws Exception
     */
    @Test
    public void howManyStartsWithPrefixTest() throws Exception {
        Trie trie = new Trie();
        trie.add("element1");
        trie.add("element2");
        trie.add("eleMent1");
        assertEquals(3, trie.howManyStartsWithPrefix("ele"));
        assertEquals(2, trie.howManyStartsWithPrefix("elem"));
        assertEquals(0, trie.howManyStartsWithPrefix("eleME"));
    }

    /** Tests serialization. */
    @Test
    public void serializeTest() {
        Trie trie = new Trie();
        trie.add("element1");
        trie.add("element2");
        trie.add("eleMent1");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            trie.serialize(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests deserialization.
     * Serialize and deserialize trie, compare it to trie2.
     */
    @Test
    public void deserializeTest() {
        Trie trie = new Trie();
        trie.add("element1");
        trie.add("element2");
        trie.add("eleMent1");
        Trie trie2 = trie;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            trie.serialize(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] byteArray = out.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
        try {
            trie.deserialize(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(trie.size(), trie2.size());
        assertEquals(trie.root(), trie2.root());
    }














}