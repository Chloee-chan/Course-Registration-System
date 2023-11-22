package main.repository;

import main.model.Student;

public interface StudentRepository {
    int update(Student student);
    int registerCourse(String studentId, String courseId);
    int withDrawCourse(String studentId, String courseId);
    Student findByUsername(String username);
    int isRegisteredCourse(String studentId, String courseId);
}
