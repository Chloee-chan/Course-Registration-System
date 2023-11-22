package main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.Main;
import main.dao.JdbcAccountRepository;
import main.dao.JdbcStudentRepository;
import main.model.Account;
import main.model.Student;
import main.repository.AccountRepository;
import main.repository.StudentRepository;

public class LoginController implements Initializable {

    private AccountRepository accountRepository = new JdbcAccountRepository();
    private StudentRepository studentRepository = new JdbcStudentRepository();

    @FXML
    private Text loginError;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button buttonLogin;

    @FXML
    private void pressEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            this.pressLogin();
        }
    }

    @FXML
    private void pressLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Account account = accountRepository.findByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            try {
                if (account.getRole().equals(Account.Role.ADMIN)) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/main/view/admin.fxml"));
                    AnchorPane adminPane = loader.load();
                    AdminController adminController = loader.getController();
                    adminController.setUsername(username);
                    Main.getMainStage().setScene(new Scene(adminPane));
                } else if (account.getRole().equals(Account.Role.STUDENT)) {
                    Student student = studentRepository.findByUsername(username);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/main/view/student.fxml"));
                    AnchorPane studentPane = loader.load();
                    StudentController studentController = loader.getController();
                    studentController.setSession(student);
                    Main.getMainStage().setScene(new Scene(studentPane));
                } else {
                    Main.getMainStage()
                            .setScene(new Scene(FXMLLoader.load(getClass().getResource("/main/view/faculty.fxml"))));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loginError.setVisible(true);
            passwordField.setText("");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginError.setVisible(false);
    }
}
