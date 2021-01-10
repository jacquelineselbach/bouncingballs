package at.ac.fhcampuswien.bouncingballs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class uiSettingsController implements Initializable {

    // EveryOption gets a boolean Value
    private static boolean optionNormal = false, optionSocialDist = false, optionLockdown =  false, optionLockdownANDsocialDist;

    // Sim stage for IF Command
    private Stage simulationStage;

    private SimulationController sc;

    @FXML
    private RadioButton rbNormal, rbSocialDist, rbLockdown, rbLockdownANDsocialDist;

    @FXML
    private Button startButton, backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       standardValues();
    }

    public void modeNormal(ActionEvent actionEvent) {
        setProperties(true,false,false, false);
        debugOutput(optionNormal,optionSocialDist,optionLockdown,optionLockdownANDsocialDist);

    }

    public void modeSocialDist(ActionEvent actionEvent) {
        setProperties(false, true, false, false);
        debugOutput(optionNormal,optionSocialDist,optionLockdown,optionLockdownANDsocialDist);
    }

    public void modeLockdown(ActionEvent actionEvent) {
        setProperties(false, false, true, false);
        debugOutput(optionNormal,optionSocialDist,optionLockdown,optionLockdownANDsocialDist);
    }

    public void modeLockdownANDsocialDist(ActionEvent actionEvent) {
        setProperties(false, false, false, true);
        debugOutput(optionNormal,optionSocialDist,optionLockdown,optionLockdownANDsocialDist);
    }

    // Back Button Method
    public void goBack(ActionEvent actionEvent) {
        Stage backStage = (Stage)backButton.getScene().getWindow();
        ((Stage) backButton.getScene().getWindow()).close();
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

    // Reset Button Method
    public void reset(ActionEvent actionEvent) {
    standardValues();
    }

    // Start Button Method
    public void letsStart(ActionEvent actionEvent) {
       /* if(simulationStage != null)
        {
            simulationStage.close();
        }*/
        // Closes Settings Window
        ((Stage)startButton.getScene().getWindow()).close();
        simulationStage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader();
            BorderPane root = loader.load(getClass().getResource("/simulationGUI.fxml").openStream());
            simulationStage.setScene(new Scene(root));
          //  root.getStylesheets().add("stylesheet.css"); // adding css stylesheet
            simulationStage.setResizable(false);   // This prevents window from resizing
            simulationStage.setTitle("Bouncing Balls - Simulation"); // sets title of the scene

            // -- Hami ist working on this section PLEASE DON'T DELETE FOLLOWING COMMENTS !!!

            //simulationStage.getScene().getWindow().startButton.fire();
            //(Button) simulationStage.getScene().lookup("startbutton").setOnMouseClicked(event -> #start);
            //simulationStage.getScene().lookup("startButton").;

            // -- Hami's section ends here

            simulationStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    //Set Properties to Buttons and Options
    private void setProperties(boolean normal, boolean distance, boolean lockdown, boolean lockdownandsocialdistancing){
        rbNormal.setSelected(normal);
        optionNormal = normal;
        rbSocialDist.setSelected(distance);
        optionSocialDist = distance;
        rbLockdown.setSelected(lockdown);
        optionLockdown = lockdown;
        rbLockdownANDsocialDist.setSelected(lockdownandsocialdistancing);
        optionLockdownANDsocialDist = lockdownandsocialdistancing;
    }

    ///////////////////////////////////////////////////////
    // Getter! PLEASE USE THEM FOR YOUR CLASS!!!!!!
    ///////////////////////////////////////////////////////
   protected static boolean getoptNormal(){
        return optionNormal;
}

    protected static boolean getoptSocialDist(){
        return optionSocialDist;
    }

    protected static boolean getoptLockdown(){
        return optionLockdown;
    }

    protected static boolean getoptLockdownANDsocialDist(){
        return optionLockdownANDsocialDist;
    }

    // Reset Buttons and Options
    private void standardValues(){
        setProperties(true,false,false, false);
        debugOutput(optionNormal,optionSocialDist,optionLockdown,optionLockdownANDsocialDist);
    }
    // A lil Helper, if the Values are setted correctly.
    private void debugOutput(boolean normal, boolean socialDist, boolean lockdown, boolean ldANDsd){
        System.out.println("DEBUG: normal: " + normal + " -- Social: " + socialDist + " -- Lock: " + lockdown + " -- Lock & Social: " + ldANDsd);
    }

}
