package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.component.MySQLConnector;
import main.model.Student;
import main.repository.StudentRepository;

public class JdbcStudentRepository implements StudentRepository {

    private Connection connection;

    public JdbcStudentRepository() {
        this.connection = MySQLConnector.connect();
    }

    @Override
    public int update(Student student) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "UPDATE student SET name=?, address=?, phone=?, email=?, class=? WHERE id=?");
            statement.setString(1, student.getFullName());
            statement.setString(2, student.getAddress());
            statement.setString(3, student.getPhoneNumber());
            statement.setString(4, student.getEmail());
            statement.setString(5, student.getClassId());
            statement.setString(6, student.getId());
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
    public int registerCourse(String studentId, String courseId) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO register VALUES(?,?)");
            statement.setString(1, studentId);
            statement.setString(2, courseId);
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
    public int withDrawCourse(String studentId, String courseId) {
        try {
            PreparedStatement statement = this.connection
                    .prepareStatement("DELETE FROM register WHERE student_id=? AND course_id=?");
            statement.setString(1, studentId);
            statement.setString(2, courseId);
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
    public Student findByUsername(String username) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM student WHERE account_id=?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Student(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("class"),
                        resultSet.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int isRegisteredCourse(String studentId, String courseId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM register WHERE student_id=? AND course_id=?");
            statement.setString(1, studentId);
            statement.setString(2, courseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
