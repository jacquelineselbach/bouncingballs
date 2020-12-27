package at.ac.testballsfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {


    @Override
    public void start(Stage primaryStage) {
        try {

            FXMLLoader loader = new FXMLLoader();
            BorderPane root = loader.load(getClass().getResource("/simulationGUI.fxml").openStream());
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);   // This prevents window from resizing
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

        public static void main (String[]args) {
            launch(args);
        }
    }

