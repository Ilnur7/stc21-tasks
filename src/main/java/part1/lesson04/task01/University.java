package part1.lesson04.task01;

import java.util.List;

public class University {
    public int countGroup;
    private String color;
    private List groups;
    public float budget;

    public University(int countGroup, String color, List groups, float budget) {
        this.countGroup = countGroup;
        this.color = color;
        this.groups = groups;
        this.budget = budget;
    }

    public int getCountGroup() {
        return countGroup;
    }

    public void setCountGroup(int countGroup) {
        this.countGroup = countGroup;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List getGroups() {
        return groups;
    }

    public void setGroups(List groups) {
        this.groups = groups;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "University{" +
                "countGroup=" + countGroup +
                ", color='" + color + '\'' +
                ", groups=" + groups +
                ", budget=" + budget +
                '}';
    }
}
