package main.model;

public class Course {
    private String id;
    private String name;
    private int credit;
    private int maxStudents;
    private Faculty faculty;

    public Course(String id, String name, int credit, int maxStudents, Faculty faculty) {
        this.id = id;
        this.name = name;
        this.credit = credit;
        this.maxStudents = maxStudents;
        this.faculty = faculty;
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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}
