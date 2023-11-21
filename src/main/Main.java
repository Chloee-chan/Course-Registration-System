package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    
    private static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/main/view/login.fxml"));
            Scene scene = new Scene(root);
            mainStage = new Stage();
            mainStage.setTitle("Login");
            mainStage.setScene(scene);
            mainStage.getIcons().add(new Image("/resources/image/sed.gif"));
            mainStage.setResizable(false);
            mainStage.show();
            primaryStage = mainStage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getMainStage() {
        return mainStage;
    }
}
