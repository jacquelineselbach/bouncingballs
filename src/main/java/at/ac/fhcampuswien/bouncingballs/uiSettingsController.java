package at.ac.fhcampuswien.bouncingballs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

/**
The uiSettingsController class is in charge of the actions on the settings (SET UP YOUR BALLS!) page.
Here you find the setters and getters that are used in other classes to activate the different modes.
**/

public class uiSettingsController implements Initializable { // controller initialization interface

    // Every option gets a boolean value
    private static boolean optionNormal, optionSocialDist, optionLockdown, optionLockdownANDsocialDist;

    // private SimulationController sc;

    @FXML
    private RadioButton rbNormal, rbSocialDist, rbLockdown, rbLockdownANDsocialDist;

    @FXML
    private Button startButton, backButton;


    // 'initialize' selects a series of predefined boolean values (setProperties) to activate the selected mode

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       standardValues();
    }

    public void modeNormal() {
        setProperties(true,false,false, false);
        debugOutput(optionNormal,optionSocialDist,optionLockdown,optionLockdownANDsocialDist);
    }

    public void modeSocialDist() {
        setProperties(false, true, false, false);
        debugOutput(optionNormal,optionSocialDist,optionLockdown,optionLockdownANDsocialDist);
    }

    public void modeLockdown() {
        setProperties(false, false, true, false);
        debugOutput(optionNormal,optionSocialDist,optionLockdown,optionLockdownANDsocialDist);
    }

    public void modeLockdownANDsocialDist() {
        setProperties(false, false, false, true);
        debugOutput(optionNormal,optionSocialDist,optionLockdown,optionLockdownANDsocialDist);
    }

    // Back Button Method
    public void goBack() {

        Stage backStage = (Stage)backButton.getScene().getWindow();
        ((Stage) backButton.getScene().getWindow()).close();

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

    // Reset Button Method
    /*public void reset(ActionEvent actionEvent) {
        standardValues();
    }*/

    // Start Button Method and actual visual simulation
    public void letsStart() {

        ((Stage)startButton.getScene().getWindow()).close();
        // sim stage for IF Command
        Stage simulationStage = new Stage();

        try {

            // set up of the scene
            FXMLLoader loader = new FXMLLoader();
            BorderPane root = loader.load(getClass().getResource("/simulationGUI.fxml").openStream());
            simulationStage.setScene(new Scene(root));

            // set up of the stage
            simulationStage.setResizable(false); // prevents window from resizing
            simulationStage.setTitle("Bouncing Balls - Simulation"); // sets title of the scene
            simulationStage.getIcons().add(new Image("images/bb-logo.png")); // gets the logo
            simulationStage.show(); // shows the simulation window

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    // Set Properties to Buttons and Options
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
        setProperties(true,false,false,false);
        debugOutput(optionNormal,optionSocialDist,optionLockdown,optionLockdownANDsocialDist);
    }

    // a little helper, to see the attribute values on the console
    private void debugOutput(boolean normal, boolean socialDist, boolean lockdown, boolean ldANDsd){
        System.out.println("DEBUG: normal: " + normal + " -- Social: " + socialDist + " -- Lock: " + lockdown + " -- Lock & Social: " + ldANDsd);
    }

}
