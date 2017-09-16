package spbau.eliseeva.test;

import org.junit.Test;
import spbau.eliseeva.hashmap.HashMap;

import static org.junit.Assert.*;

public class HashMapTest {
    @Test
    public void putTest() throws Exception {
        HashMap hashMap = new HashMap();
        hashMap.put("key1", "value1");
        hashMap.put("key2", "value2");
    }


    @Test
    public void getTest() throws Exception {
        HashMap hashMap = new HashMap();
        hashMap.put("key1", "value1");
        hashMap.put("key2", "value2");
        assertEquals("value2", hashMap.get("key2"));
    }

    @Test
    public void removeTest() throws Exception {
        HashMap hashMap = new HashMap();
        hashMap.put("key1", "value1");
        hashMap.put("key2", "value2");
        assertEquals("value1", hashMap.remove("key1"));
        assertEquals(null, hashMap.get("key1"));
    }

    @Test
    public void sizeTest() throws Exception {
        HashMap hashMap = new HashMap();
        hashMap.put("key1", "value1");
        hashMap.put("key2", "value2");
        hashMap.put("key1", "value3");
        assertEquals(2, hashMap.size());
    }

    @Test
    public void containsTets() throws Exception {
        HashMap hashMap = new HashMap();
        hashMap.put("key1", "value1");
        hashMap.put("key2", "value2");
        assertEquals(true, hashMap.contains("key2"));
        assertEquals(false, hashMap.contains("key3"));
    }

    @Test
    public void clear() throws Exception {
        HashMap hashMap = new HashMap();
        hashMap.put("key1", "value1");
        hashMap.put("key2", "value2");
        hashMap.clear();
        assertEquals(null, hashMap.get("key2"));
        assertEquals(null, hashMap.get("key1"));
    }

}