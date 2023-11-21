package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.component.MySQLConnector;
import main.model.Course;
import main.repository.CourseRepository;

public class JdbcCourseRepository implements CourseRepository {

    private Connection connection;

    public JdbcCourseRepository() {
        this.connection = MySQLConnector.connect();
    }

    @Override
    public int update(Course course) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "UPDATE course SET name=?, credit=?, max_student=? WHERE id=?");
            statement.setString(1, course.getName());
            statement.setInt(2, course.getCredit());
            statement.setInt(3, course.getMaxStudents());
            statement.setString(4, course.getId());
            int rowEffected = statement.executeUpdate();
            if (rowEffected == 1) {
                connection.commit();
                return 1;
            } else {
                connection.rollback();
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int add(Course course) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "INSERT INTO course(id, name, credit, max_student) VALUES(?,?,?,?)");
            statement.setString(1, course.getId());
            statement.setString(2, course.getName());
            statement.setInt(3, course.getCredit());
            statement.setInt(4, course.getMaxStudents());
            int rowEffected = statement.executeUpdate();
            if (rowEffected == 1) {
                connection.commit();
                return 1;
            } else {
                connection.rollback();
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Course> findAll() {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT * FROM course");
            ResultSet resultSet = statement.executeQuery();
            List<Course> courses = new ArrayList<>();
            while (resultSet.next()) {
                courses.add(
                        new Course(
                                resultSet.getString("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("credit"),
                                resultSet.getInt("max_student")));
            }
            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int deleteById(String courseId) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "DELETE FROM course WHERE id=?");
            statement.setString(1, courseId);
            int rowEffected = statement.executeUpdate();
            if (rowEffected == 1) {
                connection.commit();
                return 1;
            } else {
                connection.rollback();
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
