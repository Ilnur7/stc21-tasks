package part1.lesson02.task01;

public class Main {
    public static void main(String[] args) throws ExceptionObject {
        HashMapObject map = new HashMapObject();
        map.put(1, "a");
        map.put(2, "б");
        map.put(3, "в");
        map.put(4, "г");
        map.put(5, "г");
        map.update(5, "t");
        System.out.println(map.size());
        map.delete(2);
        System.out.println(map.size());
        System.out.println(map.get(2));



    }

}
