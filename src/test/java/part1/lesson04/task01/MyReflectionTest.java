package part1.lesson04.task01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import part1.lesson02.task01.ExceptionObject;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MyReflectionTest {
    University university;
    Set<String> fieldsToCleanup;
    Set<String> fieldsToOutput;
    Map map;

    @BeforeEach
    void setUp() {
        List<Group> groups = new ArrayList<Group>(){{
            add(new Group("Stc-1", 20));
            add(new Group("Stc-2", 25));
        }};
        university = new University(10, "brown", groups, 1_000_000f);
        fieldsToCleanup = new HashSet<String>(){{
            add("countGroup");
            add("color");
            add("groups");
            add("budget");
        }};
        fieldsToOutput = new HashSet<String>(){{
            add("groups");
            add("budget");
            add("countGroup");
            add("color");
        }};
        map = new HashMap(){{
           put("groups", "Max");
           put("budget", "Alex");
           put("countGroup", "John");
           put("color", "Peter");
        }};
    }

    @Test
    void cleanup() throws IllegalAccessException {
        MyReflection.cleanup(university, fieldsToCleanup, fieldsToOutput);
        assertEquals(null, university.getColor());
        assertEquals(0, university.getCountGroup());
        assertEquals(null, university.getGroups());
        assertEquals(0, university.getBudget());

        MyReflection.cleanup(map, fieldsToCleanup, fieldsToOutput);
        assertEquals(null, map.get("groups"));
        assertEquals(null, map.get("budget"));
        assertEquals(null, map.get("countGroup"));
        assertEquals(null, map.get("color"));

    }
}