package at.ac.fhcampuswien.bouncingballs;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class uiStartController {


    //Set Start/Info Buttons
    @FXML
    private Button startButton;

    @FXML
    private Button infoButton;

    @FXML
    protected void startConf(ActionEvent actionEvent) {
        Stage startStage = (Stage)startButton.getScene().getWindow();
        ((Stage)startButton.getScene().getWindow()).close();
       try {
            Parent root = FXMLLoader.load(getClass().getResource("/uiSettings.fxml"));
            Scene scene = new Scene(root);

            startStage.setTitle("Bouncing Balls - Setup your Simulation");
            startStage.setResizable(false);
            startStage.setScene(scene);

            startStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void info(ActionEvent actionEvent) {
        Stage infoStage = (Stage) infoButton.getScene().getWindow();
        ((Stage)infoButton.getScene().getWindow()).close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/uiInfo.fxml"));
            Scene scene = new Scene(root);

            infoStage.setTitle("Bouncing Balls - Info");
            infoStage.setResizable(false);
            infoStage.setScene(scene);

            infoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit(ActionEvent actionEvent) {
    System.exit(0);
    }
}
