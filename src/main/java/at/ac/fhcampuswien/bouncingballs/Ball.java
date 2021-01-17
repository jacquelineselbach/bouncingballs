package at.ac.fhcampuswien.bouncingballs;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

public class Ball {
    Random random = new Random();
    private Circle c; // draw circles on the screens
    private Pane area; // draw area on the screens

    // positional variables and size of balls
    private double x; // position on x-axis
    private double y; // position on y-axis
    public static int radius = 5;

    // speed and direction variables
    private double SPEED = 4;
    public double getSPEED() { // speed setter und getter für Lockdownmode
        return SPEED;
    } //getter and setter for LockdownMode
    public void setSPEED (double speed){
        SPEED = speed;
    }
    private double dx;
    private double dy;

    // variables regarding sickness and health
    private State state;
    public final static int healtime = 420; // measured in frames -> target fps: 60 -> recovery duration = 7 sec.
    private int sicktime = 0;

    public Ball(State state, Pane area) {
        this.state = state;
        this.area = area;
        c = new Circle(radius, state.getColor());

        // creates "safespace" around balls if social distancing is active
        if (uiSettingsController.getoptSocialDist() == true || uiSettingsController.getoptLockdownANDsocialDist() == true){
            c.setStroke(Color.GREY);
            c.setStrokeWidth(3);
        }

        // random starting positions 5 times radius away border in every direction
        x = radius*5 + random.nextDouble() * (area.getWidth() - radius*10);
        y = radius*5 + random.nextDouble() * (area.getHeight() - radius*10);

        // random starting directions measured in radians
        double direction = random.nextDouble() * 2 * Math.PI;
        dx = Math.sin(direction);
        dy = Math.cos(direction);

        // Get OS info and Setup SPEED for OSX
//        checkOS();
        // area needs to draw circles
        area.getChildren().add(c);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        c.setFill(state.getColor());
    }

    public void move() {
        // if x/y get less then 0 Or greater than width/ height we want to bounce the balls back from the wall
        if (x <= radius || x >= (area.getWidth()-radius)){
            dx *= -1;
        }
        else if (y <= radius || y >= (area.getHeight()-radius)){
            dy *= -1;
        }
        // when balls bounce in/near corners
        else if ((y <= (radius) && x <= (radius)) || ((x <= (radius)) && y >= (area.getHeight()-(radius)))
                || (x >= (area.getWidth()-(radius)) && y <= (radius))
                || (x >= (area.getWidth()-(radius)) && y >= (area.getHeight()-(radius))) ){
            dx *= -1;
            dy *= -1;
        }

        x += dx * SPEED;
        y += dy * SPEED;
    }

    // balls need to be drawn
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

    // Check if Running Host is OS X (macOS), because on that OS The Balls are with SPEED 1 to slow. :(
//    private void checkOS(){
//    if(System.getProperty("os.name").contains("OS X") || System.getProperty("os.name").contains("macOS"))
//    {
//        SPEED = 1.3;
//    }
//    }
}