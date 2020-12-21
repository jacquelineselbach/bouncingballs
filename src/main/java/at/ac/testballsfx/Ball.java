package at.ac.testballsfx;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Ball {

    // to adjust radius/size of balls
    public static int radius = 5;
    public static int healtime = 5 * 80; // because 60 Frames per second
    public static int distance  = 10000; // should simulate distance from where a ball starts in the simulation > add origin
    // i know it's a large number but otherwise not everybody will get infected (still not every balls gets always infected..... pretty random lol we have to work on this)

    private State state;
    private Position location;
    private Position origin;
    private Bouncing bouncing;

    // draw circles on the screens

    private Circle c;

    // draw area on the screens

    private Pane area;
    private int sickTime = 0;

    public Ball(State state, Pane area) {
        this.state = state;
        this.bouncing = new Bouncing();
        this.location = new Position(area); // 2 Objects / 2 Positions / starting at the same Value
        this.origin = new Position(location.getX(), location.getY()); // 2 Objects / 2 Positions / starting at the same Value
        this.area = area;
        this.c = new Circle(radius, state.getColor());



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

        location.move(bouncing, area, origin); // origin = where is it coming from - important for distance
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
            } else if (other.getState() == State.INFECTED && state == State.ATRISK) {
                setState(State.DEAD);
            }
        }
    }

    public void healing() {
        if (state == State.INFECTED) {
            sickTime++;
            if (sickTime >= healtime) {
                setState(State.RECOVERED);
            }
        }
    }
}

