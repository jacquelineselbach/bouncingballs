package at.ac.testballsfx;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class SimulationController {

    @FXML
    Pane area;

    @FXML
    Button startButton;

    @FXML
    Button stopButton;

    @FXML
    Button stepButton;

    @FXML
    Slider sizeSlider;

    @FXML
    Slider recoverySlider;

    @FXML
    Slider distanceSlider;

    @FXML
    TextField tickText;


    Simulation simulation;

    private Movement clock;


    private class Movement extends AnimationTimer {


        private long FRAMES_PER_SEC = 50L; // 50 Frames per second
        private long INTERVAL = 1000000000L / FRAMES_PER_SEC;
        private long last = 0;

        @Override
        public void handle(long now) {
            if (now - last > INTERVAL) {
                step();
                last = now;
            }
            // next one, how long has it been since last one / when interval gets big enough, calls action
        }
    }

    @FXML
    public void  initialize(){

        // Slider needs Listener to acknowledge changes and set Method / set size (what to do)

        sizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setSize(); // encapsulate in method
            }
        });

        recoverySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setRecovery(); // encapsulate in method
            }
        });

        distanceSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setDistance(); // encapsulate in method
            }
        });

        clock = new Movement();
        area.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null )));

    }

    @FXML
    public void reset(){
        stop();
        area.getChildren().clear(); // clear scene before reset
        simulation = new Simulation(area, 300);
    }

    public void setSize () {
        Ball.radius = (int)sizeSlider.getValue();
        simulation.draw();
    }

    public void setRecovery () {
        Ball.healtime = 50 * (int)recoverySlider.getValue(); // because healtime slider handles seconds, we need to multiply by 50
        simulation.draw();
    }

    public void setDistance(){
        Ball.distance = (int)distanceSlider.getValue();
    }

    @FXML
    public void start() {
        clock.start();
        disableButtons(true, false, true);
    }
    
    @FXML

    public void stop() {
        clock.stop();
        disableButtons(false, true, false);
    }

    @FXML
    public void step() {
        simulation.move();
        simulation.healing();
        simulation.resolveCollisions();
        simulation.draw();
    }

    public void disableButtons(boolean start, boolean stop, boolean step) {
        startButton.setDisable(start);
        stopButton.setDisable(stop);
        stepButton.setDisable(step);
    }
}
