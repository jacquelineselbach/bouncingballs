package at.ac.testballsfx;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Simulation {

    private ArrayList<Ball> balls;

    public Simulation(Pane area, int populationSize, int populationSizeAtRisk) {
        balls = new ArrayList<Ball>();

        // create healthy balls in area simulation

        for (int i = 0; i < populationSize; i++) {
            balls.add(new Ball(State.HEALTHY, area));
        }

        for (int i = 0; i < populationSizeAtRisk; i++) {
            balls.add(new Ball(State.ATRISK, area));
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
