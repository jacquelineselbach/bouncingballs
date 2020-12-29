package at.ac.fhcampuswien.bouncingballs;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Simulation {

    private ArrayList<Ball> balls;

    public Simulation(Pane area, int populationSize) {
        balls = new ArrayList<>();

        // create healthy balls in area simulation

        for (int i = 0; i < populationSize; i++) {
            balls.add(new Ball(State.HEALTHY, area));
        }

        // add infected ball in area simulation

        balls.add(new Ball(State.INFECTED, area));
        draw();
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public void move() {
        for (Ball b : balls) {
            if(b.getState() == State.DEAD)
            {
                continue; // to stop zombies from walking
            }
           b.move();
        }
    }

    public void draw() {
        for (Ball b : balls) {
            b.draw();
        }
    }

    public void resolveCollisions() {
        for (Ball b : balls) {
            for (Ball d: balls) {
                if (b != d ){
                    b.collisionCheck(d);
                }
            }
        }
    }

    public void healing() {
        for(Ball b : balls) {
            b.healing();
        }
    }
}
