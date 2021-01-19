package at.ac.fhcampuswien.bouncingballs;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

/*This class defines the characteristics of the balls such as
 //size, movement, speed, and status (dependent of the outcome).
 Also the method checkOS was implemented as a countermeasure due to discrepancies
 in the speed of movement of the balls in different operating systems.
*/

public class Ball {
    Random random = new Random();
    private Circle c; // draws circles on the screen
    private Pane area; // draws area on the screen

    // positional variables and size of balls
    private double x; // position on x-axis
    private double y; // position on y-axis
    public static int radius = 5;

    // speed and direction variables
    private double SPEED = 1;

    public void setSPEED (double speed){
        SPEED = speed;
    }
    private double dx;
    private double dy;

    // variables regarding sickness and health
    private State state; // measured in frames -> target fps: 60 -> recovery duration = 7 sec. only used with frame limiter
    public final static int healtime = 900;
    private int sicktime = 0;

    public Ball(State state, Pane area) {
        this.state = state;
        this.area = area;
        c = new Circle(radius, state.getColor());

        // creates "safespace" around balls if social distancing is active
        if (uiSettingsController.getoptSocialDist() == true || uiSettingsController.getoptLockdownANDsocialDist() == true){
            c.setStroke(Color.WHITE);
            c.setStrokeWidth(3);
        }

        // random starting positions 5 times radius away border in every direction
        x = radius * 5 + random.nextDouble() * (area.getWidth() - radius * 10);
        y = radius * 5 + random.nextDouble() * (area.getHeight() - radius * 10);

        // random starting directions measured in radians
        double direction = random.nextDouble() * 2 * Math.PI;
        dx = Math.sin(direction);
        dy = Math.cos(direction);

        // draws circles in the area
        area.getChildren().add(c);

        // Get OS info and Setup SPEED for OSX
        checkOS();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        c.setFill(state.getColor());
    }

    public void move() {
        // if x/y get less then 0 Or greater than width/ height balls should bounce back from the wall
        if (x <= radius || x >= (area.getWidth()-radius)){
            dx *= -1;
            x += dx * SPEED;
        }
        else if (y <= radius || y >= (area.getHeight()-radius)){
            dy *= -1;
            y += dy * SPEED;
        }
        else{
            x += dx * SPEED;
            y += dy * SPEED;
        }
    }

    // balls are drawn
    public void draw() {
        c.setRadius(radius);
        c.setTranslateX(x);
        c.setTranslateY(y);
    }

    public void outcome(double deathrate) {
        double probability;
        if (state == State.INFECTED) {
            sicktime++;
            probability = random.nextDouble();

            if (sicktime >= healtime && probability > deathrate/100) {
                setState(State.RECOVERED);

            } else if(sicktime >= healtime && probability <= deathrate/100) {
                setState(State.DEAD);
            }
        }
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    public double getAbsDx(){
        return Math.abs(dx);
    }
    public double getAbsDy(){
        return Math.abs(dy);
    }

    public void bounceX(){
        dx *= -1;
    }
    public void bounceY(){
        dy *= -1;
    }

    // checks if running host is OS X (macOS), because on that OS the balls are too slow :(
    private void checkOS(){
        if(System.getProperty("os.name").contains("OS X") || System.getProperty("os.name").contains("macOS")){
            SPEED = 1.2;
        }
    }
}