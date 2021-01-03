package at.ac.fhcampuswien.bouncingballs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();

            Parent root = loader.load(getClass().getResource("/uiStart.fxml"));
            primaryStage.setScene(new Scene(root));
        //    root.getStylesheets().add("stylesheet.css"); // adding css stylesheet

            primaryStage.setResizable(false);   // This prevents window from resizing
            primaryStage.setTitle("Bouncing Balls"); // sets title of the scene
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    /*
      // Starts just the Simulation!
      try {
            FXMLLoader loader = new FXMLLoader();
            BorderPane root = loader.load(getClass().getResource("/simulationGUI.fxml").openStream());
            primaryStage.setScene(new Scene(root));
            root.getStylesheets().add("stylesheet.css"); // adding css stylesheet

            primaryStage.setResizable(false);   // This prevents window from resizing
            primaryStage.setTitle("Bouncing Balls"); // sets title of the scene
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    } */


    }

        public static void main (String[]args) {
            launch(args);
        }
    }

