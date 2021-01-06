package at.ac.fhcampuswien.bouncingballs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class uiAboutUsController {
    @FXML
    private Button backButton;
    @FXML
    private Button startButton;

    @FXML
    public void back(ActionEvent actionEvent) {
        Stage backStage = (Stage)backButton.getScene().getWindow();
        ((Stage)backButton.getScene().getWindow()).close();
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/uiStart.fxml"));
            backStage.setScene(new Scene(root));

            backStage.setResizable(false);   // This prevents window from resizing
            backStage.setTitle("Bouncing Balls"); // sets title of the scene
            backStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void startConf(ActionEvent actionEvent) {
        Stage backStage = (Stage)startButton.getScene().getWindow();
        ((Stage)startButton.getScene().getWindow()).close();
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/uiSettings.fxml"));
            backStage.setScene(new Scene(root));

            backStage.setResizable(false);   // This prevents window from resizing
            backStage.setTitle("Bouncing Balls - Setup your Simulation"); // sets title of the scene
            backStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}