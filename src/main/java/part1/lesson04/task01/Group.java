package part1.lesson04.task01;

public class Group {
    private String name;
    private int countStudents;

    public Group(String name, int countStudents) {
        this.name = name;
        this.countStudents = countStudents;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", countStudents=" + countStudents +
                '}';
    }
}
