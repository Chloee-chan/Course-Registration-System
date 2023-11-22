package main.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.model.Student;

public class StudentController implements Initializable {

    private Student session;

    @FXML
    private Text id;

    @FXML
    private Text name;

    @FXML
    private AnchorPane editInfoPane;

    @FXML
    private AnchorPane registerCoursePane;

    @FXML
    private AnchorPane withdrawCoursePane;

    @FXML
    private TextField nameStudentEdit;

    @FXML
    private TextField addressStudentEdit;

    @FXML
    private TextField phoneStudentEdit;

    @FXML
    private TextField emailStudentEdit;

    @FXML
    private TextField classStudentEdit;

    @FXML 
    private Text saveCompletedEdit;

    @FXML
    private Text saveFailedEdit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML void cancelEditPressed() {

    }

    @FXML
    private void saveEditPressed() {

    }

    @FXML
    private void withdrawCoursePressed() {

    }

    @FXML
    private void registerCoursePressed() {

    }

    @FXML
    private void editInfoPressed() {

    }

    @FXML
    private void logoutButtonPressed() {

    }

    public Student getSession() {
        return session;
    }

    public void setSession(Student session) {
        this.session = session;
    }
}
