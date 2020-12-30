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
        Stage stage = (Stage)startButton.getScene().getWindow();
        try {

            //   Parent root = loader.load(getClass().getResource("/uiStart.fxml"));
            FXMLLoader loader = new FXMLLoader();
            BorderPane root = loader.load(getClass().getResource("/simulationGUI.fxml").openStream());
            //Scene scene = new Scene(root);
            stage.setScene(new Scene(root));
            //    root.getStylesheets().add("stylesheet.css"); // adding css stylesheet

            stage.setResizable(false);   // This prevents window from resizing

            stage.setTitle("Bouncing Balls"); // sets title of the scene

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }


    @FXML
    protected void info(ActionEvent actionEvent) {
        Stage infoStage = (Stage) infoButton.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/uiInfo.fxml"));
            Scene scene = new Scene(root);

            infoStage.setTitle("Bouncing Balls - Info");
            infoStage.setResizable(false);
            infoStage.setScene(scene);
            //stage.toBack();
            infoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit(ActionEvent actionEvent) {
    System.exit(0);
    }
}
