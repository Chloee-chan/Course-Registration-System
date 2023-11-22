package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.component.MySQLConnector;
import main.model.Faculty;
import main.repository.FacultyRepository;

public class JdbcFacultyRepository implements FacultyRepository {

    private Connection connection;

    public JdbcFacultyRepository() {
        this.connection = MySQLConnector.connect();
    }

    @Override
    public int update(Faculty faculty) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "UPDATE faculty SET name=?, address=?, phone=?, email=?, department=? WHERE id=?");
            statement.setString(1, faculty.getFullName());
            statement.setString(2, faculty.getAddress());
            statement.setString(3, faculty.getPhoneNumber());
            statement.setString(4, faculty.getEmail());
            statement.setString(5, faculty.getDepartment());
            statement.setString(6, faculty.getId());
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
    public int registerCourse(String facultyId, String courseId) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "UPDATE course SET faculty_id=? WHERE id=? AND faculty_id IS NULL");
            statement.setString(1, facultyId);
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
    public int withDrawCourse(String facultyId, String courseId) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "UPDATE course SET faculty_id = NULL WHERE id = ? AND faculty_id = ?");
            statement.setString(1, courseId);
            statement.setString(2, facultyId);
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
    public Faculty findByUsername(String username) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM faculty WHERE account_id=?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Faculty(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("address"),
                        resultSet.getString("department"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
