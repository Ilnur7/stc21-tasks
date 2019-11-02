package part1.lesson03.task01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HashMapGenericsV2Test {
    Map myMap;
    Map map;

    @BeforeEach
    void setUp() {
        myMap = new HashMapGenericsV2();
        map = new HashMap();
        map.put(1,"a");
        map.put(2,"b");
        myMap.put(1,"a");
        myMap.put(2,"b");
    }

    @Test
    void put() {
        assertEquals(map, myMap);
    }

    @Test
    void remove() {
        map.remove(2);
        myMap.remove(2);
        assertEquals(map, myMap);
    }

    @Test
    void get() {
        assertEquals(map.get(2), myMap.get(2));
    }

    @Test
    void putAll() {
        Map mapPutAll = new HashMap(){{
           put(3, "c");
           put(4, "d");
        }};
        map.putAll(mapPutAll);
        myMap.putAll(mapPutAll);
        assertEquals(map, myMap);
    }

    @Test
    void clear() {
        myMap.clear();
        assertEquals(0, myMap.size());
    }

    @Test
    void keySet() {
        Set set = map.keySet();
        Set mySet = myMap.keySet();
        assertEquals(set, mySet);
    }

    @Test
    void values() {
        ArrayList<String> list = new ArrayList<String>(map.values());
        ArrayList<String> myList = new ArrayList<String>(myMap.values());
        assertEquals(list.size(), myList.size());
        for (int i = 0; i < myList.size(); i++) {
            assertEquals(list.get(i), myList.get(i));
        }

    }

    @Test
    void entrySet() {
        Set set = map.entrySet();
        Set mySet = myMap.entrySet();
        assertEquals(set, mySet);
    }

    @Test
    void size() {
        assertEquals(map.size(), myMap.size());
    }

    @Test
    void isEmpty() {
        map.remove(1);
        map.remove(2);
        myMap.remove(1);
        myMap.remove(2);
        assertEquals(map.size() == 0, myMap.size() == 0);
    }

    @Test
    void containsKey() {
        assertEquals(map.containsKey(2), myMap.containsKey(2));
    }

    @Test
    void containsValue() {
        assertEquals(map.containsValue("a"), myMap.containsValue("a"));
    }
}