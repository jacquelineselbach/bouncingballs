package at.ac.fhcampuswien.bouncingballs;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
The uiInfoController class is in charge of the actions of the buttons and the look of START window.
**/


public class uiStartController {


    @FXML
    private Button startButton;

    @FXML
    private Button infoButton;

    @FXML
    private Button aboutusButton;

    @FXML
    protected void startConf() {

        Stage startStage = (Stage)startButton.getScene().getWindow();
        ((Stage)startButton.getScene().getWindow()).close();

       try {
            // set up of the scene
            Parent root = FXMLLoader.load(getClass().getResource("/uiSettings.fxml"));
            Scene scene = new Scene(root);

            // set up of the stage
            startStage.setTitle("Bouncing Balls - SET UP YOUR BALLS!");
            startStage.setResizable(false);
            startStage.getIcons().add(new Image("images/bb-logo.png"));
            startStage.setScene(scene);
            startStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void info() {

        Stage infoStage = (Stage) infoButton.getScene().getWindow();
        ((Stage)infoButton.getScene().getWindow()).close();

        try {

            // set up of the scene
            Parent root = FXMLLoader.load(getClass().getResource("/uiInfo.fxml"));
            Scene scene = new Scene(root);

            // set up of the stage
            infoStage.setTitle("Bouncing Balls - INFORMATION");
            infoStage.setResizable(false);
            infoStage.setScene(scene);

            infoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit() {
    System.exit(0);
    }

    public void aboutus() {

        Stage aboutusStage = (Stage) aboutusButton.getScene().getWindow();
        ((Stage)aboutusButton.getScene().getWindow()).close();

        try {
            // set up of the scene
            Parent root = FXMLLoader.load(getClass().getResource("/uiAboutUs.fxml"));
            Scene scene = new Scene(root);

            // set up of the stage
            aboutusStage.setTitle("Bouncing Balls - ABOUT US");
            aboutusStage.setResizable(false);
            aboutusStage.setScene(scene);
            aboutusStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
