package at.ac.fhcampuswien.bouncingballs;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.EnumMap;

/*
 The class SimulationController consists of all the methods needed for the functionality of the visual
 part of the simulation: e.g animation, buttons, diagrams, etc
 */

public class SimulationController {


    private class BBAnimationTimer extends AnimationTimer {

        /* The class AnimationTimer allows to create a timer, that is called in each frame while it is active.
        An extending class has to override the method handle(long) which will be called in every frame.
        The methods start() and stop() allow to start and stop the timer.
         */

        private int instants = 0; // time instants (single time steps) are needed for histogram and line chart

        public int getInstants() { // getter returning instants
            return instants;
        }
        public void resetInstants() { // resets the instants / timesteps to zero after START/RESET
            instants = 0;
        }
        public void incrementInstants(){ // increments+ the instants
            instants++;
        }

        /*
        EXPERIMENTAL FPS LIMITER
        handle() should get called every frame as of javafx documentation in reality speed differs different systems
        and this doesn't seem to correlate to actual framerate of the systems.
        The if statement added to the handle method should remedy that.
        The 'now'-variable is a timestamp in nanosecond. Unfortunately this could introduce jitter.
        Source: https://stackoverflow.com/questions/30146560/how-to-change-animationtimer-speed
        */

        // private long lastFrame = 0L;

        @Override
        public void handle(long now) {

            // 1 second = 1_000_000_000 nanoseconds, target framerate 60 fps -> update every 16_666_666 nanoseconds
            // if(now - lastFrame >= 16_666_666){ lastFrame = now; }

            step();
            incrementInstants();
        }
    }

    /* @FXML annotation is used to tag nonpublic controller member fields and handler methods for use by FXML markup.*/

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
    private Button settingsButton;
    @FXML
    private Pane chart;
    @FXML
    private Pane histogram;
    @FXML
    private TextField States;


    private BallsController ballsController;
    private BBAnimationTimer timer;

    private final static int populationSize = 250;
    private final static double infectionrate = 100.;
    private final static double deathrate = 10.;

    private static boolean socialdistancing;
    private static boolean lockdown;
    private static boolean lockdownANDsocialDist;

    private boolean resetswitch = true;


    public static double getDeathrate(){
        return deathrate;
    }

    public static double getInfectionrate(){
        if(socialdistancing || lockdownANDsocialDist){
            return infectionrate * 0.5;
        }else{
            return infectionrate;
        }
    }

    /* Enum map is mapping from state to rectangle for creating the histogram.
    Calculates how much balls are infected, healthy, etc..  pot = population over time */

    private EnumMap<State, Rectangle> pot = new EnumMap<>(State.class);


    /* Accessible initialize() method - the FXML loader will call the initialize() method after
    the loading of the FXML document is complete. */

    @FXML
    public void initialize() {

        socialdistancing = uiSettingsController.getoptSocialDist();
        lockdown = uiSettingsController.getoptLockdown();
        lockdownANDsocialDist = uiSettingsController.getoptLockdownANDsocialDist();

        timer = new BBAnimationTimer();

        area.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null )));
        disableButtons(true,false, true, true);
    }

    /* Every single timestep the ball controller class is called to draw and move the balls,
    resolve infections and collisions. */

    @FXML
    public void step() {

        ballsController.moveBalls();
        ballsController.resolveInfections();
        ballsController.resolveCollisions();
        ballsController.draw();

        drawChart(); // as every time step goes the line chart will be drawn
    }

    /* start() method starts the Animation timers. Once it is started, the handle(long) method of this AnimationTimers will
    be called in every frame. The AnimationTimers can be stopped by calling stop(). */

    @FXML
    public void start() {

        if(ballsController == null || resetswitch) {
            ballsController = new BallsController(area, populationSize); // initialize new simulation
            setupCharts();
            drawChart();
        }
        timer.start();
        resetswitch = false;
        System.out.println("Socialdistancing: " + socialdistancing + " / Lockdown: " + lockdown +
                " / Infectionsrate: " + getInfectionrate() + " / Deathrate: " + getDeathrate());
        disableButtons(false,true, false, true);
    }

    /* Stops the AnimationTimers. It can be activated again by calling start(). */

    @FXML
    public void stop() {

        timer.stop();

        disableButtons(false, false, true, false);
    }

    /* reset() method stops the animation, resets the timer and clears all scenes. */

    @FXML
    public void reset() {

        stop();
        timer.resetInstants(); // resets instants for chart generation before reset
        area.getChildren().clear(); // clear scene before reset
        histogram.getChildren().clear(); // clear scene for histogram
        chart.getChildren().clear(); // clear scene for line chart
        States.clear();

        resetswitch = true;

        disableButtons(true,false, true, true);
    }

    /* opensetting() method enables returning to the settings scene. It closes the simulation scene and
    opens the settings stage where the user can decide between simulation properties again. */

    @FXML
    public void opensetting(ActionEvent actionEvent) {
        ((Stage)settingsButton.getScene().getWindow()).close();
        stop();
        Stage startStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/uiSettings.fxml"));
            Scene scene = new Scene(root);

            startStage.setTitle("Bouncing Balls - SET UP YOUR BALLS!");
            startStage.setResizable(false);
            startStage.getIcons().add(new Image("images/bb-logo.png"));
            startStage.setScene(scene);

            startStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* disableButtons() method enables and disables buttons during the simulation -
    e.g. when the play button is active it gets disabled but therefore the pause button gets enabled */

    public void disableButtons(boolean reset, boolean start, boolean stop, boolean step) {
        resetButton.setDisable(reset);
        startButton.setDisable(start);
        stopButton.setDisable(stop);
        stepButton.setDisable(step);
    }

    /* setupCharts() and drawChart() are handling mostly the appearance of the histogram and the linechart.
    Additionally, thanks to the enum map we are able to know when there is none infected ball left and
    therefore - to stop the animation at this exact point. */

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

    /* creates a map between states and integers and builds up the charts in terms of numbers.
    Every timestep we are calculating in which state the balls are in */

    public void drawChart() {

        EnumMap<State, Integer> currentPopulation = new EnumMap<>(State.class);

        for (Ball b : ballsController.getBalls()) {
            if(!currentPopulation.containsKey(b.getState())) {
                currentPopulation.put(b.getState(), 0);
            }
            currentPopulation.put(b.getState(), 1 + currentPopulation.get(b.getState()));
        }

        /* setting heights for current populations / drawing the actual rectangles for histogram.
        For example, if there are 50 balls infected, the height is going to be 50. */

        for (State state : pot.keySet()) {
            if (currentPopulation.containsKey(state)) {
                pot.get(state).setHeight(currentPopulation.get(state));
                pot.get(state).setTranslateY(populationSize - currentPopulation.get(state));

                /* drawing the actual circles for the line chart */

                Circle c = new Circle(1,state.getColor());
                c.setTranslateX(timer.getInstants() / 7.0);
                c.setTranslateY(populationSize - currentPopulation.get(state));
                chart.getChildren().add(c);

            }

        }

        States.setText("" + currentPopulation); // prints the states translated to numbers in the textfield

        if (!currentPopulation.containsKey(State.INFECTED))
            stop(); // stops the animation when there is no more infected ball
    }
}
