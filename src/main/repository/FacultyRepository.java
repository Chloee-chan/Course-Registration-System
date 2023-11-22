package main.repository;

import main.model.Faculty;

public interface FacultyRepository {
    int update(Faculty faculty);
    int registerCourse(String facultyId, String courseId);
    int withDrawCourse(String facultyId, String courseId);
    Faculty findByUsername(String username);
}
