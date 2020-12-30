package at.ac.fhcampuswien.bouncingballs;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.EnumMap;

public class SimulationController {
    private class BBAnimationTimer extends AnimationTimer {
        private int instants = 0; // instants are used for charts

        public int getInstants() {
            return instants;
        }
        public void resetInstants() {
            instants = 0; // resets the instants / timesteps to zero after START/RESET
        }
        public void incrementInstants(){
            instants++;
        }
        @Override
        public void handle(long now) {
            step();
            incrementInstants();
        }
    }

    @FXML
    private Pane area;
    @FXML
    private Button resetButton;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button stepButton;
    @FXML
    private Pane chart;
    @FXML
    private Pane histogram;

    private BallsController ballsController;
    private BBAnimationTimer timer;
    private final int populationSize = 300;
    boolean resetswitch = true;

    private EnumMap<State, Rectangle> pot = new EnumMap<>(State.class);
    // enum map is mapping from state to rectangle for creating the charts
    // finds how much balls are infected, healthy, etc.. / pot = population over time

    @FXML
    public void initialize() {
        timer = new BBAnimationTimer();
        area.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null )));
        disableButtons(true,false, true, true);
    }

    @FXML
    public void step() { // single time steps
        ballsController.moveBalls();
        ballsController.resolveInfections();
        ballsController.resolveCollisions();
        ballsController.draw();
        drawChart(); // as every time step goes the chart will be drawn
    }

    @FXML
    public void start() {
        if(ballsController == null || resetswitch) {
            ballsController = new BallsController(area, populationSize); // initialize new Simulation
            setupCharts();
            drawChart();
        }
        timer.start();
        resetswitch = false;
        disableButtons(false,true, false, true);
    }

    @FXML
    public void reset() {
        stop();
        timer.resetInstants(); // resets instants for chart generation before reset
        area.getChildren().clear(); // clear scene before reset
        histogram.getChildren().clear(); // clear scene for histogram
        chart.getChildren().clear(); // clear scene for chart
        resetswitch = true;
        disableButtons(true,false, true, true);
    }

    @FXML
    public void stop() {
        timer.stop();
        disableButtons(false, false, true, false);
    }

    public void disableButtons(boolean reset, boolean start, boolean stop, boolean step) {
        resetButton.setDisable(reset);
        startButton.setDisable(start);
        stopButton.setDisable(stop);
        stepButton.setDisable(step);
    }

    public void setupCharts(){
        int offset = 0;
        for (State s : State.values()) { // fill new rectangles after hitting reset/start
            Rectangle r = new Rectangle(60,0, s.getColor()); // draws rectangle in color of each state for histogram
            r.setTranslateX(offset); // to draw the rectangles next to each other
            offset += 70;
            pot.put(s, r); // population over time: put in s state and r rectangle
            histogram.getChildren().add(r); // showing rectangles on the pane
        }
    }

    // creates a map between states and integers and builds up the histogram in terms of numbers
    public void drawChart() {
        EnumMap<State, Integer> currentPopulation = new EnumMap<>(State.class); // every timestep we are going to calculate in which state balls are
        for (Ball b : ballsController.getBalls()) {
            if(!currentPopulation.containsKey(b.getState())) {
                currentPopulation.put(b.getState(), 0);
            }
            currentPopulation.put(b.getState(), 1 + currentPopulation.get(b.getState()));
        }

        // setting heights for current populations / drawing the actual rectangles for histogram
        for (State state : pot.keySet()) {
            if (currentPopulation.containsKey(state)) { // if there are 50 balls infected, the height is going to be 50
                pot.get(state).setHeight(currentPopulation.get(state));
                pot.get(state).setTranslateY(populationSize - currentPopulation.get(state));

                // line chart
                Circle c = new Circle(1,state.getColor());
                c.setTranslateX(timer.getInstants() / 5.0);
                c.setTranslateY(populationSize - currentPopulation.get(state));
                chart.getChildren().add(c);
            }
        }

        if (!currentPopulation.containsKey(State.INFECTED))
            stop(); // stops the animation when there is no more infected ball
    }
}
