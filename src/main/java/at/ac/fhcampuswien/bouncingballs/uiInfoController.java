package at.ac.fhcampuswien.bouncingballs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
The uiInfoController class is in charge of the actions of the buttons and the look of the INFO page.
**/
public class uiInfoController {

    @FXML
    private Button backButton;
    @FXML
    private Button startButton;

    @FXML
    public void back(ActionEvent actionEvent) {

        Stage backStage = (Stage)backButton.getScene().getWindow();
        ((Stage)backButton.getScene().getWindow()).close();

        try {
            // set up of the scene
            Parent root = FXMLLoader.load(getClass().getResource("/uiStart.fxml"));
            backStage.setScene(new Scene(root));

            // set up of the stage
            backStage.setResizable(false); // prevents window from resizing
            backStage.setTitle("Bouncing Balls"); // sets title of the scene
            backStage.getIcons().add(new Image("images/bb-logo.png")); // gets the logo
            backStage.show(); // shows the current window

        /*
        Exception e indicates any kind of conditions that a reasonable application might want to catch
        */

        } catch (Exception e) {

            /*
            printStackTrace() method of throwable class prints the throwable along with other details
            like the line number and class name where the exception occurred.
            */
            e.printStackTrace();

            /*
            java.lang.System.exit() method terminates running java virtual machine and takes a status code.
            A non-zero value of status code is generally used to indicate abnormal termination.
            */

            System.exit(1);
        }
    }

    public void startConf(ActionEvent actionEvent) {
        Stage backStage = (Stage)startButton.getScene().getWindow();
        ((Stage)startButton.getScene().getWindow()).close();

        try {

            // set up of the scene
            Parent root = FXMLLoader.load(getClass().getResource("/uiSettings.fxml"));
            backStage.setScene(new Scene(root));

            // set up of the stage
            backStage.setResizable(false); // prevents window from resizing
            backStage.setTitle("Bouncing Balls - SET UP YOUR BALLS!"); // sets title of the scene
            backStage.show(); // shows the current window

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
