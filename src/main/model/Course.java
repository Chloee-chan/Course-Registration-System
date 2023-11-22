package main.model;

public class Course {
    private String id;
    private String name;
    private int credit;
    private int maxStudents;

    public Course(String id, String name, int credit, int maxStudents) {
        this.id = id;
        this.name = name;
        this.credit = credit;
        this.maxStudents = maxStudents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    @Override
    public String toString() {
        return "Course [id=" + id + ", name=" + name + ", credit=" + credit + ", maxStudents=" + maxStudents + "]";
    }

}
