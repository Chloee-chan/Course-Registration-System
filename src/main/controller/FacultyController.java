package main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
import main.dao.JdbcFacultyRepository;
import main.model.Course;
import main.model.Faculty;

public class FacultyController implements Initializable {

    private Faculty session;

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
    private TableView<Course> courseNotRegister;

    @FXML
    private TableView<Course> courseRegisted;

    @FXML
    private Text registerCompleted;

    @FXML
    private Text registerFailed;

    @FXML
    private Text withdrawCompleted;

    @FXML
    private Text withdrawFailed;

    @SuppressWarnings("unchecked")
    private void showCourseList() {
        courseNotRegister.getColumns().clear();
        courseNotRegister.getItems().clear();
        courseRegisted.getColumns().clear();
        courseRegisted.getItems().clear();

        TableColumn<Course, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Course, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Course, String> creditsColumn = new TableColumn<>("Credit");
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));

        TableColumn<Course, String> slotColumn = new TableColumn<>("Slots");
        slotColumn.setCellValueFactory(new PropertyValueFactory<>("maxStudents"));

        TableColumn<Course, String> idColumn2 = new TableColumn<>("ID");
        idColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Course, String> nameColumn2 = new TableColumn<>("Name");
        nameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Course, String> creditsColumn2 = new TableColumn<>("Credit");
        creditsColumn2.setCellValueFactory(new PropertyValueFactory<>("credit"));

        TableColumn<Course, String> slotColumn2 = new TableColumn<>("Slots");
        slotColumn2.setCellValueFactory(new PropertyValueFactory<>("maxStudents"));

        courseNotRegister.getColumns().addAll(idColumn, nameColumn, creditsColumn, slotColumn);
        List<Course> list1 = new JdbcCourseRepository().findCourseNotRegisterByFaculty();
        System.out.println(list1);
        courseNotRegister.setItems(FXCollections.observableArrayList(list1));
        
        courseRegisted.getColumns().addAll(idColumn2, nameColumn2, creditsColumn2, slotColumn2);
        List<Course> list2 = new JdbcCourseRepository().findCourseRegisteredByFacultyId(session.getId());
        System.out.println(list2);
        courseRegisted.setItems(FXCollections.observableArrayList(list2));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editInfoPane.setVisible(false);
        coursePane.setVisible(false);
    }

    @FXML
    private void registerPressed() {
        int result = new JdbcFacultyRepository().registerCourse(this.session.getId(), courseId.getText());
        if (result == 1) {
            registerCompleted.setVisible(true);
            registerFailed.setVisible(false);
            showCourseList();
        } else {
            registerCompleted.setVisible(false);
            registerFailed.setVisible(true);
        }
        courseId.setText("");
        withdrawCompleted.setVisible(false);
        withdrawFailed.setVisible(false);
    }

    @FXML
    private void withdrawPressed() {
        int result = new JdbcFacultyRepository().withDrawCourse(this.session.getId(), courseId.getText());
        if (result == 1) {
            withdrawCompleted.setVisible(true);
            withdrawFailed.setVisible(false);
            showCourseList();
        } else {
            withdrawCompleted.setVisible(false);
            withdrawFailed.setVisible(true);
        }
        courseId.setText("");
        registerCompleted.setVisible(false);
        registerFailed.setVisible(false);
    }

    @FXML
    void cancelEditPressed() {
        nameStudentEdit.setText("");
        addressStudentEdit.setText("");
        phoneStudentEdit.setText("");
        emailStudentEdit.setText("");
        classStudentEdit.setText("");
        saveCompletedEdit.setVisible(false);
        saveFailedEdit.setVisible(false);
    }

    @FXML
    private void saveEditPressed() {
        this.session.setFullName(nameStudentEdit.getText());
        this.session.setAddress(addressStudentEdit.getText());
        this.session.setPhoneNumber(phoneStudentEdit.getText());
        this.session.setEmail(emailStudentEdit.getText());
        this.session.setDepartment(classStudentEdit.getText());
        int result = new JdbcFacultyRepository().update(this.session);
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
        classStudentEdit.setText(this.session.getDepartment());
    }

    @FXML
    private void logoutButtonPressed() {
        try {
            Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/main/view/login.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Faculty getSession() {
        return session;
    }

    public void setSession(Faculty session) {
        this.session = session;
        this.id.setText(session.getId());
        this.name.setText(session.getFullName());
    }
}
