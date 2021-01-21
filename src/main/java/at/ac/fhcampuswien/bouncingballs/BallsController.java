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
        if(uiSettingsController.getoptLockdown() || uiSettingsController.getoptLockdownANDsocialDist()){
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
                continue; // stops zombies from walking
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

        if (distance(a, b) < 2 * Ball.radius){ // if distance is less that 2 times the radius a collision occurred

            /*
            infection takes place only between infected and healthy balls
            and the infectionrate controls the chance of an infection occurring
             */
            if (((a.getState() == State.INFECTED && b.getState() == State.HEALTHY) ||
                    (a.getState() == State.HEALTHY && b.getState() == State.INFECTED)) &&
                    rnd <= (infectionrate/100)){
                a.setState(State.INFECTED);
                b.setState(State.INFECTED);
            }

            /*
            Balls only bounce off each other if both aren't in the dead state and if they are at least 2 times
            the radius away from each area border. This is needed because they could otherwise get stuck on walls
            in rare occasions.
            The bouncing axis are chosen by comparing movement direction parameters to prevent balls from
            bunching up against each other
             */
            if((a.getState() != State.DEAD && b.getState() != State.DEAD) &&
                    (a.getX() > Ball.radius*2 && a.getX() < areawidth-(Ball.radius*2)) &&
                    (b.getX() > Ball.radius*2 && b.getX() < areawidth-(Ball.radius*2)) &&
                    (a.getY() > Ball.radius*2 && a.getY() < areaheight-(Ball.radius*2)) &&
                    (b.getY() > Ball.radius*2 && b.getY() < areaheight-(Ball.radius*2))){
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
                // (a.getAbsDx() >= a.getAbsDy() && b.getAbsDx() < b.getAbsDy())
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
