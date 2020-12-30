package at.ac.fhcampuswien.bouncingballs;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Ball {
    private Circle c; // draw circles on the screens
    private Pane area; // draw area on the screens
    private Position location;
    // private Position origin;

    // positional variables and size of balls
    public static int radius = 5;
    private final static double SPEED = 1;
    private double dx;
    private double dy;

    // variables regarding sickness and health
    private State state;
    public static int healtime = 10 * 60; // runs on 60 frames per second at the moment
    private int sickTime = 0;

    // public static int distance  = 10000; // should simulate distance from where a ball starts in the simulation > add origin
    // This could be used to simulate social distancing.

    public Ball(State state, Pane area) {
        this.state = state;
        this.location = new Position(area); // 2 Objects / 2 Positions / starting at the same Value
        // this.origin = new Position(location.getX(), location.getY());
        this.area = area;
        this.c = new Circle(radius, state.getColor());

        double direction = Math.random() * 2 * Math.PI;
        dx = Math.sin(direction);
        dy = Math.cos(direction);

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

    // balls need to move
    public void move() {
        location.move(this, area);
    }

    // balls need to be drawn
    public void draw() {
        c.setRadius(radius);
        c.setTranslateX(location.getX());
        c.setTranslateY(location.getY());
    }

    public void collisionCheck(Ball other) {
        if (location.collision(other.location)) {
            if (other.getState() == State.INFECTED && state == State.HEALTHY) {
                setState(State.INFECTED);
            }
            if(getState() != State.DEAD || other.getState() != State.DEAD) {
                Random random = new Random();
                double randomdirection = random.nextDouble();
                if(randomdirection < 0.5){
                    bounceX();
                    other.bounceY();
                }
                else {
                    bounceY();
                    other.bounceX();
                }
            }
        }
    }

    public void healing() {
        Random rand = new Random();
        double prob;
        if (state == State.INFECTED) {
            sickTime++;
            prob = rand.nextDouble();

            if (sickTime >= healtime && prob < 0.8) {
                setState(State.RECOVERED);

            } else if(sickTime >= healtime && prob >= 0.2) {
                setState(State.DEAD);
            }
        }
    }

    public double getDx() {
        
        return dx * SPEED;
    }

    public double getDy() {
        
        return dy * SPEED;
    }

    // if we hit walls dx/dy or other balls we need need to change direction

    public void bounceX() {
        
        dx *= -1;
    }

    public void bounceY() {
        
        dy *= -1;
    }
}