package part1.lesson02.task01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashMapObjectMain {
    HashMapObject map;

    @BeforeEach
    void init(){
        map = new HashMapObject();
    }

    @Test
    void put() throws ExceptionObject {
        map.put(1,"a");
        map.put(2,"b");
        assertEquals(2, map.size());
        assertNotEquals(1, map.size());
        assertThrows(ExceptionObject.class, () -> map.put(null, "5"));
    }

    @Test
    void update() throws ExceptionObject {
        map.put(1,"a");
        map.update(1, "b");
        assertEquals("b",map.get(1));
        assertNotEquals("a",map.get(1));
        assertThrows(ExceptionObject.class, () -> map.update(2, "5"));
    }

    @Test
    void delete() throws ExceptionObject {
        map.put(1,"a");
        map.put(2,"b");
        assertNotEquals(null, map.get(2));
        map.delete(2);
        assertEquals(1, map.size());
        assertEquals(null, map.get(2));
        assertThrows(ExceptionObject.class, () -> map.delete(3));
    }

    @Test
    void get() throws ExceptionObject {
        map.put(1,"a");
        map.put(2,"b");
        assertEquals("b", map.get(2));
        assertNotEquals("a", map.get(2));
    }
}