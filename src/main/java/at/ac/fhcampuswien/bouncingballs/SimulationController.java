package at.ac.fhcampuswien.bouncingballs;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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

    @FXML
    Pane chart;

    @FXML
    Pane histogram;

    // @FXML
    // TextField instantsCounter;   // captures of the moment sec

    Simulation simulation;

    EnumMap<State, Rectangle> pot = new EnumMap<State, Rectangle>(State.class); // enum map is mapping from state to rectangle - finds how much balls are infected, healthy, etc.. / pot = population over time


    private Movement clock;

    private class Movement extends AnimationTimer {


        @Override
        public void handle(long now) {

                step();
                instant();
                // instantsCounter.setText("" + instants); // print the instances
        }

        private int instants = 0;

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
    public void  initialize() {

        clock = new Movement();
        area.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null )));
    }

    @FXML
    public void reset() {

        stop();
        clock.resetInstants();
        // instantsCounter.setText("" + clock.getInstants());
        area.getChildren().clear(); // clear scene before reset
        histogram.getChildren().clear(); // clear scene for histogram
        chart.getChildren().clear(); // clear scene for chart
        simulation = new Simulation(area, 300);

        int offset = 0;

        for (State s : State.values()) { // fill new rectangles after hitting reset/start
            Rectangle r = new Rectangle(60,0, s.getColor()); // draws rectangle in color of each state for histogram
            r.setTranslateX(offset); // to draw the rectangles next to each other
            offset += 70;
            pot.put(s, r); // population over time: put in s state and r rectangle
            histogram.getChildren().add(r); // showing rectangles on the pane
        }

        drawChart();
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
        // instantsCounter.setText("" + clock.getInstants());
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
            if(!currentPopulation.containsKey(b.getState())){
                currentPopulation.put(b.getState(), 0);
            }
            currentPopulation.put(b.getState(), 1 + currentPopulation.get(b.getState()));
        }

        // setting heights for current populations / drawing the actual rectangles for histogram
        for (State state : pot.keySet()) {
            if (currentPopulation.containsKey(state)){ // if there are 50 balls infected, the height is going to be 50
                pot.get(state).setHeight(currentPopulation.get(state));
                pot.get(state).setTranslateY( 100 + 200 - currentPopulation.get(state));

                // line chart
                Circle c = new Circle(1,state.getColor());
                c.setTranslateX(clock.getInstants() / 5.0);
                c.setTranslateY(300 - currentPopulation.get(state));
                chart.getChildren().add(c);
            }
        }

        if (!currentPopulation.containsKey(State.INFECTED))
            stop(); // stops the animation when there is no more infected ball

    }
}
