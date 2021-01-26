package at.ac.fhcampuswien.bouncingballs;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

/**
This class defines the characteristics of the balls such as
size, movement, speed, and status (dependent of the outcome).
Also the method checkOS was implemented as a countermeasure due to discrepancies
in the speed of movement of the balls in different operating systems.
**/

public class Ball {

    Random random = new Random();

    private final Circle c; // draws circles on the screen
    private final Pane area; // draws area on the screen

    // positional variables and size of balls
    private double x; // position on x-axis
    private double y; // position on y-axis
    public static final int radius = 5; // radius of the balls

    // speed and direction variables
    private double speed = 1;
    private double dx;
    private double dy;

    // variables regarding sickness and health
    private State state;
    public final static int healtime = 900;
    private int sicktime = 0;

    // ball constructor
    public Ball(State state, Pane area) {
        this.state = state;
        this.area = area;
        c = new Circle(radius, state.getColor());

        // creates "safespace" around balls if social distancing is active
        if (uiSettingsController.getoptSocialDist() || uiSettingsController.getoptLockdownANDsocialDist()){
            c.setStroke(Color.WHITE);
            c.setStrokeWidth(2);
        }

        // random starting positions one radius away from the border in every direction
        x = radius + random.nextDouble() * (area.getWidth() - radius * 2);
        y = radius + random.nextDouble() * (area.getHeight() - radius * 2);

        // random starting directions measured in radians
        double direction = random.nextDouble() * 2 * Math.PI;
        dx = Math.sin(direction);
        dy = Math.cos(direction);

        // draws circles in the area
        area.getChildren().add(c);

        // Get OS info and Setup SPEED for OSX
        checkOS();
    }

    // getter and setter for state
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        c.setFill(state.getColor());
    }

    /*
    this method facilitates movement of balls and lets them bounce off walls
    if x/y coordinates of a given ball become less than its radius
    or greater than the maximum width/height of the area minus its radius
    the ball bounces by reversing direction on the corresponding axis
    otherwise regular movement occurs
    */

    public void move() {
        if (x < radius || x > (area.getWidth() - radius)){
            dx *= -1;
            x += dx * speed;
        }
        else if (y < radius || y > (area.getHeight() - radius)){
            dy *= -1;
            y += dy * speed;
        }
        else{
            x += dx * speed;
            y += dy * speed;
        }
    }

    /*
    draw() method is where the actual balls are drawn on the scene with javafx.scene.shape
    translated with javafx.scene.Node - base class for scene graph nodes.
    */

    public void draw() {
        c.setRadius(radius); // sets radius of circle c
        c.setTranslateX(x); // defines the x coordinate of the translation
        c.setTranslateY(y); // defines the y coordinate of the translation
    }

    /*
    outcome(double deathrate) determines if an infected ball is recovered or dead
    */

    public void outcome(double deathrate) {

        double probability;

        if (state == State.INFECTED) {
            sicktime++;
            probability = random.nextDouble();

            if (sicktime >= healtime && probability > deathrate/100) {
                setState(State.RECOVERED);
            }
            else if(sicktime >= healtime && probability <= deathrate/100) {
                setState(State.DEAD);
            }
        }
    }

    /*
    setter for speed, getters for x, y and dx, dy
    */

    public void setSpeed (double speed) {
        speed = speed;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public double getAbsDx() {
        return Math.abs(dx);
    }
    public double getAbsDy() {
        return Math.abs(dy);
    }

    /*
    bounceX() and bounceY() changes direction/ bounce to exact opposite.
    */

    public void bounceX() {
        dx *= -1;
    }
    public void bounceY() {
        dy *= -1;
    }

    /*
    checkOS() checks if running host is OS X (macOS), because on that OS the balls are too slow :(
    */

    private void checkOS() {
        if(System.getProperty("os.name").contains("OS X") || System.getProperty("os.name").contains("macOS")){
            speed = 1.2;
        }
    }
}