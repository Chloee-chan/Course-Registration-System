package main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.Main;
import main.dao.JdbcCourseRepository;
import main.dao.JdbcStudentRepository;
import main.model.Course;
import main.model.Student;
import main.repository.CourseRepository;
import main.repository.StudentRepository;

public class StudentController implements Initializable {

    private CourseRepository courseRepository = new JdbcCourseRepository();
    private StudentRepository studentRepository = new JdbcStudentRepository();

    private Student session;

    @FXML
    private Text id;

    @FXML
    private Text name;

    @FXML
    private AnchorPane editInfoPane;

    @FXML
    private AnchorPane coursePane;

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

    @FXML
    private TextField courseId;

    @FXML
    private TableView<Course> courseListView;

    @FXML
    private Text registerCompleted;

    @FXML
    private Text registerFailed;

    @FXML
    private Text withdrawCompleted;

    @FXML
    private Text withdrawFailed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editInfoPane.setVisible(false);
        coursePane.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    private void showCourseList() {
        courseListView.getColumns().clear();
        courseListView.getItems().clear();

        TableColumn<Course, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Course, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Course, String> creditsColumn = new TableColumn<>("Credit");
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));

        TableColumn<Course, String> slotColumn = new TableColumn<>("Slots");
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("maxStudents"));

        courseListView.getColumns().addAll(idColumn, nameColumn, creditsColumn, slotColumn);

        courseListView.setItems(FXCollections.observableArrayList(
                courseRepository.findAll().stream()
                        .filter(e -> {
                            int currentStudent = courseRepository.findCurrentStudentByCourseId(e.getId());
                            return currentStudent >= 0 && currentStudent <= e.getMaxStudents() && studentRepository.isRegisteredCourse(this.session.getId(), e.getId()) == 0;
                        })
                        .collect(Collectors.toList())));
    }

    @FXML
    private void registerPressed() {
        int result = studentRepository.registerCourse(this.session.getId(), courseId.getText());
        if (result == 1) {
            registerCompleted.setVisible(true);
            registerFailed.setVisible(false);
        } else {
            registerCompleted.setVisible(false);
            registerFailed.setVisible(true);
        }
        showCourseList();
        courseId.setText("");
        withdrawCompleted.setVisible(false);
        withdrawFailed.setVisible(false);
    }

    @FXML
    private void withdrawPressed() {
        int result = studentRepository.withDrawCourse(this.session.getId(), courseId.getText());
        if (result == 1) {
            withdrawCompleted.setVisible(true);
            withdrawFailed.setVisible(false);
        } else {
            withdrawCompleted.setVisible(false);
            withdrawFailed.setVisible(true);
        }
        showCourseList();
        courseId.setText("");
        registerCompleted.setVisible(false);
        registerFailed.setVisible(false);
    }

    @FXML
    void cancelEditPressed() {
        courseId.setText("");
        registerCompleted.setVisible(false);
        registerFailed.setVisible(false);
        withdrawCompleted.setVisible(false);
        withdrawFailed.setVisible(false);
    }

    @FXML
    private void saveEditPressed() {
        this.session.setFullName(nameStudentEdit.getText());
        this.session.setAddress(addressStudentEdit.getText());
        this.session.setPhoneNumber(phoneStudentEdit.getText());
        this.session.setEmail(emailStudentEdit.getText());
        this.session.setClassId(classStudentEdit.getText());
        int result = studentRepository.update(this.session);
        if (result == 1) {
            saveCompletedEdit.setVisible(true);
            saveFailedEdit.setVisible(false);
        } else {
            saveCompletedEdit.setVisible(false);
            saveFailedEdit.setVisible(true);
        }
        nameStudentEdit.setText("");
        addressStudentEdit.setText("");
        phoneStudentEdit.setText("");
        emailStudentEdit.setText("");
        classStudentEdit.setText("");
    }

    @FXML
    private void coursePressed() {
        editInfoPane.setVisible(false);
        coursePane.setVisible(true);
        registerCompleted.setVisible(false);
        registerFailed.setVisible(false);
        withdrawCompleted.setVisible(false);
        withdrawFailed.setVisible(false);
        showCourseList();
    }

    @FXML
    private void editInfoPressed() {
        editInfoPane.setVisible(true);
        coursePane.setVisible(false);
        saveCompletedEdit.setVisible(false);
        saveFailedEdit.setVisible(false);
        nameStudentEdit.setText(this.session.getFullName());
        addressStudentEdit.setText(this.session.getAddress());
        phoneStudentEdit.setText(this.session.getPhoneNumber());
        emailStudentEdit.setText(this.session.getEmail());
        classStudentEdit.setText(this.session.getClassId());
    }

    @FXML
    private void logoutButtonPressed() {
        try {
            Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/main/view/login.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Student getSession() {
        return session;
    }

    public void setSession(Student session) {
        this.session = session;
        this.id.setText(session.getId());
        this.name.setText(session.getFullName());
    }
}
