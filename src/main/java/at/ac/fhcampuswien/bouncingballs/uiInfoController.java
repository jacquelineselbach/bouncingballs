package at.ac.fhcampuswien.bouncingballs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/*
The uiInfoController class is in charge of the actions of the buttons and the look of the INFO page.
*/
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

            Parent root = FXMLLoader.load(getClass().getResource("/uiStart.fxml"));
            backStage.setScene(new Scene(root));

            backStage.setResizable(false); // prevents window from resizing
            backStage.setTitle("Bouncing Balls"); // sets title of the scene
            backStage.getIcons().add(new Image("images/bb-logo.png"));
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

            backStage.setResizable(false); // prevents window from resizing
            backStage.setTitle("Bouncing Balls - SET UP YOUR BALLS!"); // sets title of the scene
            backStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
