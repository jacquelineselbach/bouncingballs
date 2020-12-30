package at.ac.fhcampuswien.bouncingballs;

import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class BallsController {
    private ArrayList<Ball> balls;
    double areaheight;
    double areawidth;

    public BallsController(Pane area, int populationSize) {
        areaheight = area.getHeight();
        areawidth = area.getWidth();
        balls = new ArrayList<>();

        // create healthy balls in area simulation
        for (int i = 0; i < populationSize-1; i++) {
            balls.add(new Ball(State.HEALTHY, area));
        }

        // add single infected ball in area simulation
        balls.add(new Ball(State.INFECTED, area));
        draw();
    }

    public void draw() {
        for (Ball b : balls) {
            b.draw();
        }
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public void moveBalls() {
        for (Ball b : balls) {
            if(b.getState() == State.DEAD){
                continue; // to stop zombies from walking
            }
           b.move();
        }
    }

    public double distance(Ball a, Ball b) {
        // Euclidean Distance Formula - Standard Euclidean Distance
        return (Math.sqrt(Math.pow(a.getX() - b.getX(), 2)) + (Math.pow(a.getY() - b.getY(), 2)));
    }

    public void collisionCheck(Ball a, Ball b) {
        if (distance(a,b) < 2 * Ball.radius){ // if distance is less that 3 times the radius, collision occurred
            if ((a.getState() == State.INFECTED && b.getState() == State.HEALTHY) ||
                    (a.getState() == State.HEALTHY && b.getState() == State.INFECTED)) {
                a.setState(State.INFECTED);
                b.setState(State.INFECTED);
            }
            if((a.getState() != State.DEAD && b.getState() != State.DEAD) &&
                    (a.getX() > Ball.radius && a.getX() < areawidth-Ball.radius) &&
                    (b.getX() > Ball.radius && b.getX() < areawidth-Ball.radius) &&
                    (a.getY() > Ball.radius && a.getY() < areaheight-Ball.radius) &&
                    (a.getY() > Ball.radius && a.getY() < areaheight-Ball.radius)){
                    /*
                    as of now workaround to prevent balls from clumping up on the edges after bouncing off each other
                    this prevents them from bouncing off each other near the edges of the pane
                     */
                if(a.getDx() >= a.getDy() && b.getDx() >= b.getDy()){
                    a.bounceX();
                    b.bounceX();
                    a.move();
                    b.move();
                }
                if(a.getDx() < a.getDy() && b.getDx() < b.getDy()){
                    a.bounceY();
                    b.bounceY();
                    a.move();
                    b.move();
                }
                if(a.getDx() < a.getDy() && b.getDx() >= b.getDy()){
                    a.bounceY();
                    b.bounceX();
                    a.move();
                    b.move();
                }
                if(a.getDx() >= a.getDy() && b.getDx() < b.getDy()){
                    a.bounceX();
                    b.bounceY();
                    a.move();
                    b.move();
                }
            }
        }
    }

    public void resolveCollisions() {
        for (Ball a : balls) {
            for (Ball b: balls) {
                if (a != b ){
                    collisionCheck(a,b);
                }
            }
        }
    }

    public void resolveInfections() {
        for(Ball b : balls) {
            b.outcome();
        }
    }
}
