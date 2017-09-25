package spbau.eliseeva.test;

import org.junit.Test;
import spbau.eliseeva.list.List;

import static org.junit.Assert.*;

public class ListTest {
    @Test
    public void pushBackTest() throws Exception {
        List list = new List();
        list.pushBack("key1", "value1");
        list.pushBack("key2", "value2");
    }

    @Test
    public void findTest() throws Exception {
        List list = new List();
        list.pushBack("key1", "value1");
        list.pushBack("key2", "value2");
        assertEquals("value2", list.find("key2"));
    }

    @Test
    public void getFrontKeyTest() throws Exception {
        List list = new List();
        list.pushBack("key1", "value1");
        list.pushBack("key2", "value2");
        assertEquals("key1", list.getFrontKey());
    }

    @Test
    public void getFrontValueTest() throws Exception {
        List list = new List();
        list.pushBack("key1", "value1");
        list.pushBack("key2", "value2");
        assertEquals("value1", list.getFrontValue());
    }

    @Test
    public void removeFrontTest() throws Exception {
        List list  = new List();
        list.pushBack("key1", "value1");
        list.pushBack("key2", "value2");
        list.removeFront();
        assertEquals(null, list.find("key1"));
        assertEquals("key2", list.getFrontKey());
    }

    @Test
    public void removeTest() throws Exception {
        List list  = new List();
        list.pushBack("key1", "value1");
        list.pushBack("key2", "value2");
        list.remove("key2");
        assertEquals(null, list.find("key2"));
    }

    @Test
    public void clearTest() throws Exception {
        List list  = new List();
        list.pushBack("key1", "value1");
        list.pushBack("key2", "value2");
        list.clear();
        assertEquals(null, list.find("key1"));
        assertEquals(null, list.find("key2"));
    }

}