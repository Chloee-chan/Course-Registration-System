package main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.Main;
import main.dao.JdbcCourseRepository;
import main.model.Course;
import main.repository.CourseRepository;

public class AdminController implements Initializable {

    private CourseRepository courseRepository = new JdbcCourseRepository();

    @FXML
    private AnchorPane viewCoursePane;

    @FXML
    private AnchorPane createCoursePane;

    @FXML
    private AnchorPane deleteCoursePane;

    @FXML
    private AnchorPane editCoursePane;

    @FXML
    private Text username;

    @FXML
    private Button logoutButton;

    @FXML
    private Button viewCourseButton;

    @FXML
    private Button createCourseButton;

    @FXML
    private Button deleteCourseButton;

    @FXML
    private Button editCourseButton;

    @FXML
    private TableView<Course> viewCourseTable;

    @FXML
    private Text saveFailedCreate;

    @FXML
    private Text saveCompletedCreate;

    @FXML
    private TextField courseIdCreate;

    @FXML
    private TextField courseNameCreate;

    @FXML
    private TextField courseCreditCreate;

    @FXML
    private TextField courseMaxStudentCreate;

    @FXML
    private TextField courseIdDelete;

    @FXML
    private Text deleteCompleted;

    @FXML
    private Text deleteFailed;

    @FXML
    private TextField courseIdEdit;

    @FXML
    private TextField courseNameEdit;

    @FXML
    private TextField courseCreditEdit;

    @FXML
    private TextField courseMaxStudentEdit;

    @FXML
    private Text searchFound;

    @FXML
    private Text searchNotFound;

    @FXML
    private Text saveCompletedEdit;

    @FXML
    private Text saveFailedEdit;

    @FXML
    private void searchEditPressed() {
        Course course = courseRepository.findById(courseIdEdit.getText());
        if (course != null) {
            courseNameEdit.setText(course.getName());
            courseCreditEdit.setText(course.getCredit() + "");
            courseMaxStudentEdit.setText(course.getMaxStudents() + "");
            searchFound.setVisible(true);
            searchNotFound.setVisible(false);
        } else {
            courseIdEdit.setText("");
            searchFound.setVisible(false);
            searchNotFound.setVisible(true);
        }
        saveCompletedEdit.setVisible(false);
        saveFailedEdit.setVisible(false);
    }

    @FXML
    private void saveEditPressed() {
        try {
            Course course = new Course(
                    courseIdEdit.getText(),
                    courseNameEdit.getText(),
                    Integer.parseInt(courseCreditEdit.getText()),
                    Integer.parseInt(courseMaxStudentEdit.getText()));
            int result = courseRepository.update(course);
            if (result == 1) {
                saveCompletedEdit.setVisible(true);
                saveFailedEdit.setVisible(false);
            }
        } catch (NumberFormatException e) {
            saveCompletedEdit.setVisible(false);
            saveFailedEdit.setVisible(true);
        }
        courseIdEdit.setText("");
        courseNameEdit.setText("");
        courseCreditEdit.setText("");
        courseMaxStudentEdit.setText("");
        searchFound.setVisible(false);
        searchNotFound.setVisible(false);
    }

    @FXML
    private void cancelEditPressed() {
        courseIdEdit.setText("");
        courseNameEdit.setText("");
        courseCreditEdit.setText("");
        courseMaxStudentEdit.setText("");
        searchFound.setVisible(false);
        searchNotFound.setVisible(false);
        saveCompletedEdit.setVisible(false);
        saveFailedEdit.setVisible(false);
    }

    @FXML
    private void saveCreatePressed() {
        try {
            Course course = new Course(
                    courseIdCreate.getText(),
                    courseNameCreate.getText(),
                    Integer.parseInt(courseCreditCreate.getText()),
                    Integer.parseInt(courseMaxStudentCreate.getText()));
            int result = courseRepository.add(course);
            if (result == 1) {
                saveCompletedCreate.setVisible(true);
                saveFailedCreate.setVisible(false);
            } else {
                saveCompletedCreate.setVisible(false);
                saveFailedCreate.setVisible(true);
            }
        } catch (NumberFormatException e) {
            saveCompletedCreate.setVisible(false);
            saveFailedCreate.setVisible(true);
        }
        courseIdCreate.setText("");
        courseNameCreate.setText("");
        courseCreditCreate.setText("");
        courseMaxStudentCreate.setText("");
    }

    @FXML
    private void cancelCreatePressed() {
        courseIdCreate.setText("");
        courseNameCreate.setText("");
        courseCreditCreate.setText("");
        courseMaxStudentCreate.setText("");
        saveCompletedCreate.setVisible(false);
        saveFailedCreate.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    private void showCoursesToView() {
        viewCourseTable.getColumns().clear();
        viewCourseTable.getItems().clear();

        TableColumn<Course, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Course, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Course, String> creditsColumn = new TableColumn<>("Credit");
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));

        TableColumn<Course, String> slotColumn = new TableColumn<>("Slots");
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("maxStudents"));

        viewCourseTable.getColumns().addAll(idColumn, nameColumn, creditsColumn, slotColumn);

        viewCourseTable.setItems(FXCollections.observableArrayList(courseRepository.findAll()));
    }

    @FXML
    private void viewCourseButtonPressed() {
        viewCoursePane.setVisible(true);
        createCoursePane.setVisible(false);
        editCoursePane.setVisible(false);
        deleteCoursePane.setVisible(false);
        showCoursesToView();
    }

    @FXML
    private void createCourseButtonPressed() {
        viewCoursePane.setVisible(false);
        createCoursePane.setVisible(true);
        editCoursePane.setVisible(false);
        deleteCoursePane.setVisible(false);
        saveCompletedCreate.setVisible(false);
        saveFailedCreate.setVisible(false);
    }

    @FXML
    private void deleteCourseButtonPressed() {
        viewCoursePane.setVisible(false);
        createCoursePane.setVisible(false);
        editCoursePane.setVisible(false);
        deleteCoursePane.setVisible(true);
        deleteCompleted.setVisible(false);
        deleteFailed.setVisible(false);
        courseIdDelete.setText("");

        showCoursesToView();
    }

    @FXML
    private void deleteCoursePressed() {
        int result = courseRepository.deleteById(courseIdDelete.getText());
        if (result == 1) {
            deleteCompleted.setVisible(true);
            deleteFailed.setVisible(false);
            showCoursesToView();
        } else {
            deleteCompleted.setVisible(false);
            deleteFailed.setVisible(true);
        }
        courseIdDelete.setText("");
    }

    @FXML
    private void editCourseButtonPressed() {
        viewCoursePane.setVisible(false);
        createCoursePane.setVisible(false);
        editCoursePane.setVisible(true);
        deleteCoursePane.setVisible(false);
        courseIdEdit.setText("");
        courseNameEdit.setText("");
        courseCreditEdit.setText("");
        courseMaxStudentEdit.setText("");
        searchFound.setVisible(false);
        searchNotFound.setVisible(false);
        saveCompletedEdit.setVisible(false);
        saveFailedEdit.setVisible(false);
    }

    @FXML
    private void logoutButtonPressed() {
        try {
            Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/main/view/login.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewCoursePane.setVisible(false);
        createCoursePane.setVisible(false);
        editCoursePane.setVisible(false);
        deleteCoursePane.setVisible(false);
    }
}
