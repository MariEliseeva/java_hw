package spbau.eliseeva.test;

import spbau.eliseeva.hashmap.HashMap;

public class main {
    public static void main(String[] args) {
        HashMap hashmap = new HashMap();
        hashmap.put("one", "1");
        hashmap.put("three", "3");
        hashmap.put("eleven", "11");
        System.out.println(hashmap.size());
        System.out.println(hashmap.contains("one") );
        System.out.println(hashmap.contains("two") );
        System.out.println(hashmap.get("eleven") );
        hashmap.remove("three");
        System.out.println(hashmap.contains("three") );
        hashmap.clear();
        System.out.println(hashmap.size());
        System.out.println(hashmap.contains("one"));
    }
}
