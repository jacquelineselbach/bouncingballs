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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.EnumMap;


public class SimulationController {

    @FXML
    Pane area;

    @FXML
    Button startButton;

    @FXML
    Button stopButton;

    @FXML
    Button stepButton;

    // @FXML
    // Slider sizeSlider;

    @FXML
    Slider recoverySlider;

    @FXML
    Slider distanceSlider;

    @FXML
    Pane chart;

    @FXML
    Pane histogram;

    @FXML
    TextField instantsCounter;   // captures of the moment sec



    Simulation simulation;

    EnumMap<State, Rectangle> pot = new EnumMap<State, Rectangle>(State.class); // enum map is mapping from state to rectangle - finds how much balls are infected, healthy, etc.. / pot = population over time

    private Movement clock;



    private class Movement extends AnimationTimer {


        private long FRAMES_PER_SEC = 80L; // 80 Frames per second
        private long INTERVAL = 1000000000L / FRAMES_PER_SEC;

        private long last = 0;
        private int instants = 0;

        /**
         * This method needs to be overridden by extending classes. It is going to
         * be called in every frame while the {@code AnimationTimer} is active.
         *
         * @param now
         *            The timestamp of the current frame given in nanoseconds. This
         *            value will be the same for all {@code AnimationTimers} called
         *            during one frame.
         */


        @Override
        public void handle(long now) {
            if (now - last > INTERVAL) {
                step();
                last = now;
                instant();
                instantsCounter.setText("" + instants); // print the instances
            }
            // next one, how long has it been since last one / when interval gets big enough, calls action
        }
        /**
         * Starts the {@code AnimationTimer}. Once it is started, the
         * {@link #handle(long)} method of this {@code AnimationTimer} will be
         * called in every frame.
         *
         * The {@code AnimationTimer} can be stopped by calling {@link #stop()}.
         */

        public int getInstants() {
            return instants;
        }

        public void resetInstants() {
            instants = 0; // resets the instants / timesteps to zero after START/RESET
        }

        public void instant(){
            instants++;
        }
    }

    @FXML
    public void  initialize(){

        // Slider needs Listener to acknowledge changes and set Method / set size (what to do)

        /* sizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setSize(); // encapsulate in method
            }
        }); */

        recoverySlider.valueProperty().addListener((observable, oldValue, newValue) -> { // replaced with lambda (IntelliJ did that automatically)
            setRecovery(); // encapsulate in method
        });

        distanceSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            setDistance(); // encapsulate in method
        });

        clock = new Movement();
        area.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null )));

    }

    @FXML
    public void reset(){
        stop();
        clock.resetInstants();
        instantsCounter.setText("" + clock.getInstants());
        area.getChildren().clear(); // clear scene before reset
        histogram.getChildren().clear(); // clear scene for histogram
        chart.getChildren().clear(); // clear scene for chart
        simulation = new Simulation(area, 100, 20);

        int offset = 0;

        for (State s : State.values()) { // fill new rectangles after hitting reset/start
            Rectangle r = new Rectangle(60,0, s.getColor()); // draws rectangle in color of each state for histogram
            r.setTranslateX(offset); // to draw the rectangles next to each other
            offset += 65;
            pot.put(s, r); // population over time: put in s state and r rectangle
            histogram.getChildren().add(r); // showing rectangles on the pane
        }

        setRecovery();
        setDistance();
        drawChart();

        
    }

    /* public void setSize () {
        Ball.radius = (int)sizeSlider.getValue();
        simulation.draw();
    } */

    public void setRecovery () {
        Ball.healtime = 80 * (int)recoverySlider.getValue(); // because healtime slider handles seconds, we need to multiply by 80
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
    public void step() { // single time steps
        simulation.move();
        simulation.healing();
        simulation.resolveCollisions();
        simulation.draw();
        drawChart(); // as every time step goes the chart will be drawn
        clock.instant();
        instantsCounter.setText("" + clock.getInstants());

    }


    public void disableButtons(boolean start, boolean stop, boolean step) {
        startButton.setDisable(start);
        stopButton.setDisable(stop);
        stepButton.setDisable(step);
    }


    // creates a map between states and integers and builds up the histogram in terms of numbers
    public void drawChart(){
        EnumMap<State, Integer> currentPopulation = new EnumMap<State, Integer>(State.class); // every timestep we are going to calculate in which state balls are
        for (Ball b : simulation.getBalls()) {
            if(!currentPopulation.containsKey(b.getState())){ // have we seen the state yet?
                currentPopulation.put(b.getState(), 0);
            }
            currentPopulation.put(b.getState(), 1 + currentPopulation.get(b.getState()));
        }

        // setting heights for current populations / drawing the actual rectangles for histogram
        for (State state : pot.keySet()) {
            if (currentPopulation.containsKey(state)){ // if there are 50 balls infected, the height is going to be 50
                pot.get(state).setHeight(currentPopulation.get(state));
                pot.get(state).setTranslateY(30 + 120 - currentPopulation.get(state)); // 300 because of inital population of 130


                // line chart
                Circle c = new Circle(1,state.getColor());
                c.setTranslateX(clock.getInstants() / 5.0);
                c.setTranslateY(120 - currentPopulation.get(state));
                chart.getChildren().add(c);
            }
        }

        if (!currentPopulation.containsKey(State.INFECTED))
            stop();

    }
}
