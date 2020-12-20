package at.ac.testballsfx;

import javafx.scene.layout.Pane;

public class Position {

    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position(Pane area) {
        this(Ball.radius + Math.random() * (area.getWidth() - 2 * Ball.radius),
                Ball.radius + Math.random() * (area.getHeight() - 2 * Ball.radius));

    }

    // get distance between two points

    public double getX(){

        return x;
    }

    public double getY(){

        return y;
    }

    // tell distance between two points

    public double distance(Position other) {

        // Euclidean Distance Formula - Standard Euclidean Distance

        return (Math.sqrt(Math.pow(this.x - other.x, 2)) + (Math.pow(this.y - other.y, 2)));
    }

    public void move(Bouncing bouncing, Pane area, Position origin){
        x += bouncing.getDx();
        y += bouncing.getDy();

        // if x/y get less then 0 Or greater than width/ height we want to bounce the balls back from the wall

        if (x < Ball.radius || x > area.getWidth() - Ball.radius || distance(origin) > Ball.distance) { // distance(origin) > Ball.distance creates a "box/radius" around a ball
            bouncing.bounceX();
            x += bouncing.getDx();
        }

        if (y < Ball.radius || y > area.getHeight() - Ball.radius || distance(origin) > Ball.distance) {  // distance(origin) > Ball.distance creates a "box/radius" around a ball
            bouncing.bounceY();
            y += bouncing.getDy();
        }
    }

    public boolean collision(Position other) {
        return distance(other) < 2 * Ball.radius; // if distance is less that two times the radius, collision occured
    }

}
