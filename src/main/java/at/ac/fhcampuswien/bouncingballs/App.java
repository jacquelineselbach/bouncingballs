package at.ac.fhcampuswien.bouncingballs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();

            Parent root = loader.load(getClass().getResource("/uiStart.fxml"));
            primaryStage.setScene(new Scene(root));

            primaryStage.setResizable(false);   // prevents window from resizing
            primaryStage.setTitle("Bouncing Balls"); // sets title of the scene
            primaryStage.getIcons().add(new Image("images/bb-logo.png"));
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main (String[]args) {
        launch(args);
    }
}

