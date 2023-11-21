package main.repository;

import java.util.List;

import main.model.Course;

public interface CourseRepository {
    int update(Course course);
    int add(Course course);
    List<Course> findAll();
    int deleteById(String courseId);
}
