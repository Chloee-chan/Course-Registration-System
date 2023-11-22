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
    }

    @FXML
    private void deleteCourseButtonPressed() {
        viewCoursePane.setVisible(false);
        createCoursePane.setVisible(false);
        editCoursePane.setVisible(false);
        deleteCoursePane.setVisible(true);
    }

    @FXML
    private void editCourseButtonPressed() {
        viewCoursePane.setVisible(false);
        createCoursePane.setVisible(false);
        editCoursePane.setVisible(true);
        deleteCoursePane.setVisible(false);
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
