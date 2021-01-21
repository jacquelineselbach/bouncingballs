package at.ac.fhcampuswien.bouncingballs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/* javafx.application < openjdk-15 > - called when the application is starting. */

public class App extends Application {

    /* start method is the main entry point for all JavaFX applications - must be overridden. */
    @Override
    public void start(Stage primaryStage) {
        try {
            // set up of the start scene
            FXMLLoader loader = new FXMLLoader();
            Parent root = FXMLLoader.load(getClass().getResource("/uiStart.fxml"));
            primaryStage.setScene(new Scene(root));

            // set up of the stage
            primaryStage.setResizable(false); // prevents window from resizing
            primaryStage.setTitle("Bouncing Balls"); // sets title of the scene
            primaryStage.getIcons().add(new Image("images/bb-logo.png")); // gets the logo
            primaryStage.show(); // shows the actual window

            /* Exception e indicates any kind of conditions that a reasonable application might want to catch */
        } catch (Exception e) {

            /* printStackTrace() method of throwable class prints the throwable along with other details
               like the line number and class name where the exception occurred.*/
            e.printStackTrace();

            /* java.lang.System.exit() method terminates running java virtual machine and takes a status code.
               A non-zero value of status code is generally used to indicate abnormal termination. */
            System.exit(1);
        }
    }

    public static void main (String[]args) {

        /* launches a standalone application and is typically called from the main method().
           It must not be called more than once or an exception will be thrown. */
        launch(args);
    }
}

