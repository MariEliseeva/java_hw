package spbau.eliseeva.hashmap;

import static org.junit.Assert.*;

public class HashMapTest {
    @org.junit.Test
    public void testPut() throws Exception {
        HashMap hashMap = new HashMap();
        hashMap.put("key1", "value1");
        //assertEquals("value1");
    }

    @org.junit.Test
    public void testSize() throws Exception {
        HashMap hashMap = new HashMap();
        hashMap.put("key1", "value1");
        hashMap.put("key2", "value2");
        assertEquals(2, hashMap.size());
    }

    @org.junit.Test
    public void testContains() throws Exception {
        HashMap hashMap = new HashMap();
        hashMap.put("key1", "value1");
        hashMap.put("key2", "value2");
        assertEquals(true, hashMap.contains("key2"));
    }

    @org.junit.Test
    public void testGet() throws Exception {
    }

    @org.junit.Test
    public void testRemove() throws Exception {
    }

    @org.junit.Test
    public void testClear() throws Exception {
    }

}