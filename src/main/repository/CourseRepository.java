package main.repository;

import java.util.List;

import main.model.Course;

public interface CourseRepository {
    int update(Course course);
    int add(Course course);
    List<Course> findAll();
    int deleteById(String courseId);
    Course findById(String id);
    int findCurrentStudentByCourseId(String courseId);
    List<Course> findCourseNotRegisterByStudentId(String studentId);
    List<Course> findCourseRegisteredByStudentId(String studentId);
    List<Course> findCourseNotRegisterByFaculty();
    List<Course> findCourseRegisteredByFacultyId(String facultyId);
}
