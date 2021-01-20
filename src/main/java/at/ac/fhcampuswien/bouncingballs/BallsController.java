package at.ac.fhcampuswien.bouncingballs;

import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.Random;

/*
 The class BallsController is in charge of the interaction between the balls, themselves and their surroundings.
 Depending on the mode of the simulation (no precautions, social distancing, Lockdown, Lockdown with social distancing)
 some of the balls will behave differently.
*/

public class BallsController {
    private ArrayList<Ball> balls;
    private static double areaheight;
    private static double areawidth;
    private final static double infectionrate = SimulationController.getInfectionrate();
    private final static double deathrate = SimulationController.getDeathrate();

    public BallsController(Pane area, int populationSize) {
        areaheight = area.getHeight();
        areawidth = area.getWidth();
        balls = new ArrayList<>();


        // if Lockdown is selected: half of the population stands still while the other half is moving casually
        if(uiSettingsController.getoptLockdown() == true || uiSettingsController.getoptLockdownANDsocialDist() == true){
            for (int i = 0; i < (populationSize)/2; i++) {
                balls.add(new Ball(State.HEALTHY, area));
            }

            for (int i = 0; i < (populationSize-1)/2; i++) {
                Ball ball = new Ball(State.HEALTHY, area);
                ball.setSPEED(0);
                balls.add(ball);
            }
        }

        // no precautions mode
        else {

            // this creates healthy balls in the area of simulation
            for (int i = 0; i < populationSize - 1; i++) {
                balls.add(new Ball(State.HEALTHY, area));
            }
        }

        // this adds a single infected ball in the area of simulation
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
        Random random = new Random();
        double rnd = random.nextDouble();
        if (distance(a,b) < 2 * Ball.radius){ // if distance is less that 2 times the radius, collision occurred
            if (((a.getState() == State.INFECTED && b.getState() == State.HEALTHY) ||
                    (a.getState() == State.HEALTHY && b.getState() == State.INFECTED)) &&
                    rnd <= (infectionrate/100)){
                a.setState(State.INFECTED);
                b.setState(State.INFECTED);
            }
            if((a.getState() != State.DEAD && b.getState() != State.DEAD)){
//                    && (a.getX() > Ball.radius*4 && a.getX() < areawidth-(Ball.radius*4)) &&
//                    (b.getX() > Ball.radius*4 && b.getX() < areawidth-(Ball.radius*4)) &&
//                    (a.getY() > Ball.radius*4 && a.getY() < areaheight-(Ball.radius*4)) &&
//                    (b.getY() > Ball.radius*4 && b.getY() < areaheight-(Ball.radius*4))){

                    /*
                    this prevents dead balls from bouncing or getting bounced of and it prevents
                     balls from bouncing too near the edge of the pane since they otherwise could
                    bounce out of bounds
                    */

                if(a.getAbsDx() >= a.getAbsDy() && b.getAbsDx() >= b.getAbsDy()){
                    a.bounceX();
                    b.bounceX();
                }
                else if(a.getAbsDx() < a.getAbsDy() && b.getAbsDx() < b.getAbsDy()){
                    a.bounceY();
                    b.bounceY();
                }
                else if(a.getAbsDx() < a.getAbsDy() && b.getAbsDx() >= b.getAbsDy()){
                    a.bounceY();
                    b.bounceX();
                }
//                if(a.getAbsDx() >= a.getAbsDy() && b.getAbsDx() < b.getAbsDy()){ // last case
                else{
                    a.bounceX();
                    b.bounceY();
                }
                a.move();
                b.move();
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
            b.outcome(deathrate);
        }
    }
}
